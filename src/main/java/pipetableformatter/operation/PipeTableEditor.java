package pipetableformatter.operation;

import pipetableformatter.Range;

public interface PipeTableEditor {
    TableText getSelectedText();
    String getText();
    int getCaretPosition();
    void replaceText(String formattedText, Range tableRange);
    void setSelection(Range range);
}
