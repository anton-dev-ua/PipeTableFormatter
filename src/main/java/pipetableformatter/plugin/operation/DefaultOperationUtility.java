package pipetableformatter.plugin.operation;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import pipetableformatter.Range;
import pipetableformatter.TableDetector;

public class DefaultOperationUtility implements OperationUtility {
    protected final Editor editor;

    public DefaultOperationUtility(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void autoselectTableIfNotSelected() {
        if(getSelectionModel().hasSelection()) {
            return;
        }

        int currentPosition = editor.getCaretModel().getOffset();
        String text = editor.getDocument().getText();
        Range tableRange = new TableDetector(text).find(currentPosition);

        if (Range.EMPTY != tableRange) {
            getSelectionModel().setSelection(tableRange.getStart(), tableRange.getEnd());
        }
    }

    private SelectionModel getSelectionModel() {
        return editor.getSelectionModel();
    }

    @Override
    public void replaceText(String formattedText) {
        SelectionModel selectionModel = getSelectionModel();
        editor.getDocument().replaceString(
                selectionModel.getSelectionStart(),
                selectionModel.getSelectionEnd(),
                formattedText
        );
    }

    @Override
    public int getCaretPositionInSelection() {
        SelectionModel selectionModel = getSelectionModel();
        int currentPosition = editor.getCaretModel().getOffset();
        int selectionStart = selectionModel.getSelectionStart();
        return currentPosition - selectionStart;
    }

    @Override
    public String getSelectedText() {
        return getSelectionModel().getSelectedText();
    }
}
