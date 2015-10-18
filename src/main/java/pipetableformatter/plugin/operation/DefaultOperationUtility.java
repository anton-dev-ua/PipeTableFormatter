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

    private SelectionModel getSelectionModel() {
        return editor.getSelectionModel();
    }

    @Override
    public TableText getSelectedText() {
        return new TableText(
                getSelectionModel().getSelectedText(),
                new Range(getSelectionModel().getSelectionStart(), getSelectionModel().getSelectionEnd())
                );
    }

    @Override
    public String getText() {
        return editor.getDocument().getText();
    }

    @Override
    public int getCaretPosition() {
        return editor.getCaretModel().getOffset();
    }

    @Override
    public void replaceText(String newText, Range tableRange) {
        getSelectionModel().setSelection(tableRange.getStart(), tableRange.getEnd());
        editor.getDocument().replaceString(tableRange.getStart(), tableRange.getEnd(), newText);
    }

    @Override
    public void setSelection(Range range) {
        getSelectionModel().setSelection(range.getStart(), range.getEnd());
    }
}
