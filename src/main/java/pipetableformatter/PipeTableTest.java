package pipetableformatter;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PipeTableTest {

    @Test
    public void convertsEachLineOfTextToRowOfTable() {
        PipeTable pipeTable = new PipeTable("row1\nrow2");
        assertThat(pipeTable.getRowCount(), is(2));
        assertThat(pipeTable.getValue(0, 0), is("row1"));
        assertThat(pipeTable.getValue(1, 0), is("row2"));
    }

    @Test
    public void understandsWindowsLineDelimiter() {
        PipeTable pipeTable = new PipeTable("row1\r\nrow2");
        assertThat(pipeTable.getRowCount(), is(2));
        assertThat(pipeTable.getValue(0, 0), is("row1"));
        assertThat(pipeTable.getValue(1, 0), is("row2"));
    }

    @Test
    public void splitsLineForColumnsByPipe() {
        PipeTable pipeTable = new PipeTable("|column1|column2|");
        assertThat(pipeTable.getValue(0, 0), is("column1"));
        assertThat(pipeTable.getValue(0, 1), is("column2"));
    }

    @Test
    public void removesLeadingAndTrailingSpacesOfValues() {
        PipeTable pipeTable = new PipeTable("| column1    | column2   |  column3   |");
        assertThat(pipeTable.getValue(0, 0), is("column1"));
        assertThat(pipeTable.getValue(0, 1), is("column2"));
        assertThat(pipeTable.getValue(0, 2), is("column3"));
    }

    @Test
    public void extendsShorterRowsToMaxSizeWithEmptyValues() {
        PipeTable pipeTable = new PipeTable("|val11|val12|\n|val21|val22|val23|");
        assertThat(pipeTable.getValue(0, 2), is(""));
    }

    @Test
    public void preservesLineEndSeparatorForEachLine(){
        PipeTable pipeTable = new PipeTable("row1\r\nrow2\nrow3");
        assertThat(pipeTable.rows()[0].endOfLine(), is("\r\n"));
        assertThat(pipeTable.rows()[1].endOfLine(), is("\n"));
        assertThat(pipeTable.rows()[2].endOfLine(), is(""));
    }

    @Test
    public void splitsLineForColumnsByComma(){
        PipeTable pipeTable = new PipeTable(",column1,column2");
        assertThat(pipeTable.getValue(0, 0), is(""));
        assertThat(pipeTable.getValue(0, 1), is("column1"));
        assertThat(pipeTable.getValue(0, 2), is("column2"));
    }

    @Test
    public void ignoresCommasInsideDoubleQuotes(){
        PipeTable pipeTable = new PipeTable(" \"col1val1,col1val2\",column2");
        assertThat(pipeTable.getValue(0,0), is("col1val1,col1val2"));
        assertThat(pipeTable.getValue(0,1), is("column2"));
    }

    @Test
    public void ignoresPipeInsideDoubleQuotes(){
        PipeTable pipeTable = new PipeTable(" |\"col1val1|col1val2\"|column2|");
        assertThat(pipeTable.getValue(0,0), is("col1val1|col1val2"));
        assertThat(pipeTable.getValue(0,1), is("column2"));
    }

    @Test
    public void treatsTwoDoubleQuotesInsideQuotesAsOne(){
        PipeTable pipeTable = new PipeTable(" |\"val1\"\"val2\"|column2|");
        assertThat(pipeTable.getValue(0,0), is("val1\"val2"));
    }

}
