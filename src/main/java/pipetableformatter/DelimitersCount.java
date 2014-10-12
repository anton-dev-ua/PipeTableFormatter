package pipetableformatter;

import java.util.HashMap;
import java.util.Map;

public class DelimitersCount {

    private static final char[] delimiters = new char[]{'|', ',', '\t'};

    Map<Character, Integer> counters = new HashMap<Character, Integer>();

    public DelimitersCount(String text, int startIndex, int endIndex) {
        initCounters();
        count(text, startIndex, endIndex);
    }

    public DelimitersCount(String text) {
        this(text, 0, text.length());
    }

    private void initCounters() {
        for (char delimiter : delimiters) {
            counters.put(delimiter, 0);
        }
    }

    public boolean isSameCount(DelimitersCount another) {
        for (char delimiter : counters.keySet()) {
            if (counters.get(delimiter).equals(another.counters.get(delimiter)) && counters.get(delimiter) > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isZero() {
        for (int count : counters.values()) {
            if (count != 0) return false;
        }
        return true;
    }

    public boolean isDifferentCount(DelimitersCount delimitersCount) {
        return !isSameCount(delimitersCount);
    }

    private int count(String text, int start, int end) {
        boolean quoted = false;
        int count = 0;

        for (int index = start; index < end; index++) {
            if (text.charAt(index) == '"') quoted = !quoted;
            if (quoted) continue;
            checkForDelimiter(text.charAt(index));
        }

        return count;
    }

    private void checkForDelimiter(char character) {
        if (counters.containsKey(character)) {
            counters.put(character, counters.get(character) + 1);
        }
    }

    public Character mostFrequent() {
        char maxDelimiter = 0;
        int maxCount = 0;

        for (char delimiter : counters.keySet()) {
            if (counters.get(delimiter) > maxCount) {
                maxCount = counters.get(delimiter);
                maxDelimiter = delimiter;
            }
        }

        return maxDelimiter;
    }
}
