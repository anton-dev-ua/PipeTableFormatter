package pipetableformatter.plugin.operation;

import pipetableformatter.PipeTable;
import pipetableformatter.PipeTableParser;

import static pipetableformatter.FormatOptions.formatOptions;
import static pipetableformatter.PipeTableFormatter.formatter;

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
            String formattedText = formatter().withOptions(formatOptions().withoutOuterPipes()).format(pipeTable);
            utility.replaceText(formattedText);
        }
    }
}
