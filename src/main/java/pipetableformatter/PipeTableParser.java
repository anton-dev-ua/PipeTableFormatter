package pipetableformatter;

import java.util.ArrayList;
import java.util.List;

public class PipeTableParser {
    public static final String WIN_EOF = "\r\n";
    public static final String LINUX_EOF = "\n";
    public List<PipeTable.Row> parserTable = new ArrayList<PipeTable.Row>();
    public int maxRowSize = 0;

    public PipeTable parse(String notFormattedText){
        return new PipeTable(parseText(notFormattedText));

    }

    private List<PipeTable.Row> parseText(String notFormattedText) {

        LineSplitter lineSplitter = new LineSplitter(notFormattedText);
        Character delimiter = detectDelimiter(notFormattedText);

        do {
            parseLine(lineSplitter.nextLine(), delimiter, lineSplitter.getEndOfLine());
        } while (lineSplitter.hasMoreLines());

        normalizeRows();
        return parserTable;

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

    private void parseLine(String line, Character delimiter, String endOfLine) {
        PipeTable.Row row = new PipeTable.Row(splitForColumns(line, delimiter), endOfLine);
        rememberMaxLength(row.size());
        parserTable.add(row);
    }

    private List<PipeTable.Cell> splitForColumns(String line, Character delimiter) {
        List<PipeTable.Cell> columns = new ArrayList<PipeTable.Cell>();

        ColumnSplitter columnSplitter = new ColumnSplitter(line, delimiter);
        while (columnSplitter.hasValue()) {
            columns.add(new PipeTable.Cell(columnSplitter.nextValue().trim()));
        }
        return columns;
    }

    private void rememberMaxLength(int size) {
        if (size > maxRowSize) {
            maxRowSize = size;
        }
    }
}