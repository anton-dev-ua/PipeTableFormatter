package pipetableformatter.plugin.operation;

import org.junit.Test;

import static org.mockito.Mockito.verify;

public class AddColumnBeforeOperationTest extends AbstractOperationTest {


    protected static final String FORMATTED_TABLE_WITH_NEW_COLUMN = "" +
            "| Country                  |  | Currency        | Population   | Area              |\n" +
            "| United States of America |  | US dollar       | 316 million  | 9.8 million sq km |\n" +
            "| Canada                   |  | Canadian dollar | 34.7 million | 9.9 million sq km |\n" +
            "| United Kingdom           |  | pound sterling  | 62.8 million | 242,514 sq km     |\n" +
            "| Republic of Poland       |  | zloty           | 38.3 million | 312,685 sq km     |";

    @Test
    public void addsColumn() {

        new AddColumnBefore(editor).run();

        verify(document).replaceString(TABLE_START_POSITION, TABLE_END_POSITION, FORMATTED_TABLE_WITH_NEW_COLUMN);
    }
}
