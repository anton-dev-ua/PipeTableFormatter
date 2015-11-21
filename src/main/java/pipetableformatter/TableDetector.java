package pipetableformatter;

import static pipetableformatter.DelimitersCount.*;

public class TableDetector {

    private String text;
    private DelimitersCount baseLineDelimitersCount;
    private boolean onlyPipe;

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
        return onlyPipe ? pipesCountIn(text, currLine) : delimitersCountIn(text, currLine);
    }

    private Range previous(Range line) {
        return new Range(findSOL(line.getStart() - 1), line.getStart());
    }

    private Range next(Range line) {
        return new Range(line.getEnd(), findEOL(line.getEnd() + 1));
    }
    
    public TableDetector usingOnlyPipe() {
        this.onlyPipe = true;
        return this;
    }
}
