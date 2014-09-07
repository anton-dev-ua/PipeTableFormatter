package pipetableformatter;

public class TableDetector {

    public Range find(String text, int position) {

        text = "\n" + text;

        int lineEndIndex = text.indexOf("\n", position);
        int lineStartIndex = text.lastIndexOf("\n", position);

        DelimitersCount delimitersCount = new DelimitersCount(text, lineStartIndex, lineEndIndex);
        
        if (delimitersCount.isZero()) return Range.EMPTY;

        lineStartIndex = findStartOfTable(text, lineStartIndex, delimitersCount);
        lineEndIndex = findEndOfTable(text, lineEndIndex, delimitersCount);

        return new Range(lineStartIndex, lineEndIndex - 1);
    }

    private int findEndOfTable(String text, int endLineIndex, DelimitersCount delimitersCountEtalon) {

        DelimitersCount delimitersCount = delimitersCountEtalon;

        while (delimitersCountEtalon.isSameCount(delimitersCount) && endLineIndex < text.length() - 1) {
            int startIndex = endLineIndex;
            endLineIndex = text.indexOf("\n", endLineIndex + 1);
            if(endLineIndex<0) endLineIndex = text.length();
            delimitersCount = new DelimitersCount(text, startIndex, endLineIndex);
            if (delimitersCountEtalon.isDifferentCount(delimitersCount)) endLineIndex = startIndex;
        }
        return endLineIndex;
    }

    private int findStartOfTable(String text, int startLineIndex, DelimitersCount delimitersCountEtalon) {
        DelimitersCount delimitersCount = delimitersCountEtalon;

        while (delimitersCountEtalon.isSameCount(delimitersCount) && startLineIndex > 0) {
            int endIndex = startLineIndex;
            startLineIndex = text.lastIndexOf("\n", startLineIndex - 1);
            delimitersCount = new DelimitersCount(text, startLineIndex, endIndex);
            if (delimitersCountEtalon.isDifferentCount(delimitersCount)) startLineIndex = endIndex;
        }
        return startLineIndex;
    }



}
