package pipetableformatter.plugin.operation;

import pipetableformatter.Range;

public interface OperationUtility {
    TableText getSelectedText();
    String getText();
    int getCaretPosition();
    void replaceText(String formattedText, Range tableRange);
    void setSelection(Range range);
}
