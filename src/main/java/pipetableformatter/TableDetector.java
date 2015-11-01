package pipetableformatter;

public class TableDetector {

    private String text;
    private char[] delimiters = DelimitersCount.DEFAULT_DELIMITERS;
    private DelimitersCount baseLineDelimitersCount;

    private TableDetector(String text) {
        this.text = "\n" + text + "\n";
    }

    public static TableDetector detectTableIn(String text) {
        return new TableDetector(text);
    }

    public Range around(int position) {
        Range baseLine = findBaseLine(position + 1);
        baseLineDelimitersCount = asIn(baseLine);
        return baseLineDelimitersCount.isZero() ? Range.EMPTY : findTableRange(baseLine);
    }

    private Range findBaseLine(int position) {
        return new Range(findSOL(position), findEOL(position));
    }

    private Range findTableRange(Range baseLine) {
        return new Range(findStartOfTableOn(baseLine), findEndOfTableOn(baseLine) - 1);
    }

    private int findStartOfTableOn(Range line) {
        return tableContains(line) ? findStartOfTableOn(previous(line)) : line.getEnd();
    }

    private int findEndOfTableOn(Range line) {
        return tableContains(line) ? findEndOfTableOn(next(line)) : line.getStart();
    }

    private int findEOL(int position) {
        return text.indexOf("\n", position);
    }

    private int findSOL(int position) {
        return text.lastIndexOf("\n", position - 1);
    }

    private boolean tableContains(Range line) {
        return baseLineDelimitersCount.isSameCount(asIn(line));
    }

    private DelimitersCount asIn(Range currLine) {
        return new DelimitersCount(text, currLine, delimiters);
    }

    private Range previous(Range prevLine) {
        return new Range(findSOL(prevLine.getStart() - 1), prevLine.getStart());
    }

    private Range next(Range prevLine) {
        return new Range(prevLine.getEnd(), findEOL(prevLine.getEnd() + 1));
    }
    
    public TableDetector usingDelimiters(char... delimiters) {
        this.delimiters = delimiters;
        return this;
    }
}
