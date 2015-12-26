package pipetableformatter.testsupport;

import com.sun.jna.platform.win32.WinBase;
import pipetableformatter.PipeTable;
import pipetableformatter.PipeTable.Row;

import java.util.ArrayList;
import java.util.List;

public class PipeTableBuilder {


    private List<Row> rows = new ArrayList<Row>();

    public static PipeTableBuilder aPipeTable() {
        return new PipeTableBuilder();
    }

    public RowBuilder addRow(String... values) {
        Row row = row(values);
        rows.add(row);
        return new RowBuilder(row);
    }

    public PipeTable build() {
        return new PipeTable(rows);
    }

    private Row row(String... values) {
        List<PipeTable.Cell> cellList = new ArrayList<PipeTable.Cell>();
        for (String cell : values) {
            cellList.add(new PipeTable.Cell(cell));
        }
        Row row = new Row(cellList, "\n");
        return row;
    }

    public class RowBuilder {
        private Row row;

        private RowBuilder(Row row) {
            this.row = row;
            row.setIndentation("");
            row.setLeadingPipe(true);
            row.setTrailingPipe(true);
        }

        public RowBuilder commented() {
            row.setCommented(true);
            return this;
        }

        public RowBuilder withIndentation(String indentation) {
            row.setIndentation(indentation);
            return this;
        }

        public RowBuilder withoutLeadingPipe() {
            row.setLeadingPipe(false);
            return this;
        }

        public RowBuilder withoutTrailingPipe() {
            row.setTrailingPipe(false);
            return this;
        }

        public RowBuilder addRow(String... values) {
            return PipeTableBuilder.this.addRow(values);
        }
        
        public PipeTable build() {
            return PipeTableBuilder.this.build();
        }

    }
}
