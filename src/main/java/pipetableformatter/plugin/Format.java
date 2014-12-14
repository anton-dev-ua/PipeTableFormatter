package pipetableformatter.plugin;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import pipetableformatter.PipeTableFormatter;
import pipetableformatter.Range;
import pipetableformatter.TableDetector;

class Format implements Runnable {

    private PipeTableFormatter pipeTableFormatter = new PipeTableFormatter();
    private TableDetector tableDetector = new TableDetector();

    private final Editor editor;

    public Format(Editor editor) {
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
        Range tableRange = tableDetector.find(text, currentPosition);

        if (Range.EMPTY != tableRange) {
            selectionModel.setSelection(tableRange.getStart(), tableRange.getEnd());
        }
    }

    private void formatTable(SelectionModel selectionModel) {
        String text = selectionModel.getSelectedText();
        String formattedText = pipeTableFormatter.format(text);
        editor.getDocument().replaceString(
                selectionModel.getSelectionStart(),
                selectionModel.getSelectionEnd(),
                formattedText
        );
    }

}
