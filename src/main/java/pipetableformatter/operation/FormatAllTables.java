package pipetableformatter.operation;

import pipetableformatter.PipeTable;
import pipetableformatter.PipeTableParser;
import pipetableformatter.Range;
import pipetableformatter.TableDetector;

import java.util.regex.Pattern;

import static pipetableformatter.PipeTableFormatter.formatter;

public class FormatAllTables extends Operation {
    private static final Pattern AT_LEAST_TWO_LINES_PATTERN = Pattern.compile(".+\\n.+(.|\\n|\\r)*");
    
    private PipeTableEditor editor;

    public FormatAllTables(PipeTableEditor editor) {
        this.editor = editor;
    }

    @Override
    public void run() {
        String text = editor.getText();
        int position = 0;
        int offset = 0;
        while (position >= 0) {
            Range tableRange = new TableDetector(text).findAround(position);
            if (tableRange.isNotEmpty()) {
                String textToFormat = text.substring(tableRange.getStart(), tableRange.getEnd());
                if(AT_LEAST_TWO_LINES_PATTERN.matcher(textToFormat).matches()) {
                    PipeTable pipeTable = new PipeTableParser(textToFormat).parse();
                    String formattedText = formatter().format(pipeTable);
                    editor.replaceText(formattedText, tableRange.plus(offset));
                    position = tableRange.getEnd() + 1;
                    offset += formattedText.length() - textToFormat.length();
                } else {
                    position = tableRange.getEnd() + 1;
                }
            } else {
                position = text.indexOf("\n", position + 1);
            }
        }
    }
}
