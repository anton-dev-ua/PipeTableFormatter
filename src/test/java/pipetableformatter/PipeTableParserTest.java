package pipetableformatter;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PipeTableParserTest {

    @Test
    public void convertsEachLineOfTextToRowOfTable() {
        PipeTable pipeTable = new PipeTableParser().parse("row1\nrow2");
        assertThat(pipeTable.getRowCount(), is(2));
        assertThat(pipeTable.getValue(0, 0), is("row1"));
        assertThat(pipeTable.getValue(1, 0), is("row2"));
    }

    @Test
    public void understandsWindowsLineDelimiter() {
        PipeTable pipeTable = new PipeTableParser().parse("row1\r\nrow2");
        assertThat(pipeTable.getRowCount(), is(2));
        assertThat(pipeTable.getValue(0, 0), is("row1"));
        assertThat(pipeTable.getValue(1, 0), is("row2"));
    }

    @Test
    public void splitsLineForColumnsByPipe() {
        PipeTable pipeTable = new PipeTableParser().parse("|column1|column2|");
        assertThat(pipeTable.getValue(0, 0), is("column1"));
        assertThat(pipeTable.getValue(0, 1), is("column2"));
    }

    @Test
    public void splitsLineForColumnsByTab() {
        PipeTable pipeTable = new PipeTableParser().parse("column1\tcolumn2");
        assertThat(pipeTable.getValue(0, 0), is("column1"));
        assertThat(pipeTable.getValue(0, 1), is("column2"));
    }

    @Test
    public void removesLeadingAndTrailingSpacesOfValues() {
        PipeTable pipeTable = new PipeTableParser().parse("| column1    | column2   |  column3   |");
        assertThat(pipeTable.getValue(0, 0), is("column1"));
        assertThat(pipeTable.getValue(0, 1), is("column2"));
        assertThat(pipeTable.getValue(0, 2), is("column3"));
    }

    @Test
    public void extendsShorterRowsToMaxSizeWithEmptyValues() {
        PipeTable pipeTable = new PipeTableParser().parse("|val11|val12|\n|val21|val22|val23|");
        assertThat(pipeTable.getValue(0, 2), is(""));
    }

    @Test
    public void preservesLineEndSeparatorForEachLine(){
        PipeTable pipeTable = new PipeTableParser().parse("row1\r\nrow2\nrow3");
        assertThat(pipeTable.rows()[0].endOfLine(), is("\r\n"));
        assertThat(pipeTable.rows()[1].endOfLine(), is("\n"));
        assertThat(pipeTable.rows()[2].endOfLine(), is(""));
    }

    @Test
    public void splitsLineForColumnsByComma(){
        PipeTable pipeTable = new PipeTableParser().parse(",column1,column2");
        assertThat(pipeTable.getValue(0, 0), is(""));
        assertThat(pipeTable.getValue(0, 1), is("column1"));
        assertThat(pipeTable.getValue(0, 2), is("column2"));
    }

    @Test
    public void ignoresCommasInsideDoubleQuotes(){
        PipeTable pipeTable = new PipeTableParser().parse(" \"col1val1,col1val2\",column2");
        assertThat(pipeTable.getValue(0,0), is("col1val1,col1val2"));
        assertThat(pipeTable.getValue(0,1), is("column2"));
    }

    @Test
    public void ignoresPipeInsideDoubleQuotes(){
        PipeTable pipeTable = new PipeTableParser().parse(" |\"col1val1|col1val2\"|column2|");
        assertThat(pipeTable.getValue(0,0), is("col1val1|col1val2"));
        assertThat(pipeTable.getValue(0,1), is("column2"));
    }

    @Test
    public void treatsTwoDoubleQuotesInsideQuotesAsOne(){
        PipeTable pipeTable = new PipeTableParser().parse(" |\"val1\"\"val2\"|column2|");
        assertThat(pipeTable.getValue(0,0), is("val1\"val2"));
    }

    @Test
    public void doesNotTreatCommaAsDelimiterInPipeDelimitedTable(){
        PipeTable pipeTable = new PipeTableParser().parse("|value 1|value 2.1, value 2.2|");
        assertThat(pipeTable.getColumnCount(), is(2));
        assertThat(pipeTable.getValue(0,1), is("value 2.1, value 2.2"));
    }

    @Test
    public void doesNotTreatPipeAsDelimiterInCommaDelimitedTable(){
        PipeTable pipeTable = new PipeTableParser().parse("value 1, value 2.1| value 2.2, value 3");
        assertThat(pipeTable.getColumnCount(), is(3));
        assertThat(pipeTable.getValue(0,1), is("value 2.1| value 2.2"));
    }

}
