package we.software.gui;

import java.io.InputStream;

/**
 * Created by bill on 3/28/17.
 */
public class LoadAssets {

    public static InputStream load(String path) {
        InputStream input = LoadAssets.class.getResourceAsStream(path);
        if (input == null) {
            input = LoadAssets.class.getResourceAsStream("/" + path);
        }

        return input;
    }
}
