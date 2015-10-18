package pipetableformatter.testsupport;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class Utils {

    public static String loadFile(String fileName) {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);

        StringBuilder stringBuilder = new StringBuilder();
        try {
            byte[] chunk = new byte[512];
            for (int count = inputStream.read(chunk); count >= 0; count = inputStream.read(chunk)) {
                stringBuilder.append(new String(chunk, 0, count, Charset.defaultCharset()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return stringBuilder.toString();
    }
}
