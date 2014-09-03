package pipetableformatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PipeTableRow {
    List<String> row;
    private String endOfLine;

    public PipeTableRow(List<String> columns, String endOfLine) {
        this.endOfLine = endOfLine != null ? endOfLine : "";
        row = new ArrayList<String>(columns);
    }

    public int size() {
        return row.size();
    }

    public void add(String value) {
        row.add(value);
    }

    public String get(int column) {
        return row.get(column);
    }

    public String[] columns() {
        return row.toArray(new String[row.size()]);
    }

    public String endOfLine() {
        return endOfLine;
    }
}
