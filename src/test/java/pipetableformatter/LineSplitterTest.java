package pipetableformatter;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class LineSplitterTest {

    @Test
    public void iteratesThrowLines() {
        LineSplitter lineSplitter = new LineSplitter("row1\nrow2\n");
        assertThat(lineSplitter.next(), is("row1"));
        assertThat(lineSplitter.next(), is("row2"));
    }

    @Test
    public void returnTrueIfThereAreMoreLines() {
        LineSplitter lineSplitter = new LineSplitter("row1\nrow2\n");
        assertThat(lineSplitter.hasNext(), is(true));
    }

    @Test
    public void returnFalseIfThereAreNoMoreLines() {
        LineSplitter lineSplitter = new LineSplitter("row1\nrow2\n");
        lineSplitter.next();
        lineSplitter.next();

        assertThat(lineSplitter.hasNext(), is(false));
    }

    @Test
    public void returnsStartLineIndex() {
        LineSplitter lineSplitter = new LineSplitter("row1\nrow2\nrow3\n");
        lineSplitter.next();
        lineSplitter.next();

        assertThat(lineSplitter.currentLineStartIndex(), is(5));
    }

    @Test
    public void returnsEndLineIndex() {
        LineSplitter lineSplitter = new LineSplitter("row1\nrow2\nrow3\n");
        lineSplitter.next();
        lineSplitter.next();

        assertThat(lineSplitter.currentLineEndIndex(), is(9));
    }


}