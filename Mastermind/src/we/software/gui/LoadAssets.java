package we.software.gui;

import java.io.IOException;
import java.net.URL;

/**
 * Created by bill on 3/28/17.
 */
public class LoadAssets {

    /**
     * Μας βοηθάει στην καλύτερη φόρτωση των εικόνων και στην συμπερίληψή τους
     * στο τελικό εκτελέσιμο αρχείο.
     */
    public static URL load(String path) throws IOException{
        URL url = LoadAssets.class.getResource("/"+ path);
        return url;
    }
}
