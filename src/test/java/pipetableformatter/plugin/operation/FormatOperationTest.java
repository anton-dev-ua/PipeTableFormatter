package pipetableformatter.plugin.operation;

import org.junit.Before;
import org.junit.Test;
import pipetableformatter.Range;

import static org.mockito.Mockito.*;
import static pipetableformatter.FormatOptions.formatOptions;

public class FormatOperationTest {

    private static final String TEXT_WITH_NOT_FORMATTED_TABLE = "" +
            "Story: Countries\n" +
            "\n" +
            "Scenario: Country currency\n" +
            "When country is <Country>\n" +
            "Then currency is <Currency>\n" +
            "\n" +
            "Examples:\n" +
            "|Country|Currency|Population|Area|\n" +
            "|United States of America|US dollar|316 million|9.8 million sq km|\n" +
            "|Canada|Canadian dollar|34.7 million|9.9 million sq km|\n" +
            "|United Kingdom|pound sterling|62.8 million|242,514 sq km|\n" +
            "|Republic of Poland|zloty|38.3 million|312,685 sq km|";

    private static final String NOT_FORMATTED_TABLE = "" +
            "|Country|Currency|Population|Area|\n" +
            "|United States of America|US dollar|316 million|9.8 million sq km|\n" +
            "|Canada|Canadian dollar|34.7 million|9.9 million sq km|\n" +
            "|United Kingdom|pound sterling|62.8 million|242,514 sq km|\n" +
            "|Republic of Poland|zloty|38.3 million|312,685 sq km|";

    protected static final String FORMATTED_TABLE = "" +
            "| Country                  | Currency        | Population   | Area              |\n" +
            "| United States of America | US dollar       | 316 million  | 9.8 million sq km |\n" +
            "| Canada                   | Canadian dollar | 34.7 million | 9.9 million sq km |\n" +
            "| United Kingdom           | pound sterling  | 62.8 million | 242,514 sq km     |\n" +
            "| Republic of Poland       | zloty           | 38.3 million | 312,685 sq km     |";


    private static final String NOT_FORMATTED_CSV_TABLE = "" +
            "Header 1,Header 2,   Header 3,Header 4  ,Header 5\n" +
            "val 11, val 12,val 13, val 14,val 15\n" +
            "some value longer then previous, val 22,val 33,val34,and last value with extra space                       \n";

    private static final String FORMATTED_CSV_TABLE = "" +
            "| Header 1                        | Header 2 | Header 3 | Header 4 | Header 5                        |\n" +
            "| val 11                          | val 12   | val 13   | val 14   | val 15                          |\n" +
            "| some value longer then previous | val 22   | val 33   | val34    | and last value with extra space |\n";

    protected static final String FORMATTED_TABLE_WITHOUT_OUTER_PIPES = "" +
            " Country                  | Currency        | Population   | Area              \n" +
            " United States of America | US dollar       | 316 million  | 9.8 million sq km \n" +
            " Canada                   | Canadian dollar | 34.7 million | 9.9 million sq km \n" +
            " United Kingdom           | pound sterling  | 62.8 million | 242,514 sq km     \n" +
            " Republic of Poland       | zloty           | 38.3 million | 312,685 sq km     ";


    PipeTableEditor utility;

    @Before
    public void before() {
        utility = mock(PipeTableEditor.class);
    }

    @Test
    public void formatsSelectedTable() {
        when(utility.getSelectedText()).thenReturn(new TableText(NOT_FORMATTED_TABLE, new Range(110, 380)));

        new Format(utility).run();

        verify(utility).replaceText(FORMATTED_TABLE, new Range(110, 380));
    }

    @Test
    public void findsAndFormatsTable() {
        when(utility.getSelectedText()).thenReturn(new TableText(null, new Range(0, 0)));
        when(utility.getText()).thenReturn(TEXT_WITH_NOT_FORMATTED_TABLE);
        when(utility.getCaretPosition()).thenReturn(229);

        new Format(utility).run();

        verify(utility).replaceText(FORMATTED_TABLE, new Range(110, 380));
    }

    @Test
    public void formatsCsvTableAndReplaceCommaWithPipe() {
        when(utility.getSelectedText()).thenReturn(new TableText(NOT_FORMATTED_CSV_TABLE, new Range(110, 380)));

        new Format(utility).run();

        verify(utility).replaceText(FORMATTED_CSV_TABLE, new Range(110, 380));
    }

    @Test
    public void formatsTableWithoutOuterPipes() {
        when(utility.getSelectedText()).thenReturn(new TableText(NOT_FORMATTED_TABLE, new Range(110, 380)));

        new Format(utility, formatOptions().withoutOuterPipes()).run();

        verify(utility).replaceText(FORMATTED_TABLE_WITHOUT_OUTER_PIPES, new Range(110, 380));
    }

}