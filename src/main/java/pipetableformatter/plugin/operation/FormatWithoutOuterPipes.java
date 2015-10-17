package pipetableformatter.plugin.operation;

import pipetableformatter.PipeTable;
import pipetableformatter.PipeTableFormatter;
import pipetableformatter.PipeTableParser;

import static pipetableformatter.FormatOptions.defaultFormatOptions;

public class FormatWithoutOuterPipes implements Runnable  {

    OperationUtility utility;

    public FormatWithoutOuterPipes(OperationUtility anUtility) {
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
            String formattedText = new PipeTableFormatter().format(pipeTable, defaultFormatOptions().withoutOuterPipes());
            utility.replaceText(formattedText);
        }
    }
}
