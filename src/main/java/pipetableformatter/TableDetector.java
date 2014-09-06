package pipetableformatter;

public class TableDetector {

    public static final char PIPE = '|';
    public static final char COMMA = ',';

    public Range find(String text, int position) {

        text = "\n" + text;

        int lineEndIndex = text.indexOf("\n", position);
        int lineStartIndex = text.lastIndexOf("\n", position);

        DelimitersCount delimitersCount = calcDelimitersCount(text, lineEndIndex, lineStartIndex);
        
        if (delimitersCount.isZero()) return Range.EMPTY;

        lineStartIndex = findStartOfTable(text, lineStartIndex, delimitersCount);
        lineEndIndex = findEndOfTable(text, lineEndIndex, delimitersCount);

        return new Range(lineStartIndex, lineEndIndex - 1);
    }

    private DelimitersCount calcDelimitersCount(String text, int lineEndIndex, int lineStartIndex) {
        return new DelimitersCount(
                charCount(text, PIPE, lineStartIndex, lineEndIndex), 
                charCount(text, COMMA, lineStartIndex, lineEndIndex)
        );
    }

    private int findEndOfTable(String text, int endLineIndex, DelimitersCount delimitersCountEtalon) {

        DelimitersCount delimitersCount = delimitersCountEtalon;

        while (delimitersCountEtalon.isSameCount(delimitersCount) && endLineIndex < text.length() - 1) {
            int startIndex = endLineIndex;
            endLineIndex = text.indexOf("\n", endLineIndex + 1);
            if(endLineIndex<0) endLineIndex = text.length();
            delimitersCount = calcDelimitersCount(text, endLineIndex, startIndex);
            if (delimitersCountEtalon.isDifferentCount(delimitersCount)) endLineIndex = startIndex;
        }
        return endLineIndex;
    }

    private int findStartOfTable(String text, int startLineIndex, DelimitersCount delimitersCountEtalon) {
        DelimitersCount delimitersCount = delimitersCountEtalon;

        while (delimitersCountEtalon.isSameCount(delimitersCount) && startLineIndex > 0) {
            int endIndex = startLineIndex;
            startLineIndex = text.lastIndexOf("\n", startLineIndex - 1);
            delimitersCount = calcDelimitersCount(text, endIndex, startLineIndex);
            if (delimitersCountEtalon.isDifferentCount(delimitersCount)) startLineIndex = endIndex;
        }
        return startLineIndex;
    }

    private int charCount(String text, char delimiter, int start, int end) {
        boolean quoted = false;
        int count = 0;
        for (int index = start; index < end; index++) {
            if (text.charAt(index) == '"') {
                quoted = !quoted;
            }
            if (!quoted && text.charAt(index) == delimiter) {
                count++;
            }
        }
        return count;
    }

    private static class DelimitersCount {
        private int pipe;
        private int comma;

        public DelimitersCount(int pipeCount, int commaCount) {
            pipe = pipeCount;
            comma = commaCount;
        }

        public int getPipe() {
            return pipe;
        }

        public int getComma() {
            return comma;
        }

        public boolean isSameCount(DelimitersCount count){
            return (pipe == count.getPipe() && pipe > 0) || (comma == count.getComma() && comma > 0);
        }

        public boolean isZero() {
            return pipe == 0 && comma == 0;
        }

        public boolean isDifferentCount(DelimitersCount delimitersCount) {
            return !isSameCount(delimitersCount);
        }
    }
}
