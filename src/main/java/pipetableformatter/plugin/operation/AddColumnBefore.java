package pipetableformatter.plugin.operation;

import pipetableformatter.PipeTable;
import pipetableformatter.PipeTableParser;

import static pipetableformatter.PipeTableFormatter.formatter;

public class AddColumnBefore extends Operation {

    private PipeTableEditor editor;

    public AddColumnBefore(PipeTableEditor editor) {
        this.editor = editor;
    }


    @Override
    public void run() {
        addColumnAndFormat();
    }

    private void addColumnAndFormat() {
        TableText tableText = getTextToFormat(editor);
        if (tableText.isNotEmpty()) {
            int caretPosition = editor.getCaretPosition();
            PipeTableParser pipeTableParser =
                    new PipeTableParser(tableText.getText())
                            .withDetectingCellByCaretPosition(caretPosition - tableText.getRange().getStart());
            PipeTable pipeTable = pipeTableParser.parse();
            pipeTable.addColumnBefore(pipeTableParser.getSelectedColumn());
            String formattedText = formatter().format(pipeTable);
            editor.replaceText(formattedText, tableText.getRange());
        }
    }

}
