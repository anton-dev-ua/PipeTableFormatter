package pipetableformatter.plugin.operation;

public class Select extends Operation {


    private PipeTableEditor editor;

    public Select(PipeTableEditor editor) {
        this.editor = editor;
    }

    @Override
    public void run() {
        TableText tableText = getTextToFormat(editor);
        if(tableText.isNotEmpty()) {
            editor.setSelection(tableText.getRange());
        }
    }

}
