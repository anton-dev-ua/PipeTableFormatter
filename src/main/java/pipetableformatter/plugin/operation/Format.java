package pipetableformatter.plugin.operation;

import pipetableformatter.PipeTable;
import pipetableformatter.PipeTableFormatter;
import pipetableformatter.PipeTableParser;

import static pipetableformatter.FormatOptions.defaultFormatOptions;

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
            String formattedText = new PipeTableFormatter().format(pipeTable, defaultFormatOptions());
            utility.replaceText(formattedText);
        }
    }

}
