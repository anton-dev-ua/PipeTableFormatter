package pipetableformatter.plugin.operation;

import pipetableformatter.Range;
import pipetableformatter.TableDetector;

public abstract class Operation implements Runnable {
    protected TableText getTextToFormat(OperationUtility utility) {
        TableText tableText = utility.getSelectedText();
        if(tableText.isEmpty()) {
            String text = utility.getText();
            int caretPosition = utility.getCaretPosition();
            Range range = new TableDetector(text).findAround(caretPosition);
            tableText = new TableText(text.substring(range.getStart(), range.getEnd()), range);
        }
        return tableText;
    }
}
