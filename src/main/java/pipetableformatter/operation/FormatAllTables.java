package pipetableformatter.operation;

import pipetableformatter.PipeTable;
import pipetableformatter.PipeTableParser;
import pipetableformatter.Range;

import static pipetableformatter.PipeTableFormatter.formatter;
import static pipetableformatter.TableDetector.detectTableIn;

public class FormatAllTables extends Operation {

    private PipeTableEditor editor;

    public FormatAllTables(PipeTableEditor editor) {
        this.editor = editor;
    }

    @Override
    protected void perform() {
        formatNext(editor.getText(), editor.getText().length() - 1);
        editor.setSelection(new Range(0, 0));
    }

    private void formatNext(String text, int position) {
        if (position < 0) return;

        Range tableRange = detectTableIn(text).usingDelimiters('|').around(position);
        if (tableRange.isNotEmpty()) {
            formatAndReplace(text, tableRange);
            formatNext(text, tableRange.getStart() - 1);
        } else {
            formatNext(text, text.lastIndexOf("\n", position) - 1);
        }
    }

    private void formatAndReplace(String text, Range tableRange) {
        String textToFormat = text.substring(tableRange.getStart(), tableRange.getEnd());
        PipeTable pipeTable = parse(textToFormat);
        if (pipeTable.getRowCount() > 1) {
            String formattedText = formatter().format(pipeTable);
            editor.replaceText(formattedText, tableRange);
        }
    }

    private PipeTable parse(String textToFormat) {
        return new PipeTableParser(textToFormat).parse();
    }
}
