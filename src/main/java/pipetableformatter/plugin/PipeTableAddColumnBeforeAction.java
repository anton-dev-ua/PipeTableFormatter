package pipetableformatter.plugin;

import com.intellij.openapi.editor.Editor;

public class PipeTableAddColumnBeforeAction extends AbstractPipeTableFormatAction {
    public PipeTableAddColumnBeforeAction() {
        super(new PipeTableActionHandler() {
            @Override
            protected Runnable action(Editor editor) {
                return new AddColumnBefore(editor);
            }
        });
    }
}
