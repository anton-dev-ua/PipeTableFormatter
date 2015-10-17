package pipetableformatter;

import pipetableformatter.PipeTable.Cell;

import static pipetableformatter.FormatOptions.formatOptions;

public class PipeTableFormatter {

    public static final String PIPE = "|";
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
            appendOuterPipe(options, buffer);
            int columnIndex = 0;
            for (Cell cell : row.columns()) {
                String formattedValue = padValue(columnsMaxLength[columnIndex], cell.getValue());
                buffer.append(" ").append(formattedValue).append(" ");
                columnIndex++;
                appendInternalPipe(buffer, row, columnIndex);
            }
            appendOuterPipe(options, buffer);
            buffer.append(row.endOfLine());
        }
        return buffer.toString();
    }

    private void appendOuterPipe(FormatOptions options, StringBuffer buffer) {
        if(options.shouldIncludeOuterPipes()) {
            buffer.append(PIPE);
        }
    }

    private void appendInternalPipe(StringBuffer buffer, PipeTable.Row row, int columnIndex) {
        if(columnIndex < row.size()) {
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
                if (cell.getValue().length() > columnsMaxLength[columnIndex]) {
                    columnsMaxLength[columnIndex] = cell.getValue().length();
                }
                columnIndex++;
            }
        }
        return columnsMaxLength;
    }

    public static PipeTableFormatter formatter() {
        return new PipeTableFormatter();
    }

    public PipeTableFormatter withOptions(FormatOptions options) {
        this.formatOptions = options;
        return this;
    }
}
