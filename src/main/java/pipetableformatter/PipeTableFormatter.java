package pipetableformatter;

public class PipeTableFormatter {

    public static final String PIPE = "|";

    public String format(String notFormattedText) {

        PipeTable table = new PipeTable(notFormattedText);

        return formatPipeTable(table);
    }

    private String formatPipeTable(PipeTable table) {

        int[] columnsMaxLength = calculateColumnsMaxLength(table);

        StringBuffer buffer = new StringBuffer();
        for (PipeTableRow row : table.rows()) {
            buffer.append(PIPE);
            int columnIndex = 0;
            for (String value : row.columns()) {
                String formattedValue = String.format("%-" + columnsMaxLength[columnIndex] + "s", value);
                buffer.append(" ").append(formattedValue).append(" ").append(PIPE);
                columnIndex++;
            }
            buffer.append(row.endOfLine());
        }
        return buffer.toString();
    }

    private int[] calculateColumnsMaxLength(PipeTable table) {
        int[] columnsMaxLength = new int[table.getColumnCount()];

        for (PipeTableRow row : table.rows()) {
            int columnIndex = 0;
            for (String value : row.columns()) {
                if(value.length()>columnsMaxLength[columnIndex]){
                    columnsMaxLength[columnIndex] = value.length();
                }
                columnIndex++;
            }
        }
        return columnsMaxLength;
    }
}
