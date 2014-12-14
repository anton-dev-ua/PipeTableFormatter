package pipetableformatter.plugin;

import com.intellij.openapi.editor.Editor;

public class PipeTableFormatAction extends AbstractPipeTableFormatAction {

    public PipeTableFormatAction() {
        super(new PipeTableActionHandler() {
            @Override
            protected Runnable action(Editor editor) {
                return new Format(editor);
            }
        });
    }

}
