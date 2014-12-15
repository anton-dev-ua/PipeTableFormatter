package pipetableformatter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PipeTableTest {

    private PipeTable pipeTable;
    private PipeTable.Row[] expectedRows;

    @Before
    public void setUp() {
        List<PipeTable.Row> rows = new ArrayList<PipeTable.Row>();

        rows.add(row("row11", "row12", "row13"));
        rows.add(row("row21", "row22", "row23"));

        pipeTable = new PipeTable(rows);

        rows = new ArrayList<PipeTable.Row>();
        rows.add(row("row11", "", "row12", "row13"));
        rows.add(row("row21", "", "row22", "row23"));

        expectedRows = rows.toArray(new PipeTable.Row[rows.size()]);


    }

    @Test
    public void addsColumnBefore() {
        pipeTable.addColumnBefore(1);

        assertThat(pipeTable.getColumnCount(), is(4));
        PipeTable.Row[] rows = pipeTable.rows();
        for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
            for (int cellIndex = 0; cellIndex < rows[rowIndex].size(); cellIndex++) {
                assertThat(rows[rowIndex].get(cellIndex), is(expectedRows[rowIndex].get(cellIndex)));
            }
        }
    }

    private PipeTable.Row row(String... cells) {
        List<PipeTable.Cell> cellList = new ArrayList<PipeTable.Cell>();
        for (String cell : cells) {
            cellList.add(new PipeTable.Cell(cell));
        }
        return new PipeTable.Row(cellList, "\n");
    }
}