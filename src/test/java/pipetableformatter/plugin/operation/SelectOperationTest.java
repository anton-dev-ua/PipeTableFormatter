package pipetableformatter.plugin.operation;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class SelectOperationTest {

    OperationUtility utility;

    @Before
    public void before() {
        utility = mock(OperationUtility.class);
    }

    @Test
    public void selectsTable() {

        new Select(utility).run();

        verify(utility).autoselectTableIfNotSelected();
        verify(utility, never()).getSelectedText();
        verify(utility, never()).replaceText(anyString());
    }
}
