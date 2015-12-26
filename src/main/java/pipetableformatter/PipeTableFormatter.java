package pipetableformatter;

import pipetableformatter.PipeTable.Cell;

import static pipetableformatter.FormatOptions.formatOptions;

public class PipeTableFormatter {

    public static final String PIPE = "|";
    public static final String PIPE_COMMENT_START = "|--";
    public static final String PIPE_COMMENT_END = "--|";
    private FormatOptions formatOptions;

    private PipeTableFormatter() {
    }

    public String format(PipeTable pipeTable) {
        return formatPipeTable(
                pipeTable,
                formatOptions != null ? formatOptions : formatOptions()
        );
    }

    private String formatPipeTable(PipeTable table, FormatOptions options) {

        int[] columnsMaxLength = calculateColumnsMaxLength(table);

        StringBuffer buffer = new StringBuffer();
        for (PipeTable.Row row : table.rows()) {
            appendOuterPipe(options, buffer, row.isCommented() ? PIPE_COMMENT_START : PIPE);
            int columnIndex = 0;
            for (Cell cell : row.columns()) {
                int width = correctWidthForCommentedRow(columnsMaxLength[columnIndex], row, columnIndex);
                String formattedValue = padValue(width, cell.getValue());
                buffer.append(" ").append(formattedValue).append(" ");
                columnIndex++;
                appendInternalPipe(buffer, row, columnIndex);
            }
            appendOuterPipe(options, buffer, row.isCommented() ? PIPE_COMMENT_END : PIPE);
            buffer.append(row.endOfLine());
        }
        return buffer.toString();
    }
    
    private int correctWidthForCommentedRow(int maxWidth, PipeTable.Row row, int columnIndex) {
        return maxWidth - (row.isCommented() && (columnIndex == 0 || columnIndex == row.size() - 1) ? 2 : 0);
    }

    private void appendOuterPipe(FormatOptions options, StringBuffer buffer, String pipe) {
        if (options.shouldIncludeOuterPipes()) {
            buffer.append(pipe);
        }
    }

    private void appendInternalPipe(StringBuffer buffer, PipeTable.Row row, int columnIndex) {
        if (columnIndex < row.size()) {
            buffer.append(PIPE);
        }
    }

    private String padValue(int width, String value) {
        if (width > 0) {
            return String.format("%-" + width + "s", value);
        } else {
            return "";
        }
    }

    private int[] calculateColumnsMaxLength(PipeTable table) {
        int[] columnsMaxLength = new int[table.getColumnCount()];

        for (PipeTable.Row row : table.rows()) {
            int columnIndex = 0;
            for (Cell cell : row.columns()) {
                int length = cellValueLength(row, columnIndex, cell);
                if (length > columnsMaxLength[columnIndex]) {
                    columnsMaxLength[columnIndex] = length;
                }
                columnIndex++;
            }
        }
        return columnsMaxLength;
    }

    private int cellValueLength(PipeTable.Row row, int columnIndex, Cell cell) {
        return cell.getValue().length() + (row.isCommented() && (columnIndex == 0 || columnIndex == row.size() - 1) ? 2 : 0);
    }

    public static PipeTableFormatter formatter() {
        return new PipeTableFormatter();
    }

    public PipeTableFormatter withOptions(FormatOptions options) {
        this.formatOptions = options;
        return this;
    }
}
