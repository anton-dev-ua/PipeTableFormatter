package pipetableformatter;

public class TableDetector {

    public static final String DELIMITERS = "|,";

    public Range findTableRange(String text, int position) {

        text = "\n" + text;
        int endLineIndex = text.indexOf("\n", position);
        int startLineIndex = text.lastIndexOf("\n", position);

        int delimiterCountEtalon = delimitersCount(text, startLineIndex, endLineIndex);
        if (delimiterCountEtalon == 0) return Range.EMPTY;

        startLineIndex = findStartOfTable(text, startLineIndex, delimiterCountEtalon);
        endLineIndex = findEndOfTable(text, endLineIndex, delimiterCountEtalon);

        return new Range(startLineIndex, endLineIndex - 1);
    }

    private int findEndOfTable(String text, int endLineIndex, int delimiterCountEtalon) {
        int delimiterCount = delimiterCountEtalon;
        while (delimiterCount == delimiterCountEtalon && endLineIndex < text.length() - 1) {
            int startIndex = endLineIndex;
            endLineIndex = text.indexOf("\n", endLineIndex + 1);
            delimiterCount = delimitersCount(text, startIndex, endLineIndex);
            if (delimiterCount != delimiterCountEtalon) endLineIndex = startIndex;
        }
        return endLineIndex;
    }

    private int findStartOfTable(String text, int startLineIndex, int delimiterCountEtalon) {
        int delimiterCount = delimiterCountEtalon;
        while (delimiterCount == delimiterCountEtalon && startLineIndex > 0) {
            int endIndex = startLineIndex;
            startLineIndex = text.lastIndexOf("\n", startLineIndex - 1);
            delimiterCount = delimitersCount(text, startLineIndex, endIndex);
            if (delimiterCount != delimiterCountEtalon) startLineIndex = endIndex;
        }
        return startLineIndex;
    }

    private int delimitersCount(String text, int start, int end) {
        boolean quoted = false;
        int count = 0;
        for (int index = start; index < end; index++) {
            if (text.charAt(index) == '"') {
                quoted = !quoted;
            }
            if (!quoted && isDelimiter(text.charAt(index))) {
                count++;
            }
        }
        return count;
    }

    private boolean isDelimiter(char ch) {
        return DELIMITERS.indexOf(ch) >= 0;
    }
}
