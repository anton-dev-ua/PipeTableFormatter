package pipetableformatter;

import java.util.ArrayList;
import java.util.List;

public class PipeTableBuilder {


    private List<PipeTable.Row> rows = new ArrayList<PipeTable.Row>();

    public static PipeTableBuilder aPipeTable() {
        return new PipeTableBuilder();
    }

    public PipeTableBuilder withRow(String... values) {
        rows.add(row(values));
        return this;
    }

    public PipeTable build() {
        return new PipeTable(rows);
    }

    private PipeTable.Row row(String... values) {
        List<PipeTable.Cell> cellList = new ArrayList<PipeTable.Cell>();
        for (String cell : values) {
            cellList.add(new PipeTable.Cell(cell));
        }
        return new PipeTable.Row(cellList, "\n");
    }
}
