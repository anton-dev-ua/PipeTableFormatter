package pipetableformatter.plugin.operation;

import com.intellij.openapi.editor.Editor;
import pipetableformatter.*;

public class Format implements Runnable {


    PipeTableController pipeTableController = new PipeTableController();
    OperationUtility utility;

    public Format(Editor editor) {
        utility = new OperationUtility(editor);
    }

    @Override
    public void run() {
        utility.autoselectTableIfNotSelected();

        formatTable();
    }

    private void formatTable() {
        String text = utility.getSelectedText();
        if (text != null) {
            String formattedText = pipeTableController.format(text);
            utility.replaceText(formattedText);
        }
    }

}
