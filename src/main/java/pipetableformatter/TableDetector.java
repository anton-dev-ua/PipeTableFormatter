package pipetableformatter;

public class TableDetector {

    private String text;
    private char[] delimiters = DelimitersCount.DEFAULT_DELIMITERS;
    private DelimitersCount baseDelimitersCount;

    private TableDetector(String text) {
        this.text = "\n" + text + "\n";
    }

    public static TableDetector detectTableIn(String text) {
        return new TableDetector(text);
    }

    public Range around(int position) {
        Range baseLine = findBaseLine(position + 1);
        baseDelimitersCount = asIn(baseLine);
        
        if (baseDelimitersCount.isZero()) {
            return Range.EMPTY;
        } else {
            return findTableRange(baseLine);
        }
    }

    private Range findBaseLine(int position) {
        return new Range(findSOL(position), findEOL(position));
    }

    private Range findTableRange(Range baseLine) {
        return new Range(findStartOfTable(baseLine), findEndOfTable(baseLine) - 1);
    }

    private int findEOL(int position) {
        return text.indexOf("\n", position);
    }

    private int findSOL(int position) {
        return text.lastIndexOf("\n", position - 1);
    }

    private int findStartOfTable(Range currentLine) {
        Range line = findPrevious(currentLine);
        if(baseDelimitersCount.isSameCount(asIn(line))) {
            return findStartOfTable(line);
        } else {
            return currentLine.getStart();
        }
    }

    private int findEndOfTable(Range currentLine) {
        Range line = findNext(currentLine);
        if(baseDelimitersCount.isSameCount(asIn(line))) {
            return findEndOfTable(line);
        } else {
            return currentLine.getEnd();
        }
    }

    private DelimitersCount asIn(Range currLine) {
        return new DelimitersCount(text, currLine, delimiters);
    }

    private Range findPrevious(Range prevLine) {
        return new Range(findSOL(prevLine.getStart() - 1), prevLine.getStart());
    }

    private Range findNext(Range prevLine) {
        return new Range(prevLine.getEnd(), findEOL(prevLine.getEnd() + 1));
    }
    
    public TableDetector usingDelimiters(char... delimiters) {
        this.delimiters = delimiters;
        return this;
    }
}
