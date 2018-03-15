package pipetableformatter;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PipeTableParserTest {

    private final static String TABLE =
            "" +
                    " | Header 1                        | Header 2 | Header 3 | Header 4 | Header 5                        | \n" +
                    " | val 11                          | val 12   | val 13   | val 14   | val 15                          | \n" +
                    " | some value longer then previous | val 22   | val 33   | val34    | and last value with extra space | ";


    @Test
    public void convertsEachLineOfTextToRowOfTable() {
        PipeTable pipeTable = new PipeTableParser("row1\nrow2").parse();
        assertThat(pipeTable.getRowCount(), is(2));
        assertThat(pipeTable.getValue(0, 0), is("row1"));
        assertThat(pipeTable.getValue(1, 0), is("row2"));
    }

    @Test
    public void understandsWindowsLineDelimiter() {
        PipeTable pipeTable = new PipeTableParser("row1\r\nrow2").parse();
        assertThat(pipeTable.getRowCount(), is(2));
        assertThat(pipeTable.getValue(0, 0), is("row1"));
        assertThat(pipeTable.getValue(1, 0), is("row2"));
    }

    @Test
    public void splitsLineForColumnsByPipe() {
        PipeTable pipeTable = new PipeTableParser("|column1|column2|").parse();
        assertThat(pipeTable.getValue(0, 0), is("column1"));
        assertThat(pipeTable.getValue(0, 1), is("column2"));
    }

    @Test
    public void splitsLineForColumnsByTab() {
        PipeTable pipeTable = new PipeTableParser("column1\tcolumn2").parse();
        assertThat(pipeTable.getValue(0, 0), is("column1"));
        assertThat(pipeTable.getValue(0, 1), is("column2"));
    }

    @Test
    public void removesLeadingAndTrailingSpacesOfValues() {
        PipeTable pipeTable = new PipeTableParser("| column1    | column2   |  column3   |").parse();
        assertThat(pipeTable.getValue(0, 0), is("column1"));
        assertThat(pipeTable.getValue(0, 1), is("column2"));
        assertThat(pipeTable.getValue(0, 2), is("column3"));
    }

    @Test
    public void extendsShorterRowsToMaxSizeWithEmptyValues() {
        PipeTable pipeTable = new PipeTableParser("|val11|val12|\n|val21|val22|val23|").parse();
        assertThat(pipeTable.getValue(0, 2), is(""));
    }

    @Test
    public void preservesLineEndSeparatorForEachLine() {
        PipeTable pipeTable = new PipeTableParser("row1\r\nrow2\nrow3").parse();
        assertThat(pipeTable.rows()[0].endOfLine(), is("\r\n"));
        assertThat(pipeTable.rows()[1].endOfLine(), is("\n"));
        assertThat(pipeTable.rows()[2].endOfLine(), is(""));
    }

    @Test
    public void splitsLineForColumnsByComma() {
        PipeTable pipeTable = new PipeTableParser(",column1,column2").parse();
        assertThat(pipeTable.getValue(0, 0), is(""));
        assertThat(pipeTable.getValue(0, 1), is("column1"));
        assertThat(pipeTable.getValue(0, 2), is("column2"));
    }

    @Test
    public void ignoresCommasInsideDoubleQuotes() {
        PipeTable pipeTable = new PipeTableParser(" \"col1val1,col1val2\",column2").parse();
        assertThat(pipeTable.getValue(0, 0), is("\"col1val1,col1val2\""));
        assertThat(pipeTable.getValue(0, 1), is("column2"));
    }

    @Test
    public void ignoresPipeInsideDoubleQuotes() {
        PipeTable pipeTable = new PipeTableParser(" |\"col1val1|col1val2\"|column2|").parse();
        assertThat(pipeTable.getValue(0, 0), is("\"col1val1|col1val2\""));
        assertThat(pipeTable.getValue(0, 1), is("column2"));
    }

    @Test
    public void treatsTwoDoubleQuotesInsideQuotesAsOne() {
        PipeTable pipeTable = new PipeTableParser(" |\"val1\"\"val2\"|column2|").parse();
        assertThat(pipeTable.getValue(0, 0), is("\"val1\"\"val2\""));
    }

    @Test
    public void doesNotTreatCommaAsDelimiterInPipeDelimitedTable() {
        PipeTable pipeTable = new PipeTableParser("|value 1|value 2.1, value 2.2|").parse();
        assertThat(pipeTable.getColumnCount(), is(2));
        assertThat(pipeTable.getValue(0, 1), is("value 2.1, value 2.2"));
    }

    @Test
    public void doesNotTreatPipeAsDelimiterInCommaDelimitedTable() {
        PipeTable pipeTable = new PipeTableParser("value 1, value 2.1| value 2.2, value 3").parse();
        assertThat(pipeTable.getColumnCount(), is(3));
        assertThat(pipeTable.getValue(0, 1), is("value 2.1| value 2.2"));
    }

    @Test
    public void usesPipeAsPriorityDelimiter() {
        PipeTable pipeTable = new PipeTableParser("|{color:red, length:10, strength:2}|{color:green, length:2, strength:5}|").parse();
        assertThat(pipeTable.getColumnCount(), is(2));
        assertThat(pipeTable.getValue(0, 0), is("{color:red, length:10, strength:2}"));
        assertThat(pipeTable.getValue(0, 1), is("{color:green, length:2, strength:5}"));
    }

    @Test
    public void determinesRowWhereRowIsPlaced() {
        PipeTableParser pipeTableParser = new PipeTableParser(TABLE).detectingCellByPosition(167);
        PipeTable pipeTable = pipeTableParser.parse();

        assertThat(pipeTable.getSelectedRow(), is(1));
    }

    @Test
    public void determinesColumnWhereRowIsPlaced() {
        PipeTableParser pipeTableParser = new PipeTableParser(TABLE).detectingCellByPosition(167);
        PipeTable pipeTable = pipeTableParser.parse();

        assertThat(pipeTable.getSelectedColumn(), is(3));
    }

    @Test
    public void determinesColumnIfCaretPlacedRightBeforeDelimiter() {
        PipeTableParser pipeTableParser = new PipeTableParser(TABLE).detectingCellByPosition(159);
        PipeTable pipeTable = pipeTableParser.parse();

        assertThat(pipeTable.getSelectedColumn(), is(2));
    }

    @Test
    public void determinesRowIfCaretPlacedRightAfterLastDelimiter() {
        PipeTableParser pipeTableParser = new PipeTableParser(TABLE).detectingCellByPosition(103);
        PipeTable pipeTable = pipeTableParser.parse();

        assertThat(pipeTable.getSelectedRow(), is(0));
    }

    @Test
    public void interpretsAsExtraColumnWhenCaretPlacedRightAfterLastDelimiter() {
        PipeTableParser pipeTableParser = new PipeTableParser(TABLE).detectingCellByPosition(103);
        PipeTable pipeTable = pipeTableParser.parse();

        assertThat(pipeTable.getSelectedColumn(), is(5));
    }

    @Test
    public void interpretsAsExtraColumnWhenCaretPlacedAtTheEndOfLine() {
        PipeTableParser pipeTableParser = new PipeTableParser(TABLE).detectingCellByPosition(104);
        PipeTable pipeTable = pipeTableParser.parse();

        assertThat(pipeTable.getSelectedColumn(), is(5));
    }

    @Test
    public void interpretsAsFirstColumnIfCaretPlacedRightBeforeDelimiter() {
        PipeTableParser pipeTableParser = new PipeTableParser(TABLE).detectingCellByPosition(106);
        PipeTable pipeTable = pipeTableParser.parse();

        assertThat(pipeTable.getSelectedColumn(), is(0));
    }

    @Test
    public void interpretsAsFirstColumnIfCaretPlacedAtStartOfLine() {
        PipeTableParser pipeTableParser = new PipeTableParser(TABLE).detectingCellByPosition(105);
        PipeTable pipeTable = pipeTableParser.parse();

        assertThat(pipeTable.getSelectedColumn(), is(0));
    }

    @Test
    public void interpretsAsExtraColumnIfCaretPlacedAtEndOfLastLine() {
        PipeTableParser pipeTableParser = new PipeTableParser(TABLE).detectingCellByPosition(314);
        PipeTable pipeTable = pipeTableParser.parse();

        assertThat(pipeTable.getSelectedColumn(), is(5));
    }

    @Test
    public void detectsCommentedRow() {
        PipeTable pipeTable = new PipeTableParser("" +
                "|row1.col1|row1.col2|\n" +
                "|-- row2.col1|row2.col2--|\n" +
                "|row3.col1|row3.col2|")
                .parse();
        
        assertThat(pipeTable.isRowCommented(0), is(false));
        assertThat(pipeTable.isRowCommented(1), is(true));
        assertThat(pipeTable.getValue(1,0), is("row2.col1"));
        assertThat(pipeTable.getValue(1,1), is("row2.col2"));
        assertThat(pipeTable.isRowCommented(2), is(false));

    }

    @Test
    public void savesOuterStates() {
        PipeTable pipeTable = new PipeTableParser("" +
                "    |row1.col1|row1.col2|\n" +
                "     row2.col1|row2.col2|\n" +
                "    |row3.col1|row3.col2 ")
                .parse();

        assertThat(pipeTable.rows()[1].getIndentation(), is("     "));
        assertThat(pipeTable.rows()[0].hasLeadingPipe(), is(true));
        assertThat(pipeTable.rows()[0].hasTrailingPipe(), is(true));
        assertThat(pipeTable.rows()[1].hasLeadingPipe(), is(false));
        assertThat(pipeTable.rows()[2].hasTrailingPipe(), is(false));

    }

    @Test
    public void detectsPresenceOfOuterPipeInCommentedRow() {
        PipeTable pipeTable = new PipeTableParser("|--row2.col1|row2.col2--|").parse();

        assertThat(pipeTable.rows()[0].hasLeadingPipe(), is(true));
        assertThat(pipeTable.rows()[0].hasTrailingPipe(), is(true));

    }

}
