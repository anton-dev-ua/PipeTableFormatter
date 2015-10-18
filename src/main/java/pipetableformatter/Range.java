package pipetableformatter;

public class Range {

    @Deprecated
    public static final Range EMPTY = new Range(0, 0);

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

    public boolean isNotEmpty() {
        return end - start > 0;
    }

    public boolean isEmpty() {
        return end - start == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Range)) return false;

        Range range = (Range) o;

        if (end != range.end) return false;
        if (start != range.start) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = start;
        result = 31 * result + end;
        return result;
    }

    @Override
    public String toString() {
        return "Range{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
