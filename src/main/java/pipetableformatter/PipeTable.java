package pipetableformatter;

import java.util.ArrayList;
import java.util.List;

//TODO: make it immutable (use builder?)
public class PipeTable {

    List<Row> table;
    private int selectedRow = -1;
    private int selectedColumn = -1;

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

    public void addColumnBefore(int columnIndex) {
        for (Row row : table) {
            row.add(columnIndex, "");
        }
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(int selectedRow) {
        this.selectedRow = selectedRow;
    }

    public int getSelectedColumn() {
        return selectedColumn;
    }

    public void setSelectedColumn(int selectedColumn) {
        this.selectedColumn = selectedColumn;
    }

    public boolean isRowCommented(int row) {
        return table.get(row).isCommented();
    }

    //TODO: make it immutable (use builder?)
    public static class Row {
        List<Cell> row;
        private String endOfLine;
        private boolean commented;
        private String indentation;
        private boolean leadingPipe;
        private boolean trailingPipe;

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

        public void add(int column, String value) {
            row.add(column, new Cell(value));
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

        public boolean isCommented() {
            return commented;
        }

        public void setCommented(boolean commented) {
            this.commented = commented;
        }

        public String getIndentation() {
            return indentation;
        }

        public void setIndentation(String indentation) {
            this.indentation = indentation;
        }

        public boolean hasLeadingPipe() {
            return leadingPipe;
        }

        public boolean hasTrailingPipe() {
            return trailingPipe;
        }

        public void setLeadingPipe(boolean leadingPipe) {
            this.leadingPipe = leadingPipe;
        }

        public void setTrailingPipe(boolean trailingPipe) {
            this.trailingPipe = trailingPipe;
        }
    }

    public static class Cell {
        private String value;

        public Cell(String value) {
            this.value = value != null ? value.trim() : "";
        }

        public String getValue() {
            return value;
        }
    }
}
