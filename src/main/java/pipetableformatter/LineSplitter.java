package pipetableformatter;

class LineSplitter {
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


        int winEof = notFormattedText.indexOf(PipeTable.WIN_EOF, startIndex);
        int linuxEof = notFormattedText.indexOf(PipeTable.LINUX_EOF, startIndex);

        if (winEof < 0 && linuxEof < 0) {
            endIndex = notFormattedText.length();
            endOfLine = "";
        } else if (winEof > 0 && winEof < linuxEof) {
            endIndex = winEof;
            endOfLine = PipeTable.WIN_EOF;
        } else {
            endIndex = linuxEof;
            endOfLine = PipeTable.LINUX_EOF;
        }

        String line = notFormattedText.substring(startIndex, endIndex);
        startIndex = endIndex + endOfLine.length();

        return line;
    }

    public boolean hasMoreLines() {
        return startIndex < notFormattedText.length();
    }
}
