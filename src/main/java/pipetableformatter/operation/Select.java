package pipetableformatter.operation;

public class Select extends Operation {

    private final PipeTableEditor editor;

    public Select(PipeTableEditor editor) {
        this.editor = editor;
    }

    @Override
    protected void perform() {
        TableText tableText = getSelectedTable(editor);
        if(tableText.isNotEmpty()) {
            editor.setSelection(tableText.getRange());
        }
    }

}
