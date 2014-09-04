package pipetableformatter;

public class ColumnSplitter {
    public static final String DELIMITERS = "|,";
    private String line;
    private int startIndex = 0;
    private int length = 0;
    private boolean insideQuoted = false;
    private boolean quoted = false;

    public ColumnSplitter(String line) {
        this.line = line.trim();
        init();
    }

    private void init() {
        length = line.length();
        if (line.startsWith("|")) startIndex = 1;
        if (line.endsWith("|")) length--;
    }

    public String nextValue() {
        int endIndex = startIndex;
        while (hasMoreChars(endIndex) && notDelimiter(endIndex)) {
            detectQuoted(endIndex++);
        }
        String value = line.substring(startIndex, endIndex);
        startIndex = endIndex + 1;
        return openQuotes(value);
    }

    private boolean hasMoreChars(int endIndex) {
        return endIndex < length;
    }

    private boolean notDelimiter(int index) {
        return insideQuoted || !(DELIMITERS.indexOf(line.charAt(index)) >= 0);
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

    public Boolean hasValue() {
        return startIndex < length;
    }
}
