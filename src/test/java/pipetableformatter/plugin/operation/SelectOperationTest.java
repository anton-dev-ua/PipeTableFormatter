package pipetableformatter.plugin.operation;

import org.junit.Test;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class SelectOperationTest extends AbstractOperationTest {

    @Test
    public void selectsTable() {

        new Select(editor).run();

        verify(selectionModel).setSelection(TABLE_START_POSITION, TABLE_END_POSITION);
        verify(document, never()).replaceString(anyInt(), anyInt(), anyString());
    }
}
