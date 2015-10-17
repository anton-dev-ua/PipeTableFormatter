package pipetableformatter.plugin.operation;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class FormatWithoutOuterPipesOperationTest {

    private static final String NOT_FORMATTED_TABLE = "" +
            "|Country|Currency|Population|Area|\n" +
            "|United States of America|US dollar|316 million|9.8 million sq km|\n" +
            "|Canada|Canadian dollar|34.7 million|9.9 million sq km|\n" +
            "|United Kingdom|pound sterling|62.8 million|242,514 sq km|\n" +
            "|Republic of Poland|zloty|38.3 million|312,685 sq km|";

    protected static final String FORMATTED_TABLE = "" +
            " Country                  | Currency        | Population   | Area              \n" +
            " United States of America | US dollar       | 316 million  | 9.8 million sq km \n" +
            " Canada                   | Canadian dollar | 34.7 million | 9.9 million sq km \n" +
            " United Kingdom           | pound sterling  | 62.8 million | 242,514 sq km     \n" +
            " Republic of Poland       | zloty           | 38.3 million | 312,685 sq km     ";


    OperationUtility utility;

    @Before
    public void before() {
        utility = mock(OperationUtility.class);
        when(utility.getSelectedText()).thenReturn(NOT_FORMATTED_TABLE);
    }

    @Test
    public void selectsAndFormatsTable() {

        new FormatWithoutOuterPipes(utility).run();

        verify(utility).replaceText(FORMATTED_TABLE);

    }
}
