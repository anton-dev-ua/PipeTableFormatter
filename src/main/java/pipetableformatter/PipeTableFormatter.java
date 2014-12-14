package pipetableformatter;

public class PipeTableFormatter {

    public static final String PIPE = "|";

    public String format(String notFormattedText) {

        PipeTable table = new PipeTableParser().parse(notFormattedText);

        return formatPipeTable(table);
    }

    private String formatPipeTable(PipeTable table) {

        int[] columnsMaxLength = calculateColumnsMaxLength(table);

        StringBuffer buffer = new StringBuffer();
        for (PipeTable.Row row : table.rows()) {
            buffer.append(PIPE);
            int columnIndex = 0;
            for (PipeTable.Cell cell : row.columns()) {
                String formattedValue = padValue(columnsMaxLength[columnIndex], cell.getValue());
                buffer.append(" ").append(formattedValue).append(" ").append(PIPE);
                columnIndex++;
            }
            buffer.append(row.endOfLine());
        }
        return buffer.toString();
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
            for (PipeTable.Cell cell : row.columns()) {
                if (cell.getValue().length() > columnsMaxLength[columnIndex]) {
                    columnsMaxLength[columnIndex] = cell.getValue().length();
                }
                columnIndex++;
            }
        }
        return columnsMaxLength;
    }
}
