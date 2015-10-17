package pipetableformatter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static pipetableformatter.PipeTableBuilder.aPipeTable;

public class PipeTableTest {

    private PipeTable pipeTable;
    private PipeTable expectedTable;

    @Before
    public void setUp() {
        pipeTable = aPipeTable()
                .withRow("row11", "row12", "row13")
                .withRow("row21", "row22", "row23")
                .build();

        expectedTable = aPipeTable()
                .withRow("row11", "", "row12", "row13")
                .withRow("row21", "", "row22", "row23")
                .build();
    }

    @Test
    public void addsColumnBefore() {
        pipeTable.addColumnBefore(1);

        assertThat(pipeTable.getColumnCount(), is(4));

        PipeTable.Row[] rows = pipeTable.rows();
        PipeTable.Row[] expectedRows = expectedTable.rows();
        for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
            for (int cellIndex = 0; cellIndex < rows[rowIndex].size(); cellIndex++) {
                assertThat(rows[rowIndex].get(cellIndex), is(expectedRows[rowIndex].get(cellIndex)));
            }
        }
    }
}