package pipetableformatter;

import java.util.ArrayList;
import java.util.List;

import static pipetableformatter.DelimitersCount.*;

public class PipeTableParser {
    public static final String WIN_EOF = "\r\n";
    public static final String LINUX_EOF = "\n";
    public List<PipeTable.Row> tableRows = new ArrayList<PipeTable.Row>();
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

    public PipeTableParser detectingCellByPosition(int caretPosition) {
        this.caretPosition = caretPosition;
        return this;
    }

    public PipeTable parse() {
        parseText();
        PipeTable pipeTable = new PipeTable(tableRows);
        pipeTable.setSelectedRow(selectedRow);
        pipeTable.setSelectedColumn(selectedColumn);
        return pipeTable;
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
        for (PipeTable.Row row : tableRows) {
            for (int i = row.size(); i < maxRowSize; i++) {
                row.add("");
            }
        }
    }

    private Character detectDelimiter(String text) {
        return delimitersCountIn(text).mostFrequent();
    }

    private void parseLine(String line, String endOfLine, boolean rowWithCaret) {
        PipeTable.Row row = splitForColumns(line, rowWithCaret, endOfLine);

        rememberMaxLength(row.size());

        tableRows.add(row);
    }

    private PipeTable.Row splitForColumns(String line, boolean rowWithCaret, String endOfLine) {
        List<PipeTable.Cell> cells = new ArrayList<PipeTable.Cell>();
        int rowCaretPosition = caretPosition - currentLineStartIndex;

        ColumnSplitter tableRow = new ColumnSplitter(line, delimiter);
        for (String value : tableRow) {
            cells.add(new PipeTable.Cell(value));
            if (rowWithCaret) {
                if (rowCaretPosition >= tableRow.currentColumnStartIndex() && rowCaretPosition <= tableRow.currentColumnEndIndex()) {
                    selectedColumn = tableRow.currentColumnIndex();
                } else if (tableRow.currentColumnIndex() == 0 && rowCaretPosition < tableRow.currentColumnStartIndex()) {
                    selectedColumn = 0;
                }
            }
        }
        if (rowWithCaret && selectedColumn == -1) {
            selectedColumn = cells.size();
        }
        
        PipeTable.Row row = new PipeTable.Row(cells, endOfLine);
        row.setCommented(tableRow.isCommented());
        row.setIndentation(tableRow.getIndentetion());
        row.setLeadingPipe(tableRow.hasLeadingPipe());
        row.setTrailingPipe(tableRow.hasTrailingPipe());
        
        return row;
    }

    private void rememberMaxLength(int size) {
        if (size > maxRowSize) {
            maxRowSize = size;
        }
    }
}