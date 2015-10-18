package pipetableformatter.plugin.operation;

import org.junit.Before;
import org.junit.Test;
import pipetableformatter.Range;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class SelectOperationTest {

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

    OperationUtility utility;

    @Before
    public void before() {
        utility = mock(OperationUtility.class);
    }

    @Test
    public void selectsTable() {
        when(utility.getSelectedText()).thenReturn(new TableText(null, new Range(0, 0)));
        when(utility.getText()).thenReturn(TEXT_WITH_NOT_FORMATTED_TABLE);
        when(utility.getCaretPosition()).thenReturn(229);

        new Select(utility).run();

        verify(utility).setSelection(new Range(110, 380));
    }
}
