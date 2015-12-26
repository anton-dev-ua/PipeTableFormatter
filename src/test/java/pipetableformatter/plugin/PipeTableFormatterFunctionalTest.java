package pipetableformatter.plugin;

import com.intellij.openapi.editor.SelectionModel;
import com.intellij.testFramework.builders.JavaModuleFixtureBuilder;
import com.intellij.testFramework.fixtures.*;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pipetableformatter.testsupport.Utils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static pipetableformatter.testsupport.Utils.loadFile;

public class PipeTableFormatterFunctionalTest {

    private static final String TEXT_WITH_NOT_FORMATTED_TABLE = loadFile("input/text-with-not-formatted-table.txt");
    private static final String TEXT_WITH_NOT_FORMATTED_TABLE_WITH_COMMENT = loadFile("input/text-with-not-formatted-table-with-comments.txt");
    private static final String TEXT_WITH_A_FEW_NOT_FORMATTED_TABLE = loadFile("input/text-with-a-few-not-formatted-table.txt");
    private static final String TEXT_WITH_A_PIPE_TABLE_WITH_A_LOT_OF_COMMAS = loadFile("input/text-with-a-pipe-table-with-a-lot-commas.txt");
    
    private static final String TEXT_WITH_FORMATTED_TABLE = loadFile("expected/text-with-formatted-table.txt");
    private static final String TEXT_WITH_FORMATTED_TABLE_WITH_COMMENT = loadFile("expected/text-with-formatted-table-with-comment.txt");
    private static final String TEXT_WITH_FORMATTED_TABLE_AND_NEW_COLUMN = loadFile("expected/text-with-formatted-table-and-new-column.txt");
    private static final String TEXT_WITH_FORMATTED_TABLE_WITHOUT_OUTER_PIPES = loadFile("expected/text-with-formatted-table-without-outer-pipes.txt");
    private static final String TEXT_WITH_ALL_FORMATTED_TABLES = loadFile("expected/text-with-all-formatted-table.txt");
    private static final String TEXT_WITH_A_FORMATTED_PIPE_TABLE_WITH_A_LOT_OF_COMMAS = loadFile("expected/text-with-a-formatted-pipe-table-with-a-lot-commas.txt");

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

        assertThat(textAfterActionApplied(), is(TEXT_WITH_FORMATTED_TABLE));
    }

    @Test
    public void formatsTableAroundCaretWithComments() throws Exception {
        myFixture.configureByText("test.story", TEXT_WITH_NOT_FORMATTED_TABLE_WITH_COMMENT);

        myFixture.performEditorAction("PipeTableFormatter.Format");

        assertThat(textAfterActionApplied(), is(TEXT_WITH_FORMATTED_TABLE_WITH_COMMENT));
    }


    @Test
    public void formatsPipeTableIgnoringCommas() {
        myFixture.configureByText("test.story", TEXT_WITH_A_PIPE_TABLE_WITH_A_LOT_OF_COMMAS);

        myFixture.performEditorAction("PipeTableFormatter.Format");

        assertThat(textAfterActionApplied(), is(TEXT_WITH_A_FORMATTED_PIPE_TABLE_WITH_A_LOT_OF_COMMAS));
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

        assertThat(textAfterActionApplied(), is(TEXT_WITH_FORMATTED_TABLE_AND_NEW_COLUMN));
    }

    @Test
    public void formatsTableWithoutOuterPipes() throws Exception {
        myFixture.configureByText("test.story", TEXT_WITH_NOT_FORMATTED_TABLE);

        myFixture.performEditorAction("PipeTableFormatter.FormatWithoutOuterPipes");

        assertThat(textAfterActionApplied(), is(TEXT_WITH_FORMATTED_TABLE_WITHOUT_OUTER_PIPES));
    }

    @Test
    public void formatsAllTablesInEditor() throws Exception {
        myFixture.configureByText("test.story", TEXT_WITH_A_FEW_NOT_FORMATTED_TABLE);

        myFixture.performEditorAction("PipeTableFormatter.FormatAllTables");

        assertThat(textAfterActionApplied(), is(TEXT_WITH_ALL_FORMATTED_TABLES));
    }

    @NotNull
    private String textAfterActionApplied() {
        return myFixture.getEditor().getDocument().getText();
    }
}