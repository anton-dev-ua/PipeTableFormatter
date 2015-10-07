package pipetableformatter;

import static pipetableformatter.FormatOptions.*;

public class PipeTableController {

    public String addColumnAndFormat(String text, int caretPosition) {
        PipeTableParser pipeTableParser = new PipeTableParser(text).withDetectingCellByCaretPosition(caretPosition);
        PipeTable pipeTable = pipeTableParser.parse();
        pipeTable.addColumnBefore(pipeTableParser.getSelectedColumn());
        return new PipeTableFormatter().format(pipeTable, formatOptions());
    }

    public String format(String text) {
        return format(text, formatOptions());
    }

    public String format(String text, FormatOptions formatOptions) {
        PipeTable pipeTable = new PipeTableParser(text).parse();
        return new PipeTableFormatter().format(pipeTable, formatOptions);
    }
}
