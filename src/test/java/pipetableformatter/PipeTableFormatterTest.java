package pipetableformatter;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static pipetableformatter.PipeTableBuilder.aPipeTable;
import static pipetableformatter.PipeTableFormatter.formatter;

public class PipeTableFormatterTest {

    private static final String FORMATTED_TABLE =
            "" +
                    "| Header 1                        | Header 2 | Header 3 | Header 4 | Header 5                        |\n" +
                    "| val 11                          | val 12   | val 13   | val 14   | val 15                          |\n" +
                    "| some value longer then previous | val 22   | val 33   | val34    | and last value with extra space |\n";

    @Test
    public void formatsTableDelimitingColumnsWithPipes() {
        PipeTable pipeTable = aPipeTable()
                .withRow("Header 1", "Header 2", "Header 3", "Header 4", "Header 5")
                .withRow("val 11", "val 12", "val 13", "val 14", "val 15")
                .withRow("some value longer then previous", "val 22", "val 33", "val34", "and last value with extra space")
                .build();

        String formattedText = formatter().format(pipeTable);

        assertThat(formattedText, is(FORMATTED_TABLE));
    }
}
