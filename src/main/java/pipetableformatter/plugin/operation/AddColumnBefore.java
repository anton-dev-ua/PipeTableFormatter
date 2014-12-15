package pipetableformatter.plugin.operation;

import com.intellij.openapi.editor.Editor;
import pipetableformatter.PipeTableController;

public class AddColumnBefore implements Runnable {

    private OperationUtility utility;
    private PipeTableController pipeTableController = new PipeTableController();

    public AddColumnBefore(Editor editor) {
        utility = new OperationUtility(editor);
    }


    @Override
    public void run() {
        utility.autoselectTableIfNotSelected();

        addColumnAndFormat();
    }

    private void addColumnAndFormat() {
        String text = utility.getSelectedText();
        if (text != null) {
            int caretPositionInSelection = utility.getCaretPositionInSelection();
            String formattedText = pipeTableController.addColumnAndFormat(text, caretPositionInSelection);
            utility.replaceText(formattedText);
        }
    }

}
