package pipetableformatter.plugin.operation;

import pipetableformatter.PipeTableController;

import static pipetableformatter.FormatOptions.formatOptions;

public class FormatWithoutOuterPipes implements Runnable  {

    PipeTableController pipeTableController = new PipeTableController();
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
            String formattedText = pipeTableController.format(text, formatOptions().withoutOuterPipes());
            utility.replaceText(formattedText);
        }
    }
}
