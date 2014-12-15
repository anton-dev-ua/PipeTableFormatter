package pipetableformatter.plugin.operation;

import com.intellij.openapi.editor.Editor;

public class Select implements Runnable {


    private OperationUtility utility;

    public Select(Editor editor) {
        utility = new OperationUtility(editor);
    }

    @Override
    public void run() {
        utility.autoselectTableIfNotSelected();
    }

}
