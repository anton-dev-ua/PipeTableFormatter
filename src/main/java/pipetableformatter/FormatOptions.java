package pipetableformatter;

public class FormatOptions {

    private boolean preserveOuterState = false;


    public static FormatOptions formatOptions() {
        return new FormatOptions();
    }

    public FormatOptions preserveOuterState() {
        preserveOuterState = true;
        return this;
    }

    public boolean shouldPreserveOuterState() {
        return preserveOuterState;
    }

    public boolean shouldNotPreserveOuterState() {
        return !preserveOuterState;
    }
}
