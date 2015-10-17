package pipetableformatter.plugin.operation;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class FormatOperationTest {

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


    OperationUtility utility;

    @Before
    public void before() {
        utility = mock(OperationUtility.class);
    }

    @Test
    public void formatsTable() {
        when(utility.getSelectedText()).thenReturn(NOT_FORMATTED_TABLE);

        new Format(utility).run();

        verify(utility).replaceText(FORMATTED_TABLE);
    }

    @Test
    public void formatsCsvTableAndReplaceCommaWithPipe() {
        when(utility.getSelectedText()).thenReturn(NOT_FORMATTED_CSV_TABLE);

        new Format(utility).run();

        verify(utility).replaceText(FORMATTED_CSV_TABLE);
    }

}