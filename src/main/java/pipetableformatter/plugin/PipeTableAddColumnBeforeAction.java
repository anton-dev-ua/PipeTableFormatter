package pipetableformatter.plugin;

import com.intellij.openapi.editor.Editor;
import pipetableformatter.operation.AddColumnBefore;

public class PipeTableAddColumnBeforeAction extends AbstractPipeTableFormatAction {
    public PipeTableAddColumnBeforeAction() {
        super(new PipeTableActionHandler() {
            @Override
            protected Runnable action(Editor editor) {
                return new AddColumnBefore(new IdeaPipeTableEditor(editor));
            }
        });
    }
}
