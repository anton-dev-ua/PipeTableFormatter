package pipetableformatter.plugin.operation;

import pipetableformatter.PipeTable;
import pipetableformatter.PipeTableParser;

import static pipetableformatter.PipeTableFormatter.formatter;

public class AddColumnBefore implements Runnable {

    private OperationUtility utility;

    public AddColumnBefore(OperationUtility anUtility) {
        utility = anUtility;
    }


    @Override
    public void run() {
        utility.autoselectTableIfNotSelected();

        addColumnAndFormat();
    }

    private void addColumnAndFormat() {
        String text = utility.getSelectedText();
        if (text != null) {
            int caretPositionInSelection = utility.getCaretPositionInSelection();
            PipeTableParser pipeTableParser = new PipeTableParser(text).withDetectingCellByCaretPosition(caretPositionInSelection);
            PipeTable pipeTable = pipeTableParser.parse();
            pipeTable.addColumnBefore(pipeTableParser.getSelectedColumn());
            String formattedText =  formatter().format(pipeTable);
            utility.replaceText(formattedText);
        }
    }

}
