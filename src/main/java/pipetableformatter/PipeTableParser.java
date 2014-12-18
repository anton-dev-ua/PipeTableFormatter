package pipetableformatter;

import java.util.ArrayList;
import java.util.List;

public class PipeTableParser {
    public static final String WIN_EOF = "\r\n";
    public static final String LINUX_EOF = "\n";
    public List<PipeTable.Row> parserTable = new ArrayList<PipeTable.Row>();
    public int maxRowSize = 0;
    private int caretPosition = -1;
    private int selectedRow = -1;
    private Character delimiter;
    private int selectedColumn = -1;
    private int currentLineStartIndex;
    private String notFormattedText;

    public PipeTableParser(String notFormattedText) {
        this.notFormattedText = notFormattedText;
    }

    public PipeTableParser withDetectingCellByCaretPosition(int caretPosition) {
        this.caretPosition = caretPosition;
        return this;
    }

    public PipeTable parse() {
        parseText();
        return new PipeTable(parserTable);

    }

    private void parseText() {

        delimiter = detectDelimiter(notFormattedText);
        LineSplitter lineSplitter = new LineSplitter(notFormattedText);

        for (String line : lineSplitter) {
            boolean rowWithCaret = false;
            if (caretPosition >= lineSplitter.currentLineStartIndex() && caretPosition <= lineSplitter.currentLineEndIndex()) {
                selectedRow = lineSplitter.currentLineIndex();
                currentLineStartIndex = lineSplitter.currentLineStartIndex();
                rowWithCaret = true;
            }
            parseLine(line, lineSplitter.getEndOfLine(), rowWithCaret);
        }

        normalizeRows();
    }

    private void normalizeRows() {
        for (PipeTable.Row row : parserTable) {
            for (int i = row.size(); i < maxRowSize; i++) {
                row.add("");
            }
        }
    }

    private Character detectDelimiter(String text) {
        return new DelimitersCount(text).mostFrequent();
    }

    private void parseLine(String line, String endOfLine, boolean rowWithCaret) {
        PipeTable.Row row = new PipeTable.Row(splitForColumns(line, rowWithCaret), endOfLine);
        rememberMaxLength(row.size());
        parserTable.add(row);
    }

    private List<PipeTable.Cell> splitForColumns(String line, boolean rowWithCaret) {
        List<PipeTable.Cell> columns = new ArrayList<PipeTable.Cell>();
        int rowCaretPosition = caretPosition - currentLineStartIndex;

        ColumnSplitter columnSplitter = new ColumnSplitter(line, delimiter);
        for (String value : columnSplitter) {
            columns.add(new PipeTable.Cell(value));
            if (rowWithCaret) {
                if (rowCaretPosition >= columnSplitter.currentColumnStartIndex() && rowCaretPosition <= columnSplitter.currentColumnEndIndex()) {
                    selectedColumn = columnSplitter.currentColumnIndex();
                } else if (columnSplitter.currentColumnIndex() == 0 && rowCaretPosition < columnSplitter.currentColumnStartIndex()) {
                    selectedColumn = 0;
                }
            }
        }
        if (rowWithCaret && selectedColumn == -1) {
            selectedColumn = columns.size();
        }
        return columns;
    }

    private void rememberMaxLength(int size) {
        if (size > maxRowSize) {
            maxRowSize = size;
        }
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public int getSelectedColumn() {
        return selectedColumn;
    }
}