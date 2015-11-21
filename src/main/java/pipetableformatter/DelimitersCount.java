package pipetableformatter;

import java.util.HashMap;
import java.util.Map;

public class DelimitersCount {

    public static final char PIPE = '|';
    public static final char[] DEFAULT_DELIMITERS = new char[]{PIPE, ',', '\t'};

    private String text;
    private char[] delimiters;
    Map<Character, Integer> counters = new HashMap<Character, Integer>();

    private DelimitersCount(String text, Range lineRange, char... delimiters) {
        this.text = text;
        this.delimiters = delimiters;
        initCounters();
        count(text, lineRange.getStart(), lineRange.getEnd());
    }
    
    public static DelimitersCount delimitersCountIn(String text) {
        return new DelimitersCount(text, new Range(0, text.length()), DEFAULT_DELIMITERS);
    }
    
    public static DelimitersCount delimitersCountIn(String text, Range lineRange) {
        return new DelimitersCount(text, lineRange, DEFAULT_DELIMITERS);
    }

    public static DelimitersCount pipesCountIn(String text, Range lineRange) {
        return new DelimitersCount(text, lineRange, PIPE);
    }

    private void initCounters() {
        for (char delimiter : delimiters) {
            counters.put(delimiter, 0);
        }
    }

    public boolean isSameCount(DelimitersCount another) {
        if (counters.get(PIPE) > 0 || another.counters.get(PIPE) > 0) {
            return counters.get(PIPE).equals(another.counters.get(PIPE));
        }
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

        if (text.split("\n").length * 2 <= counters.get(PIPE)) {
            return PIPE;
        }

        for (char delimiter : counters.keySet()) {
            if (counters.get(delimiter) > maxCount) {
                maxCount = counters.get(delimiter);
                maxDelimiter = delimiter;
            }
        }

        return maxDelimiter;
    }
}
