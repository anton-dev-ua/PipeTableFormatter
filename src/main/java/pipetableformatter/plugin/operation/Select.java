package pipetableformatter.plugin.operation;

public class Select extends Operation {


    private OperationUtility utility;

    public Select(OperationUtility anUtility) {
        utility = anUtility;
    }

    @Override
    public void run() {
        TableText tableText = getTextToFormat(utility);
        if(tableText.isNotEmpty()) {
            utility.setSelection(tableText.getRange());
        }
    }

}
