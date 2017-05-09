package we.software.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Bill on 09-May-17.
 */
public class KeyInput implements KeyListener {

    public static boolean send;
    private boolean[] keys;

    public KeyInput(){

        keys = new boolean[256];
    }

    public void update(){

        send = keys[KeyEvent.VK_ENTER];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        keys[e.getKeyCode()] = true;
        update();
    }

    @Override
    public void keyReleased(KeyEvent e) {

        keys[e.getKeyCode()] = false;
        update();
    }
}
