package pipetableformatter.plugin;

import com.intellij.openapi.editor.SelectionModel;
import com.intellij.testFramework.builders.JavaModuleFixtureBuilder;
import com.intellij.testFramework.fixtures.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PipeTableFormatterFunctionalTest {

    private static final String TEXT_WITH_NOT_FORMATTED_TABLE = loadFile("input/text-with-not-formatted-table.txt");
    private static final String TEXT_WITH_FORMATTED_TABLE = loadFile("expected/text-with-formatted-table.txt");
    private static final String TEXT_WITH_FORMATTED_TABLE_AND_NEW_COLUMN = loadFile("expected/text-with-formatted-table-and-new-column.txt");
    private static final String TEXT_WITH_FORMATTED_TABLE_WITHOUT_OUTER_PIPES =  loadFile("expected/text-with-formatted-table-without-outer-pipes.txt");

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


    private static String loadFile(String fileName) {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);

        StringBuilder stringBuilder = new StringBuilder();
        try {
            byte[] chunk = new byte[512];
            for (int count = inputStream.read(chunk); count >= 0; count = inputStream.read(chunk)) {
                stringBuilder.append(new String(chunk, 0, count, Charset.defaultCharset()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        
        return stringBuilder.toString();
    }

}