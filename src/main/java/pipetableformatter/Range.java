package pipetableformatter;

public class Range {

    public static final Range EMPTY = new Range(0,0);

    private int start;
    private int end;

    public Range(int start, int end) {

        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
