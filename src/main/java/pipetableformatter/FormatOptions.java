package pipetableformatter;

public class FormatOptions {

    private boolean outerPipes = true;


    public static FormatOptions formatOptions() {
        return new FormatOptions();
    }

    public FormatOptions withoutOuterPipes() {
        outerPipes = false;
        return this;
    }

    public boolean shouldIncludeOuterPipes() {
        return outerPipes;
    }

    public boolean shouldNotIncludeOuterPipes() {
        return !shouldIncludeOuterPipes();
    }
}
