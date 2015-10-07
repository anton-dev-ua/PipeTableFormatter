package pipetableformatter.plugin.operation;

import com.intellij.openapi.editor.Editor;
import pipetableformatter.PipeTableController;

import static pipetableformatter.FormatOptions.formatOptions;

public class FormatWithoutOuterPipes implements Runnable  {

    PipeTableController pipeTableController = new PipeTableController();
    OperationUtility utility;

    public FormatWithoutOuterPipes(Editor editor) {
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
            String formattedText = pipeTableController.format(text, formatOptions().withoutOuterPipes());
            utility.replaceText(formattedText);
        }
    }
}
