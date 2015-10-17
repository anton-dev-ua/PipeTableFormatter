package pipetableformatter.plugin.operation;

public interface OperationUtility {
    void autoselectTableIfNotSelected();
    void replaceText(String formattedText);
    int getCaretPositionInSelection();
    String getSelectedText();
}
