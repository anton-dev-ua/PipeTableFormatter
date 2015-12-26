package pipetableformatter;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static pipetableformatter.FormatOptions.formatOptions;
import static pipetableformatter.testsupport.PipeTableBuilder.aPipeTable;
import static pipetableformatter.PipeTableFormatter.formatter;

public class PipeTableFormatterTest {

    private static final String FORMATTED_TABLE =
            "" +
                    "| Header 1                        | Header 2 | Header 3 | Header 4 | Header 5                        |\n" +
                    "| val 11                          | val 12   | val 13   | val 14   | val 15                          |\n" +
                    "| some value longer then previous | val 22   | val 33   | val34    | and last value with extra space |\n";

    private static final String FORMATTED_TABLE_WITH_COMMENT =
            "" +
                    "| Header 1                                              | Header 2 | Header 3 | Header 4 | Header 5                                              |\n" +
                    "|-- this is commented row that should be left commented | val 12   | val 13   | val 14   | this is commented row that should be left commented --|\n" +
                    "| some value longer then previous                       | val 22   | val 33   | val34    | val35                                                 |\n";

    @Test
    public void formatsTableDelimitingColumnsWithPipes() {
        PipeTable pipeTable = aPipeTable()
                .addRow("Header 1", "Header 2", "Header 3", "Header 4", "Header 5")
                .addRow("val 11", "val 12", "val 13", "val 14", "val 15")
                .addRow("some value longer then previous", "val 22", "val 33", "val34", "and last value with extra space")
                .build();

        String formattedText = formatter().format(pipeTable);

        assertThat(formattedText, is(FORMATTED_TABLE));
    }

    @Test
    public void preservesCommentedRow() {
        PipeTable pipeTable = aPipeTable()
                .addRow("Header 1", "Header 2", "Header 3", "Header 4", "Header 5")
                .addRow("this is commented row that should be left commented", "val 12", "val 13", "val 14", "this is commented row that should be left commented").commented()
                .addRow("some value longer then previous", "val 22", "val 33", "val34", "val35")
                .build();

        String formattedText = formatter().format(pipeTable);

        assertThat(formattedText, is(FORMATTED_TABLE_WITH_COMMENT));
    }

    @Test
    public void preservesIndentation() {
        PipeTable pipeTable = aPipeTable()
                .addRow("row1.val1", "row1.val2", "row1.val3").withIndentation("    ")
                .addRow("row2.val1", "row2.val2", "row2.val3").withIndentation("    ")
                .addRow("row3.val1", "row3.val2", "row3.val3").withIndentation("    ")
                .build();

        String formattedText = formatter().withOptions(formatOptions().preserveOuterState()).format(pipeTable);

        assertThat(formattedText, is("" +
                "    | row1.val1 | row1.val2 | row1.val3 |\n" +
                "    | row2.val1 | row2.val2 | row2.val3 |\n" +
                "    | row3.val1 | row3.val2 | row3.val3 |\n"
        ));
    }


    @Test
    public void preservesAbsenceOfOuterPipes() {
        PipeTable pipeTable = aPipeTable()
                .addRow("row1.val1", "row1.val2", "row1.val3").withIndentation("    ").withoutLeadingPipe().withoutTrailingPipe()
                .addRow("row2.val1", "row2.val2", "row2.val3").withIndentation("    ").withoutLeadingPipe().withoutTrailingPipe()
                .addRow("row3.val1", "row3.val2", "row3.val3").withIndentation("    ").withoutLeadingPipe().withoutTrailingPipe()
                .build();

        String formattedText = formatter().withOptions(formatOptions().preserveOuterState()).format(pipeTable);

        assertThat(formattedText, is("" +
                "    row1.val1 | row1.val2 | row1.val3\n" +
                "    row2.val1 | row2.val2 | row2.val3\n" +
                "    row3.val1 | row3.val2 | row3.val3\n"
        ));
    }

}
