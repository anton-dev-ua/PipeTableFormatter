package pipetableformatter.plugin;

import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;

public class AbstractPipeTableFormatAction extends EditorAction {
    public AbstractPipeTableFormatAction(EditorActionHandler defaultHandler) {
        super(defaultHandler);
    }

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        if (ActionPlaces.isPopupPlace(e.getPlace())) {
            e.getPresentation().setVisible(e.getPresentation().isEnabled());
        }
    }
}
