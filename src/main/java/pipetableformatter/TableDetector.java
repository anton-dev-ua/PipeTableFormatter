package pipetableformatter;

public class TableDetector {

    private String text;

    public TableDetector(String text) {
        this.text = text;
    }

    public Range find(int position) {

        text = "\n" + text;

        int lineEndIndex = findEOL(text, position);
        int lineStartIndex = findSOL(text, position);

        DelimitersCount delimitersCount = new DelimitersCount(text, lineStartIndex, lineEndIndex);

        if (delimitersCount.isZero()) return Range.EMPTY;

        lineStartIndex = findStartOfTable(text, lineStartIndex, delimitersCount);
        lineEndIndex = findEndOfTable(text, lineEndIndex, delimitersCount);

        return new Range(lineStartIndex, lineEndIndex - 1);
    }

    private int findEOL(String text, int position) {
        int lineEndIndex = text.indexOf("\n", position);
        if (lineEndIndex < 0) lineEndIndex = text.length();
        return lineEndIndex;
    }

    private int findSOL(String text, int position) {
        return text.lastIndexOf("\n", position);
    }

    private int findEndOfTable(String text, int endLineIndex, DelimitersCount delimitersCountEtalon) {

        DelimitersCount delimitersCount = delimitersCountEtalon;

        while (delimitersCountEtalon.isSameCount(delimitersCount) && endLineIndex < text.length() - 1) {
            int startIndex = endLineIndex;
            endLineIndex = findEOL(text, endLineIndex + 1);
            delimitersCount = new DelimitersCount(text, startIndex, endLineIndex);
            if (delimitersCountEtalon.isDifferentCount(delimitersCount)) endLineIndex = startIndex;
        }
        return endLineIndex;
    }

    private int findStartOfTable(String text, int startLineIndex, DelimitersCount delimitersCountEtalon) {
        DelimitersCount delimitersCount = delimitersCountEtalon;

        while (delimitersCountEtalon.isSameCount(delimitersCount) && startLineIndex > 0) {
            int endIndex = startLineIndex;
            startLineIndex = findSOL(text, startLineIndex - 1);
            delimitersCount = new DelimitersCount(text, startLineIndex, endIndex);
            if (delimitersCountEtalon.isDifferentCount(delimitersCount)) startLineIndex = endIndex;
        }
        return startLineIndex;
    }


}
