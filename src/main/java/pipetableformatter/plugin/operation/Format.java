package pipetableformatter.plugin.operation;

import pipetableformatter.FormatOptions;
import pipetableformatter.PipeTable;
import pipetableformatter.PipeTableParser;

import static pipetableformatter.FormatOptions.formatOptions;
import static pipetableformatter.PipeTableFormatter.formatter;

public class Format extends Operation {

    OperationUtility utility;
    private final FormatOptions options;

    public Format(OperationUtility utility) {
        this(utility, formatOptions());
    }

    public Format(OperationUtility utility, FormatOptions formatOptions) {
        this.utility = utility;
        this.options = formatOptions;
    }

    @Override
    public void run() {
        formatTable();
    }

    private void formatTable() {
        TableText tableText = getTextToFormat(utility);
        if (tableText.isNotEmpty()) {
            PipeTable pipeTable = new PipeTableParser(tableText.getText()).parse();
            String formattedText = formatter().withOptions(options).format(pipeTable);
            utility.replaceText(formattedText, tableText.getRange());
        }
    }

}
