package pipetableformatter.plugin.operation;

import pipetableformatter.PipeTableController;

public class Format implements Runnable {


    PipeTableController pipeTableController = new PipeTableController();
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
            String formattedText = pipeTableController.format(text);
            utility.replaceText(formattedText);
        }
    }

}
