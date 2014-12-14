package pipetableformatter.plugin;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import pipetableformatter.PipeTableController;
import pipetableformatter.Range;
import pipetableformatter.TableDetector;

public class AddColumnBefore  implements Runnable  {

    private PipeTableController pipeTableController = new PipeTableController();

    private final Editor editor;

    public AddColumnBefore(Editor editor) {
        this.editor = editor;
    }


    @Override
    public void run() {
        SelectionModel selectionModel = editor.getSelectionModel();

        if (!selectionModel.hasSelection()) {
            autoselectTable(selectionModel);
        }

        if (selectionModel.hasSelection()) {
            formatTable(selectionModel);
        }
    }

    private void autoselectTable(SelectionModel selectionModel) {
        int currentPosition = editor.getCaretModel().getOffset();
        String text = editor.getDocument().getText();
        Range tableRange = new TableDetector(text).find(currentPosition);

        if (Range.EMPTY != tableRange) {
            selectionModel.setSelection(tableRange.getStart(), tableRange.getEnd());
        }
    }

    private void formatTable(SelectionModel selectionModel) {
        int currentPosition = editor.getCaretModel().getOffset();
        int selectionStart = selectionModel.getSelectionStart();
        String text = selectionModel.getSelectedText();
        String formattedText = pipeTableController.addColumnAndFormat(text, currentPosition - selectionStart);
        editor.getDocument().replaceString(
                selectionModel.getSelectionStart(),
                selectionModel.getSelectionEnd(),
                formattedText
        );
    }
}
