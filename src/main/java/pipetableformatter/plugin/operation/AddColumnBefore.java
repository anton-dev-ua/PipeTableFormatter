package pipetableformatter.plugin.operation;

import pipetableformatter.PipeTable;
import pipetableformatter.PipeTableParser;

import static pipetableformatter.PipeTableFormatter.formatter;

public class AddColumnBefore extends Operation {

    private OperationUtility utility;

    public AddColumnBefore(OperationUtility anUtility) {
        utility = anUtility;
    }


    @Override
    public void run() {
        addColumnAndFormat();
    }

    private void addColumnAndFormat() {
        TableText tableText = getTextToFormat(utility);
        if (tableText.isNotEmpty()) {
            int caretPosition = utility.getCaretPosition();
            PipeTableParser pipeTableParser =
                    new PipeTableParser(tableText.getText())
                            .withDetectingCellByCaretPosition(caretPosition - tableText.getRange().getStart());
            PipeTable pipeTable = pipeTableParser.parse();
            pipeTable.addColumnBefore(pipeTableParser.getSelectedColumn());
            String formattedText = formatter().format(pipeTable);
            utility.replaceText(formattedText, tableText.getRange());
        }
    }

}
