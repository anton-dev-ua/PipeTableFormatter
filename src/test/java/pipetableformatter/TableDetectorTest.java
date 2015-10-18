package pipetableformatter;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TableDetectorTest {

    public static final String TEXT_WITH_TABLE_PIPE = "" +
            "this line not in table\n" +
            "\n" +
            "|header 1|header 2|header 3|\n" +
            "|value 11|value 12|value 13|\n" +
            "|value 21|value 22|value 33|\n";

    public static final String TEXT_WITH_TABLE_NO_LAST_EOL = "" +
            "this line not in table\n" +
            "\n" +
            "|header 1|header 2|header 3|\n" +
            "|value 11|value 12|value 13|\n" +
            "|value 21|value 22|value 33|";

    public static final String TEXT_WITH_TABLE_COMMA = "" +
            "this line not in table\n" +
            "\n" +
            "header 1,header 2,header 3\n" +
            "value 11,value 12,value 13\n" +
            "value 21,value 22,value 33\n";

    public static final String TEXT_WITH_TABLE_TAB = "" +
            "this line not in table\n" +
            "\n" +
            "header 1\theader 2\theader 3\n" +
            "value 11\tvalue 12\tvalue 13\n" +
            "value 21\tvalue 22\tvalue 33\n";

    public static final String TEXT_WITH_COMMA_INSIDE_QUOTES = "" +
            "header 1,header 2,header 3\n" +
            "value 11,\"val1,val2\",value 13\n";

    public static final String TEXT_WITH_PIPE_INSIDE_QUOTES = "" +
            "|header 1|header 2|header 3|\n" +
            "|value 11|\"val1|val2\"|value 13|\n";

    public static final String PIPE_TABLE_CONTAINS_VALUE_WITH_COMMA = "" +
            "|header 1|header 2|header 3|\n" +
            "|value 11|val1,val2|value 13|\n";

    public static final int PIPE_TABLE_START_POS = 24;
    public static final int PIPE_TABLE_END_POS = 110;
    public static final int COMMA_TABLE_START_POS = 24;
    public static final int COMMA_TABLE_END_POS = 104;
    public static final int TAB_TABLE_START_POS = 24;
    public static final int TAB_TABLE_END_POS = 104;

    @Test
    public void detectsStartOfTheTableDelimitedWithPipe() {

        Range range = new TableDetector(TEXT_WITH_TABLE_PIPE).findAround(67);

        assertThat(range.getStart(), is(PIPE_TABLE_START_POS));
    }

    @Test
    public void detectsEndOfTheTableDelimitedWithPipe(){
        Range range = new TableDetector(TEXT_WITH_TABLE_PIPE).findAround(67);

        assertThat(range.getEnd(), is(PIPE_TABLE_END_POS));
    }

    @Test
    public void detectsStartOfTheTableDelimitedWithComma() {

        Range range = new TableDetector(TEXT_WITH_TABLE_COMMA).findAround(67);

        assertThat(range.getStart(), is(COMMA_TABLE_START_POS));
    }

    @Test
    public void detectsEndOfTheTableDelimitedWithComma(){
        Range range = new TableDetector(TEXT_WITH_TABLE_COMMA).findAround(67);

        assertThat(range.getEnd(), is(COMMA_TABLE_END_POS));
    }

    @Test
    public void detectsStartOfTheTableDelimitedWithTab() {

        Range range = new TableDetector(TEXT_WITH_TABLE_TAB).findAround(67);

        assertThat(range.getStart(), is(TAB_TABLE_START_POS));
    }

    @Test
    public void detectsEndOfTheTableDelimitedWithTab(){
        Range range = new TableDetector(TEXT_WITH_TABLE_TAB).findAround(67);

        assertThat(range.getEnd(), is(TAB_TABLE_END_POS));
    }

    @Test
    public void ignoresCommasInsideQuotes() {
        Range range = new TableDetector(TEXT_WITH_COMMA_INSIDE_QUOTES).findAround(37);

        assertThat(range.getStart(), is(0));
        assertThat(range.getEnd(), is(TEXT_WITH_COMMA_INSIDE_QUOTES.length()-1));
    }

    @Test
    public void ignoresPipeInsideQuotes() {
        Range range = new TableDetector(TEXT_WITH_PIPE_INSIDE_QUOTES).findAround(37);

        assertThat(range.getStart(), is(0));
        assertThat(range.getEnd(), is(TEXT_WITH_PIPE_INSIDE_QUOTES.length()-1));
    }

    @Test
    public void canDetectTableIfNoLastEOL(){
        Range range = new TableDetector(TEXT_WITH_TABLE_NO_LAST_EOL).findAround(67);

        assertThat(range.getEnd(), is(TEXT_WITH_TABLE_NO_LAST_EOL.length()));
    }

    @Test
    public void canDetectTableIfNoLastEOLAndCaretAtLastLine(){
        Range range = new TableDetector(TEXT_WITH_TABLE_NO_LAST_EOL).findAround(98);

        assertThat(range.getEnd(), is(TEXT_WITH_TABLE_NO_LAST_EOL.length()));
    }

    @Test
    public void ignoresCommaInsidePipeTable() {
        Range range = new TableDetector(PIPE_TABLE_CONTAINS_VALUE_WITH_COMMA).findAround(37);

        assertThat(range.getStart(), is(0));
        assertThat(range.getEnd(), is(PIPE_TABLE_CONTAINS_VALUE_WITH_COMMA.length()-1));
    }

    @Test
    public void detectsTableWhenCaretPlacedAtStartOfALine() {
        Range range = new TableDetector(TEXT_WITH_TABLE_PIPE).findAround(53);

        assertThat(range.getStart(), is(PIPE_TABLE_START_POS));
    }

    @Test
    public void detectsTableWhenCaretPlacedAtEndOfALine() {
        Range range = new TableDetector(TEXT_WITH_TABLE_PIPE).findAround(52);

        assertThat(range.getStart(), is(PIPE_TABLE_START_POS));
    }
}
