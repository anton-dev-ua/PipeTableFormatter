package pipetableformatter.operation;

import pipetableformatter.PipeTable;
import pipetableformatter.PipeTableParser;
import pipetableformatter.Range;
import pipetableformatter.TableDetector;

import static pipetableformatter.PipeTableFormatter.formatter;

public class FormatAllTables extends Operation {
    private PipeTableEditor editor;

    public FormatAllTables(PipeTableEditor editor) {
        this.editor = editor;
    }

    @Override
    public void run() {
        String text = editor.getText();
        int position = text.lastIndexOf("\n");
        while (position >= 0) {
            position -= 1;
            Range tableRange = new TableDetector(text).findAround(position);
            if (tableRange.isNotEmpty()) {
                String textToFormat = text.substring(tableRange.getStart(), tableRange.getEnd());
                PipeTable pipeTable = new PipeTableParser(textToFormat).parse();
                String formattedText = formatter().format(pipeTable);
                editor.replaceText(formattedText, tableRange);
                position = tableRange.getStart();
            } else {
                position = text.lastIndexOf("\n", position);
            }
        }
    }
}
