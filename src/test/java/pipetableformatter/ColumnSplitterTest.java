package pipetableformatter;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ColumnSplitterTest {

    @Test
    public void iteratesTroughColumns() {
        ColumnSplitter columnSplitter = new ColumnSplitter("|col1|col2|col3|", '|');
        assertThat(columnSplitter.next(), is("col1"));
        assertThat(columnSplitter.next(), is("col2"));
        assertThat(columnSplitter.next(), is("col3"));
    }

    @Test
    public void returnsTrueIfThereAreMoreColumns() {
        ColumnSplitter columnSplitter = new ColumnSplitter("|col1|col2|col3|", '|');
        assertThat(columnSplitter.hasNext(), is(true));
    }

    @Test
    public void returnsFalseIfThereAreNoMoreColumns() {
        ColumnSplitter columnSplitter = new ColumnSplitter("|col1|col2|col3|", '|');
        columnSplitter.next();
        columnSplitter.next();
        columnSplitter.next();

        assertThat(columnSplitter.hasNext(), is(false));
    }

    @Test
    public void returnsColumnStartIndex() {
        ColumnSplitter columnSplitter = new ColumnSplitter("|col1|col2|col3|", '|');
        columnSplitter.next();
        columnSplitter.next();

        assertThat(columnSplitter.currentColumnStartIndex(), is(6));
    }

    @Test
    public void returnsColumnEndIndex() {
        ColumnSplitter columnSplitter = new ColumnSplitter("|col1|col2|col3|", '|');
        columnSplitter.next();
        columnSplitter.next();

        assertThat(columnSplitter.currentColumnEndIndex(), is(10));
    }

    @Test
    public void incrementsColumnIndex() {
        ColumnSplitter columnSplitter = new ColumnSplitter("|col1|col2|col3|", '|');
        int index = 0;
        for(String value : columnSplitter) {
            assertThat(columnSplitter.currentColumnIndex(), is(index));
            index++;
        }
    }

}