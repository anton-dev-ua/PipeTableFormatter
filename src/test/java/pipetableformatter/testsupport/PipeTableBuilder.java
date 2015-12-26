package pipetableformatter.testsupport;

import pipetableformatter.PipeTable;

import java.util.ArrayList;
import java.util.List;

public class PipeTableBuilder {


    private List<PipeTable.Row> rows = new ArrayList<PipeTable.Row>();

    public static PipeTableBuilder aPipeTable() {
        return new PipeTableBuilder();
    }

    public PipeTableBuilder withRow(String... values) {
        rows.add(row(false, values));
        return this;
    }

    public PipeTableBuilder withCommentedRow(String... values) {
        rows.add(row(true, values));
        return this;
    }

    public PipeTable build() {
        return new PipeTable(rows);
    }

    private PipeTable.Row row(boolean commented, String... values) {
        List<PipeTable.Cell> cellList = new ArrayList<PipeTable.Cell>();
        for (String cell : values) {
            cellList.add(new PipeTable.Cell(cell));
        }
        PipeTable.Row row = new PipeTable.Row(cellList, "\n");
        row.setCommented(commented);
        return row;
    }
}
