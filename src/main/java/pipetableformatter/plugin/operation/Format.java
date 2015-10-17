package pipetableformatter.plugin.operation;

import pipetableformatter.PipeTable;
import pipetableformatter.PipeTableParser;

import static pipetableformatter.PipeTableFormatter.formatter;

public class Format implements Runnable {

    OperationUtility utility;

    public Format(OperationUtility anUtility) {
        utility = anUtility;
    }

    @Override
    public void run() {
        utility.autoselectTableIfNotSelected();

        formatTable();
    }

    private void formatTable() {
        String text = utility.getSelectedText();
        if (text != null) {
            PipeTable pipeTable = new PipeTableParser(text).parse();
            String formattedText = formatter().format(pipeTable);
            utility.replaceText(formattedText);
        }
    }

}
