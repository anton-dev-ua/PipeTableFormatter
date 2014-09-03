package pipetableformatter;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PipeTableFormatterTest {

    String notFormattedText =
            "" +
                    "|Header 1|Header 2|   Header 3|Header 4  |Header 5|\n" +
                    "|val 11| val 12|val 13| val 14|val 15|\n" +
                    "|some value longer then previous| val 22|val 33|val34|and last value with extra space                       |";

    String notFormattedCSVText =
            "" +
                    "Header 1,Header 2,   Header 3,Header 4  ,Header 5\n" +
                    "val 11, val 12,val 13, val 14,val 15\n" +
                    "some value longer then previous, val 22,val 33,val34,and last value with extra space                       ";

    String expectedText =
            "" +
                    "| Header 1                        | Header 2 | Header 3 | Header 4 | Header 5                        |\n" +
                    "| val 11                          | val 12   | val 13   | val 14   | val 15                          |\n" +
                    "| some value longer then previous | val 22   | val 33   | val34    | and last value with extra space |";

    @Test
    public void makesPipeDelimitedTableReadable() {
        String formattedText = new PipeTableFormatter().format(notFormattedText);

        assertThat(formattedText, is(expectedText));

    }
    
    @Test
    public void convertsCSVTableToPipeTable(){
        String formattedText = new PipeTableFormatter().format(notFormattedCSVText);

        assertThat(formattedText, is(expectedText));
    }
}
