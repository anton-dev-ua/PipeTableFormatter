package pipetableformatter.plugin;

import com.intellij.openapi.editor.Editor;
import pipetableformatter.plugin.operation.Select;

public class PipeTableSelectAction extends AbstractPipeTableFormatAction {
    public PipeTableSelectAction() {
        super(new PipeTableActionHandler() {
            @Override
            protected Runnable action(Editor editor) {
                return new Select(editor);
            }
        });
    }
}
