package pipetableformatter.operation;

import org.junit.Before;
import org.junit.Test;
import pipetableformatter.Range;

import static org.mockito.Mockito.*;

public class FormatAllTablesOperationTest {

    private static final String TEXT_WITH_A_FEW_NOT_FORMATTED_TABLES = "" +
            "Story: Countries\n" +
            "\n" +
            "Scenario: Country currency\n" +
            "When country is <Country>\n" +
            "Then currency is <Currency>\n" +
            "\n" +
            "Examples:\n" +
            "|Country|Currency|\n" +
            "|United States of America|US dollar|\n" +
            "|Canada|Canadian<caret> dollar|\n" +
            "|United Kingdom|pound sterling|\n" +
            "|Republic of Poland|zloty|\n" +
            "\n" +
            "\n" +
            "Scenario: Country Population\n" +
            "When country is <Country>\n" +
            "Then currency is <Population>\n" +
            "\n" +
            "Examples:\n" +
            "Country,Population\n" +
            "United States of America,316 million\n" +
            "Canada,34.7 million\n" +
            "United Kingdom,62.8 million\n" +
            "Republic of Poland,38.3 million\n" +
            "\n" +
            "\n" +
            "Scenario: Country Area\n" +
            "When country is <Country>\n" +
            "Then currency is <Area>\n" +
            "\n" +
            "Examples:\n" +
            "|Country|Area|\n" +
            "|United States of America|9.8 million sq km|\n" +
            "|Canada|9.9 million sq km|\n" +
            "|United Kingdom|242,514 sq km|\n" +
            "|Republic of Poland|312,685 sq km|\n";

    private static final String FORMATTED_TABLE_1 = "" +
            "| Country                  | Currency               |\n" +
            "| United States of America | US dollar              |\n" +
            "| Canada                   | Canadian<caret> dollar |\n" +
            "| United Kingdom           | pound sterling         |\n" +
            "| Republic of Poland       | zloty                  |";

    private static final String FORMATTED_TABLE_2 = "" +
            "| Country                  | Population   |\n" +
            "| United States of America | 316 million  |\n" +
            "| Canada                   | 34.7 million |\n" +
            "| United Kingdom           | 62.8 million |\n" +
            "| Republic of Poland       | 38.3 million |";

    private static final String FORMATTED_TABLE_3 = "" +
            "| Country                  | Area              |\n" +
            "| United States of America | 9.8 million sq km |\n" +
            "| Canada                   | 9.9 million sq km |\n" +
            "| United Kingdom           | 242,514 sq km     |\n" +
            "| Republic of Poland       | 312,685 sq km     |";

    private static final String TEXT_WITH_SINGLE_LINE_WITH_DELIMITERS = "" +
            "Scenario: Country strength\n" +
            "\n" +
            "When Country has raising GDP, export over import\n" +
            "Then Country is prosperous\n" +
            "\n";

    PipeTableEditor editor;

    @Before
    public void before() {
        editor = mock(PipeTableEditor.class);
    }

    @Test
    public void formatsAllTablesInTheText() {
        when(editor.getText()).thenReturn(TEXT_WITH_A_FEW_NOT_FORMATTED_TABLES);

        new FormatAllTables(editor).run();

        verify(editor).getText();
        verify(editor).replaceText(FORMATTED_TABLE_1, new Range(110, 256));
        verify(editor).replaceText(FORMATTED_TABLE_2, new Range(478, 613));
        verify(editor).replaceText(FORMATTED_TABLE_3, new Range(784, 936));
        verifyNoMoreInteractions(editor);

    }

    @Test
    public void ignoresSingleLineWithDelimiters() {
        when(editor.getText()).thenReturn(TEXT_WITH_SINGLE_LINE_WITH_DELIMITERS);
        
        new FormatAllTables(editor).run();
        
        verify(editor).getText();
        verifyNoMoreInteractions(editor);
    }

}