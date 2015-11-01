package pipetableformatter;

import java.util.HashMap;
import java.util.Map;

public class DelimitersCount {

    public static final char[] DEFAULT_DELIMITERS = new char[]{'|', ',', '\t'};
    
    private char[] delimiters;
    Map<Character, Integer> counters = new HashMap<Character, Integer>();

    public DelimitersCount(String text) {
        this(text, new Range(0, text.length()), DEFAULT_DELIMITERS);
    }

    public DelimitersCount(String text, Range lineRange, char[] delimiters) {
        this.delimiters = delimiters;
        initCounters();
        count(text, lineRange.getStart(), lineRange.getEnd());
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

    private int count(String text, int start, int end) {
        boolean quoted = false;
        int count = 0;

        for (int index = start >= 0 ? start : 0; index < end; index++) {
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
