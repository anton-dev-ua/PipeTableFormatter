package pipetableformatter;

import java.util.Iterator;

public class ColumnSplitter implements Iterable<String>, Iterator<String> {
    public static final String PIPE = "|";
    public static final String PIPE_COMMENT_START = "|--";
    public static final String PIPE_COMMENT_END = "--|";
    private final int leadingSpaces;
    private String line;
    private int startIndex = 0;
    private int length = 0;
    private boolean insideQuoted = false;
    private boolean quoted = false;
    private Character delimiter;
    private int prevStartIndex;
    private int columnIndex;

    public ColumnSplitter(String line, Character delimiter) {
        this.delimiter = delimiter;
        this.leadingSpaces = countLeadingSpaces(line);
        this.line = line.trim();
        init();
    }

    private int countLeadingSpaces(String line) {
        int charCount = 0;
        while (line.length() > charCount && line.charAt(charCount++) <= ' ') ;
        return charCount - 1;
    }

    private void init() {
        length = line.length();
        if (line.startsWith(PIPE_COMMENT_START)) {
            startIndex = PIPE_COMMENT_START.length();
        } else if (line.startsWith(PIPE)) {
            startIndex = 1;
        }
        if (line.endsWith(PIPE_COMMENT_END)) {
            length -= PIPE_COMMENT_END.length();
        } else if (line.endsWith(PIPE)) {
            length--;
        }
        columnIndex = -1;
    }

    @Override
    public String next() {
        int endIndex = startIndex;
        while (hasMoreChars(endIndex) && notDelimiter(endIndex)) {
            detectQuoted(endIndex++);
        }
        String value = line.substring(startIndex, endIndex);
        prevStartIndex = startIndex;
        startIndex = endIndex + 1;
        columnIndex++;
        return openQuotes(value);
    }

    private boolean hasMoreChars(int endIndex) {
        return endIndex < length;
    }

    private boolean notDelimiter(int index) {
        return insideQuoted || line.charAt(index) != delimiter;
    }

    private void detectQuoted(int index) {
        if (line.charAt(index) == '"') {
            quoted = true;
            insideQuoted = !insideQuoted;
        }
    }

    private String openQuotes(String value) {
        if (quoted) {
            quoted = false;
            return replaceTwoQuotesWithOne(removeOpenAndCloseQuotes(value));
        } else {
            return value;
        }
    }

    private String replaceTwoQuotesWithOne(String value) {
        return value.replaceAll("\"\"", "\"");
    }

    private String removeOpenAndCloseQuotes(String value) {
        return value.replaceAll("(^ *\")|(\" *$)", "");
    }

    public int currentColumnStartIndex() {
        return prevStartIndex + leadingSpaces;
    }

    public int currentColumnEndIndex() {
        return startIndex - 1 + leadingSpaces;
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return startIndex < length;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    public int currentColumnIndex() {
        return columnIndex;
    }
}
