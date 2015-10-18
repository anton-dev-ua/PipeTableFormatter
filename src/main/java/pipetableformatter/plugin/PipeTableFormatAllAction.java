package pipetableformatter.plugin;

import com.intellij.openapi.editor.Editor;
import pipetableformatter.operation.FormatAllTables;

public class PipeTableFormatAllAction extends AbstractPipeTableFormatAction {
    
    public PipeTableFormatAllAction() {
        super(new PipeTableActionHandler() {
            @Override
            protected Runnable action(Editor editor) {
                return new FormatAllTables(new IdeaPipeTableEditor(editor));
            }
        });
    }
}
