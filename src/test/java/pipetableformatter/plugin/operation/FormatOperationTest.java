package pipetableformatter.plugin.operation;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class FormatOperationTest {

    private static final String TEXT_WITH_TABLE = "" +
            "Story:\n" +
            "\n" +
            "Meta:\n" +
            "\n" +
            "Scenario\n" +
            "\n" +
            "Examples:\n" +
            "|Country|Currency|Population|Area|\n" +
            "|United States of America|US dollar|316 million|9.8 million sq km|\n" +
            "|Canada|Canadian dollar|34.7 million|9.9 million sq km|\n" +
            "|United Kingdom|pound sterling|62.8 million|242,514 sq km|\n" +
            "|Republic of Poland|zloty|38.3 million|312,685 sq km|\n";

    public static final int CARET_POSITION_INSIDE_A_TABLE = 160;
    public static final int TABLE_START_POSITION = 35;
    public static final int TABLE_END_POSITION = 305;

    public static final String SELECTED_TABLE = TEXT_WITH_TABLE.substring(TABLE_START_POSITION, TABLE_END_POSITION);

    private static final String FORMATED_TABLE = "" +
            "| Country                  | Currency        | Population   | Area              |\n" +
            "| United States of America | US dollar       | 316 million  | 9.8 million sq km |\n" +
            "| Canada                   | Canadian dollar | 34.7 million | 9.9 million sq km |\n" +
            "| United Kingdom           | pound sterling  | 62.8 million | 242,514 sq km     |\n" +
            "| Republic of Poland       | zloty           | 38.3 million | 312,685 sq km     |";
    private SelectionModel selectionModel;
    private Document document;
    private Editor editor;

    @Test
    public void selectsAndFormatsTable() {

        new Format(editor).run();

        verify(selectionModel).setSelection(TABLE_START_POSITION, TABLE_END_POSITION);
        verify(document).replaceString(TABLE_START_POSITION, TABLE_END_POSITION, FORMATED_TABLE);

    }

    @Before
    public void setUp() {
        editor = mock(Editor.class);
        selectionModel = mock(SelectionModel.class);
        when(selectionModel.hasSelection()).thenReturn(false);
        when(selectionModel.getSelectedText()).thenReturn(SELECTED_TABLE);
        when(selectionModel.getSelectionStart()).thenReturn(TABLE_START_POSITION);
        when(selectionModel.getSelectionEnd()).thenReturn(TABLE_END_POSITION);

        CaretModel caretModel = mock(CaretModel.class);
        when(caretModel.getOffset()).thenReturn(CARET_POSITION_INSIDE_A_TABLE);

        document = mock(Document.class);
        when(document.getText()).thenReturn(TEXT_WITH_TABLE);

        when(editor.getSelectionModel()).thenReturn(selectionModel);
        when(editor.getCaretModel()).thenReturn(caretModel);
        when(editor.getDocument()).thenReturn(document);

        Application application = mock(Application.class);
        Disposable parent = mock(Disposable.class);
        ApplicationManager.setApplication(application, parent);
    }
}