package we.software.gui;

import java.net.URL;

/**
 * Created by bill on 3/28/17.
 */
class LoadAssets {

    /**
     * Helps with loading the files and not having to write this line over and over
     */
    public static URL load(String path){
        return LoadAssets.class.getResource("/"+ path);
    }
}
