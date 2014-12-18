package pipetableformatter;

import java.util.Iterator;

public class LineSplitter implements Iterable<String>, Iterator<String> {
    private String notFormattedText;
    private int startIndex;
    private int endIndex;
    private String endOfLine;
    private int prevStartIndex;
    private int lineIndex;

    public LineSplitter(String notFormattedText) {
        this.notFormattedText = notFormattedText;
        startIndex = 0;
        endIndex = 0;
        endOfLine = "";
        lineIndex = -1;
    }

    @Override
    public String next() {

        int winEof = notFormattedText.indexOf(PipeTableParser.WIN_EOF, startIndex);
        int linuxEof = notFormattedText.indexOf(PipeTableParser.LINUX_EOF, startIndex);

        if (winEof < 0 && linuxEof < 0) {
            endIndex = notFormattedText.length();
            endOfLine = "";
        } else if (winEof > 0 && winEof < linuxEof) {
            endIndex = winEof;
            endOfLine = PipeTableParser.WIN_EOF;
        } else {
            endIndex = linuxEof;
            endOfLine = PipeTableParser.LINUX_EOF;
        }

        String line = notFormattedText.substring(startIndex, endIndex);
        prevStartIndex = startIndex;
        startIndex = endIndex + endOfLine.length();
        lineIndex++;

        return line;
    }

    public String getEndOfLine() {
        return endOfLine;
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return startIndex < notFormattedText.length();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    public int currentLineStartIndex() {
        return prevStartIndex;
    }

    public int currentLineEndIndex() {
        return startIndex - endOfLine.length();
    }

    public int currentLineIndex() {
        return lineIndex;
    }
}
