package pipetableformatter;

import java.util.ArrayList;
import java.util.List;

public class PipeTable {

    List<Row> table;

    public PipeTable(List<Row> table) {
        this.table = new ArrayList<Row>(table);
    }

    public int getRowCount() {
        return table.size();
    }

    public String getValue(int row, int column) {
        return table.get(row).get(column);
    }

    public Row[] rows() {
        return table.toArray(new Row[table.size()]);
    }

    public int getColumnCount() {
        return table.size() > 0 ? table.get(0).size() : 0;
    }

    static class Row {
        List<Cell> row;
        private String endOfLine;

        public Row(List<Cell> columns, String endOfLine) {
            this.endOfLine = endOfLine != null ? endOfLine : "";
            row = new ArrayList<Cell>(columns);
        }

        public int size() {
            return row.size();
        }

        public void add(String value) {
            row.add(new Cell(value));
        }

        public String get(int column) {
            return row.get(column).getValue();
        }

        public Cell[] columns() {
            return row.toArray(new Cell[row.size()]);
        }

        public String endOfLine() {
            return endOfLine;
        }
    }

    static class Cell {
        private String value;

        public Cell(String value) {
            this.value = value != null ? value.trim() : "";
        }

        public String getValue() {
            return value;
        }
    }
}
