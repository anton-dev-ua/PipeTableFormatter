package pipetableformatter;

public class PipeTableController {

    public String addColumnAndFormat(String text, int caretPosition) {
        PipeTableParser pipeTableParser = new PipeTableParser(text).withDetectingCellByCaretPosition(caretPosition);
        PipeTable pipeTable = pipeTableParser.parse();
        pipeTable.addColumnBefore(pipeTableParser.getSelectedColumn());
        return new PipeTableFormatter().format(pipeTable);
    }

}
