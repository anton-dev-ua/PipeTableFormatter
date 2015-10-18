package pipetableformatter.operation;

import pipetableformatter.PipeTable;
import pipetableformatter.PipeTableParser;

import static pipetableformatter.PipeTableFormatter.formatter;

public class AddColumnBefore extends Operation {

    private final PipeTableEditor editor;

    public AddColumnBefore(PipeTableEditor editor) {
        this.editor = editor;
    }

    @Override
    protected void perform() {
        TableText tableText = getSelectedTable(editor);
        if (tableText.isNotEmpty()) {
            PipeTable pipeTable = parseTable(tableText, editor.getCaretPosition());
            pipeTable.addColumnBefore(pipeTable.getSelectedColumn());
            String formattedText = formatter().format(pipeTable);
            editor.replaceText(formattedText, tableText.getRange());
        }
    }

    private PipeTable parseTable(TableText tableText, int caretPosition) {
        return new PipeTableParser(tableText.getText())
                .detectingCellByPosition(caretPosition - tableText.getRange().getStart())
                .parse();
    }

}
