package pipetableformatter.plugin.operation;

public class Select implements Runnable {


    private OperationUtility utility;

    public Select(OperationUtility anUtility) {
        utility = anUtility;
    }

    @Override
    public void run() {
        utility.autoselectTableIfNotSelected();
    }

}
