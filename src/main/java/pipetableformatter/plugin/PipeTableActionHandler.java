package pipetableformatter.plugin;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

abstract class PipeTableActionHandler extends EditorActionHandler {

    @Override
    protected void doExecute(Editor editor, @Nullable Caret caret, final DataContext dataContext) {
        ApplicationManager.getApplication().runWriteAction(action(editor));
    }

    abstract protected Runnable action(Editor editor);

}
