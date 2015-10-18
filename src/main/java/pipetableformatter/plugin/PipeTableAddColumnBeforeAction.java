package pipetableformatter.plugin;

import com.intellij.openapi.editor.Editor;
import pipetableformatter.plugin.operation.AddColumnBefore;
import pipetableformatter.plugin.operation.IdeaPipeTableEditor;

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
