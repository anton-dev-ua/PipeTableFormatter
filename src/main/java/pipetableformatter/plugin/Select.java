package pipetableformatter.plugin;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import pipetableformatter.PipeTableFormatter;
import pipetableformatter.Range;
import pipetableformatter.TableDetector;

public class Select implements Runnable {

    private TableDetector tableDetector = new TableDetector();
    private Editor editor;


    public Select(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void run() {
        autoselectTable(editor.getSelectionModel());
    }

    private void autoselectTable(SelectionModel selectionModel) {
        int currentPosition = editor.getCaretModel().getOffset();
        String text = editor.getDocument().getText();
        Range tableRange = tableDetector.find(text, currentPosition);

        if (Range.EMPTY != tableRange) {
            selectionModel.setSelection(tableRange.getStart(), tableRange.getEnd());
        }
    }
}
