package pipetableformatter.plugin;

import com.intellij.openapi.editor.Editor;
import pipetableformatter.plugin.operation.DefaultOperationUtility;
import pipetableformatter.plugin.operation.Format;

import static pipetableformatter.FormatOptions.formatOptions;

public class PipeTableFormatWithoutOuterPipesAction extends AbstractPipeTableFormatAction {

    public PipeTableFormatWithoutOuterPipesAction() {
        super(new PipeTableActionHandler() {
            @Override
            protected Runnable action(Editor editor) {
                return new Format(new DefaultOperationUtility(editor), formatOptions().withoutOuterPipes());
            }
        });
    }

}
