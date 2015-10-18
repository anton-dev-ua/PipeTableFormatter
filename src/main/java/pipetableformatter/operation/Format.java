package pipetableformatter.operation;

import pipetableformatter.FormatOptions;
import pipetableformatter.PipeTable;
import pipetableformatter.PipeTableParser;

import static pipetableformatter.FormatOptions.formatOptions;
import static pipetableformatter.PipeTableFormatter.formatter;

public class Format extends Operation {

    private final PipeTableEditor editor;
    private final FormatOptions options;

    public Format(PipeTableEditor editor) {
        this(editor, formatOptions());
    }

    public Format(PipeTableEditor editor, FormatOptions formatOptions) {
        this.editor = editor;
        this.options = formatOptions;
    }

    @Override
    public void run() {
        formatTable();
    }

    private void formatTable() {
        TableText tableText = getSelectedTable(editor);
        if (tableText.isNotEmpty()) {
            PipeTable pipeTable = new PipeTableParser(tableText.getText()).parse();
            String formattedText = formatter().withOptions(options).format(pipeTable);
            editor.replaceText(formattedText, tableText.getRange());
        }
    }

}
