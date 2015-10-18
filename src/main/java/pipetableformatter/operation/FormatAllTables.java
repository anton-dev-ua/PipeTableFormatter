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
        String text = editor.getText();
        formatNext(text, 0, 0);
    }

    private void formatNext(String text, int position, int offset) {
        if (noMoreToFormat(text, position)) return;

        Range tableRange = detectTableIn(text).around(position);
        if (tableRange.isNotEmpty()) {
            offset = formatAndReplace(text, tableRange, offset);
            formatNext(text, tableRange.getEnd() + 1, offset);
        } else {
            formatNext(text, text.indexOf("\n", position + 1), offset);
        }
    }

    private int formatAndReplace(String text, Range tableRange, int offset) {
        String textToFormat = text.substring(tableRange.getStart(), tableRange.getEnd());
        PipeTable pipeTable = parse(textToFormat);
        if (pipeTable.getRowCount() > 1) {
            String formattedText = formatter().format(pipeTable);
            editor.replaceText(formattedText, tableRange.plus(offset));
            offset += formattedText.length() - textToFormat.length();
        }
        return offset;
    }

    private boolean noMoreToFormat(String text, int position) {
        return position < 0 || position >= text.length();
    }

    private PipeTable parse(String textToFormat) {
        return new PipeTableParser(textToFormat).parse();
    }
}
