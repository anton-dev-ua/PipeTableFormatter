package pipetableformatter.plugin;

import com.intellij.openapi.editor.Editor;
import pipetableformatter.operation.Format;

public class PipeTableFormatAction extends AbstractPipeTableFormatAction {

    public PipeTableFormatAction() {
        super(new PipeTableActionHandler() {
            @Override
            protected Runnable action(Editor editor) {
                return new Format(new IdeaPipeTableEditor(editor));
            }
        });
    }

}
