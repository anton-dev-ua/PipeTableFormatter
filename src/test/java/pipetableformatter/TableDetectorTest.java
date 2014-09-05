package pipetableformatter;

import org.junit.Before;
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

    public static final String TEXT_WITH_COMMA_INSIDE_QUOTES = "" +
            "header 1,header 2,header 3\n" +
            "value 11,\"val1,val2\",value 13\n";

    public static final String TEXT_WITH_PIPE_INSIDE_QUOTES = "" +
            "|header 1|header 2|header 3|\n" +
            "|value 11|\"val1|val2\"|value 13|\n";

    private TableDetector tableDetector;

    @Before
    public void setUp() {
        tableDetector = new TableDetector();

    }

    @Test
    public void detectsStartOfTheTableDelimitedWithPipe() {

        Range range = tableDetector.findTableRange(TEXT_WITH_TABLE_PIPE, 67);

        assertThat(range.getStart(), is(24));
    }

    @Test
    public void detectsEndOfTheTableDelimitedWithPipe(){
        Range range = tableDetector.findTableRange(TEXT_WITH_TABLE_PIPE, 67);

        assertThat(range.getEnd(), is(110));
    }

    @Test
    public void detectsStartOfTheTableDelimitedWithComma() {

        Range range = tableDetector.findTableRange(TEXT_WITH_TABLE_COMMA, 67);

        assertThat(range.getStart(), is(24));
    }

    @Test
    public void detectsEndOfTheTableDelimitedWithComma(){
        Range range = tableDetector.findTableRange(TEXT_WITH_TABLE_COMMA, 67);

        assertThat(range.getEnd(), is(104));
    }

    @Test
    public void ignoresCommasInsideQuotes() {
        Range range = tableDetector.findTableRange(TEXT_WITH_COMMA_INSIDE_QUOTES, 37);

        assertThat(range.getStart(), is(0));
        assertThat(range.getEnd(), is(56));
    }

    @Test
    public void ignoresPipeInsideQuotes() {
        Range range = tableDetector.findTableRange(TEXT_WITH_PIPE_INSIDE_QUOTES, 37);

        assertThat(range.getStart(), is(0));
        assertThat(range.getEnd(), is(60));
    }

    @Test
    public void canDetectTableIfNoLastEOL(){
        Range range = tableDetector.findTableRange(TEXT_WITH_TABLE_NO_LAST_EOL, 67);

        assertThat(range.getEnd(), is(110));
    }

}
