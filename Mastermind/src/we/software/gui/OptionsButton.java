package we.software.gui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by Camel on 30-Mar-17.
 */
class OptionsButton extends JButton {

    private ImageIcon buttonIcon;
    private ImageIcon hoverButtonIcon;

    public OptionsButton(String path, int x, int y){

        try {
            buttonIcon = new ImageIcon(LoadAssets.load(path));
            hoverButtonIcon = new ImageIcon(LoadAssets.load("h"+path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setIcon(buttonIcon);
        setBounds(x, y, buttonIcon.getIconWidth(), buttonIcon.getIconHeight());
        setOpaque(false);
        setBorderPainted(false);
        setContentAreaFilled(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setIcon(hoverButtonIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setIcon(buttonIcon);
            }
        });


    }
}
