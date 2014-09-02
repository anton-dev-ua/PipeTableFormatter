package pipetableformatter;

import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;

public class PipeTableFormatterAction extends EditorAction {

    public PipeTableFormatterAction() {
        super(new MyHandler());
    }

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        if (ActionPlaces.isPopupPlace(e.getPlace())) {
            e.getPresentation().setVisible(e.getPresentation().isEnabled());
        }
    }

    private static class MyHandler extends EditorActionHandler {

        PipeTableFormatter pipeTableFormatter = new PipeTableFormatter();

        @Override
        public void execute(final Editor editor, final DataContext dataContext) {
            ApplicationManager.getApplication().runWriteAction(
                    new Runnable() {
                        @Override
                        public void run() {

                            SelectionModel selectionModel = editor.getSelectionModel();

                            if (!selectionModel.hasSelection()) {
                                selectionModel.selectLineAtCaret();
                            }

                            final String text = selectionModel.getSelectedText();

                            String formattedText = pipeTableFormatter.format(text);

                            editor.getDocument().replaceString(selectionModel.getSelectionStart(), selectionModel.getSelectionEnd(), formattedText);
                        }

                    });
        }
    }
}
