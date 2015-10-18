package pipetableformatter.plugin;

import com.intellij.openapi.editor.SelectionModel;
import com.intellij.testFramework.builders.JavaModuleFixtureBuilder;
import com.intellij.testFramework.fixtures.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PipeTableFormatterFunctionalTest {

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
            "|Canada|Canadian<caret> dollar|34.7 million|9.9 million sq km|\n" +
            "|United Kingdom|pound sterling|62.8 million|242,514 sq km|\n" +
            "|Republic of Poland|zloty|38.3 million|312,685 sq km|";

    private static final String TEXT_WITH_FORMATTED_TABLE = "" +
            "Story: Countries\n" +
            "\n" +
            "Scenario: Country currency\n" +
            "When country is <Country>\n" +
            "Then currency is <Currency>\n" +
            "\n" +
            "Examples:\n" +
            "| Country                  | Currency        | Population   | Area              |\n" +
            "| United States of America | US dollar       | 316 million  | 9.8 million sq km |\n" +
            "| Canada                   | Canadian dollar | 34.7 million | 9.9 million sq km |\n" +
            "| United Kingdom           | pound sterling  | 62.8 million | 242,514 sq km     |\n" +
            "| Republic of Poland       | zloty           | 38.3 million | 312,685 sq km     |";

    private static final String TEXT_WITH_FORMATTED_TABLE_AND_NEW_COLUMN = "" +
            "Story: Countries\n" +
            "\n" +
            "Scenario: Country currency\n" +
            "When country is <Country>\n" +
            "Then currency is <Currency>\n" +
            "\n" +
            "Examples:\n" +
            "| Country                  |  | Currency        | Population   | Area              |\n" +
            "| United States of America |  | US dollar       | 316 million  | 9.8 million sq km |\n" +
            "| Canada                   |  | Canadian dollar | 34.7 million | 9.9 million sq km |\n" +
            "| United Kingdom           |  | pound sterling  | 62.8 million | 242,514 sq km     |\n" +
            "| Republic of Poland       |  | zloty           | 38.3 million | 312,685 sq km     |";

    private static final String TEXT_WITH_FORMATTED_TABLE_WITHOUT_OUTER_PIPES = "" +
            "Story: Countries\n" +
            "\n" +
            "Scenario: Country currency\n" +
            "When country is <Country>\n" +
            "Then currency is <Currency>\n" +
            "\n" +
            "Examples:\n" +
            " Country                  | Currency        | Population   | Area              \n" +
            " United States of America | US dollar       | 316 million  | 9.8 million sq km \n" +
            " Canada                   | Canadian dollar | 34.7 million | 9.9 million sq km \n" +
            " United Kingdom           | pound sterling  | 62.8 million | 242,514 sq km     \n" +
            " Republic of Poland       | zloty           | 38.3 million | 312,685 sq km     ";


    private CodeInsightTestFixture myFixture;


    @Before
    public void before() throws Exception {
        final IdeaTestFixtureFactory fixtureFactory = IdeaTestFixtureFactory.getFixtureFactory();
        final TestFixtureBuilder<IdeaProjectTestFixture> testFixtureBuilder = fixtureFactory.createFixtureBuilder("PipeTableFormatter");
        myFixture = JavaTestFixtureFactory.getFixtureFactory().createCodeInsightFixture(testFixtureBuilder.getFixture());
        final JavaModuleFixtureBuilder builder = testFixtureBuilder.addModule(JavaModuleFixtureBuilder.class);

        builder.addContentRoot(myFixture.getTempDirPath()).addSourceRoot("");
        builder.setMockJdkLevel(JavaModuleFixtureBuilder.MockJdkLevel.jdk15);

        myFixture.setUp();
    }

    @After
    public void after() throws Exception {
        myFixture.tearDown();
    }

    @Test
    public void formatsTableAroundCaret() throws Exception {
        myFixture.configureByText("test.story", TEXT_WITH_NOT_FORMATTED_TABLE);

        myFixture.performEditorAction("PipeTableFormatter.Format");

        String textAfterActionApplied = myFixture.getEditor().getDocument().getText();
        assertThat(textAfterActionApplied, is(TEXT_WITH_FORMATTED_TABLE));
    }

    @Test
    public void selectsTableAroundCaret() throws Exception {
        myFixture.configureByText("test.story", TEXT_WITH_NOT_FORMATTED_TABLE);

        myFixture.performEditorAction("PipeTableFormatter.SelectTable");

        SelectionModel selection = myFixture.getEditor().getSelectionModel();
        assertThat(selection.getSelectionStart(), is(110));
        assertThat(selection.getSelectionEnd(), is(380));
    }

    @Test
    public void addsColumn() {
        myFixture.configureByText("test.story", TEXT_WITH_NOT_FORMATTED_TABLE);

        myFixture.performEditorAction("PipeTableFormatter.AddColumnBefore");

        String textAfterActionApplied = myFixture.getEditor().getDocument().getText();
        assertThat(textAfterActionApplied, is(TEXT_WITH_FORMATTED_TABLE_AND_NEW_COLUMN));
    }

    @Test
    public void formatsTableWithoutOuterPipes() throws Exception {
        myFixture.configureByText("test.story", TEXT_WITH_NOT_FORMATTED_TABLE);

        myFixture.performEditorAction("PipeTableFormatter.FormatWithoutOuterPipes");

        String textAfterActionApplied = myFixture.getEditor().getDocument().getText();
        assertThat(textAfterActionApplied, is(TEXT_WITH_FORMATTED_TABLE_WITHOUT_OUTER_PIPES));
    }

}