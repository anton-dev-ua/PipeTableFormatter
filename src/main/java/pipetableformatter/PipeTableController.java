package pipetableformatter;

public class PipeTableController {

    public String addColumnAndFormat(String text, int caretPosition) {
        PipeTable pipeTable = new PipeTableParser().parse(text, caretPosition);
        pipeTable.addColumnBeforeSelected();
        return new PipeTableFormatter().format(pipeTable);
    }

}
