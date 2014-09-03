package pipetableformatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PipeTable {

    public static final String WIN_EOF = "\r\n";
    public static final String LINUX_EOF = "\n";
    List<PipeTableRow> table = new ArrayList<PipeTableRow>();
    int maxRowSize = 0;

    public PipeTable(String notFormattedText) {
        parseText(notFormattedText);
        normalizeRows();
    }

    private void normalizeRows() {
        for (PipeTableRow row : table) {
            for (int i = row.size(); i < maxRowSize; i++) {
                row.add("");
            }
        }
    }

    private void parseText(String notFormattedText) {

        LineSplitter lineSplitter = new LineSplitter(notFormattedText);

        do {
            parseLine(lineSplitter.nextLine(), lineSplitter.getEndOfLine());
        } while (lineSplitter.hasMoreLines());
    }

    private void parseLine(String line, String endOfLine) {
        String clearedLine = line.trim().replaceAll("(^\\|\\s*)|(\\s*\\|$)", "");

        List<String> columns = splitForColumns(clearedLine);

        PipeTableRow row = new PipeTableRow(columns, endOfLine);
        rememberMaxLength(row.size());
        table.add(row);
    }

    private List<String> splitForColumns(String clearedLine) {
        String[] quoted = clearedLine.split("\"");

        List<String> columns = new ArrayList<String>();
        for (int index = clearedLine.startsWith("\"") ? 1 : 0; index < quoted.length; index++) {
            if (index % 2 == 0) {
                String subColumns = index > 0 ? quoted[index].trim().replaceAll("(^\\|)|(^,)", "") : quoted[index];
                columns.addAll(Arrays.asList(subColumns.split("\\s*(\\||,)\\s*")));
            } else {
                columns.add(quoted[index]);
            }
        }
        return columns;
    }

    private void rememberMaxLength(int size) {
        if (size > maxRowSize) {
            maxRowSize = size;
        }
    }

    public int getRowCount() {
        return table.size();
    }

    public String getValue(int row, int column) {
        return table.get(row).get(column);
    }

    public PipeTableRow[] rows() {
        return table.toArray(new PipeTableRow[table.size()]);
    }

    public int getColumnCount() {
        return maxRowSize;
    }

    private static class LineSplitter {
        private String notFormattedText;
        private int startIndex;
        private int endIndex;
        private String endOfLine;

        public LineSplitter(String notFormattedText) {
            this.notFormattedText = notFormattedText;
            startIndex = 0;
            endIndex = 0;
            endOfLine = "";
        }

        public String getEndOfLine() {
            return endOfLine;
        }

        public String nextLine() {


            int winEof = notFormattedText.indexOf(WIN_EOF, startIndex);
            int linuxEof = notFormattedText.indexOf(LINUX_EOF, startIndex);

            if (winEof < 0 && linuxEof < 0) {
                endIndex = notFormattedText.length();
                endOfLine = "";
            } else if (winEof > 0 && winEof < linuxEof) {
                endIndex = winEof;
                endOfLine = WIN_EOF;
            } else {
                endIndex = linuxEof;
                endOfLine = LINUX_EOF;
            }

            String line = notFormattedText.substring(startIndex, endIndex);
            startIndex = endIndex + endOfLine.length();

            return line;
        }

        public boolean hasMoreLines() {
            return startIndex < notFormattedText.length();
        }
    }
}
