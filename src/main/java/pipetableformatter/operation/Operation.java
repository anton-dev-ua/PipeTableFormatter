package pipetableformatter.operation;

import pipetableformatter.Range;
import pipetableformatter.TableDetector;

import static pipetableformatter.TableDetector.detectTableIn;

public abstract class Operation implements Runnable {
    
    @Override
    final public void run() {
        perform();
    }

    protected abstract void perform();

    final protected TableText getSelectedTable(PipeTableEditor editor) {
        TableText tableText = editor.getSelectedText();
        if(tableText.isEmpty()) {
            String text = editor.getText();
            int caretPosition = editor.getCaretPosition();
            Range range = detectTableIn(text).around(caretPosition);
            tableText = new TableText(text.substring(range.getStart(), range.getEnd()), range);
        }
        return tableText;
    }
}
