package we.software.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

import javax.swing.border.*;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class ChatGui extends JPanel{

	public JTextField chatInput;
	public JTextPane chatHistory;
	public Thread chatThread;
	public boolean chatRunning = false;
	//private KeyInput kp;
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;

	public ChatGui() {

        setBounds(1, 595, 640, 125);
        //setOpaque(false);
        setLayout(new GridBagLayout());
        this.setBackground(Color.BLACK);

        chatInput = new JTextField();
        Font f1 = new Font(Font.SANS_SERIF, Font.PLAIN, 15);
        chatInput.setFont(f1);
        chatInput.setForeground(Color.white);
        chatInput.setOpaque(false);

        //Font f2 = new Font("Dialog", Font.ITALIC, 15);
        chatHistory = new JTextPane();
        //chatHistory.setFont(f2);
        chatHistory.setAutoscrolls(true);
        chatHistory.setEditable(false);
        chatHistory.setOpaque(false);

        //kp = new KeyInput();


        DefaultCaret caret = (DefaultCaret)chatHistory.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane js = new JScrollPane(chatHistory);
        js.getViewport().setOpaque(false);
        js.setOpaque(false);
        js.setVisible(true);
        js.setAutoscrolls(true);


        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 1;
        c.ipady = 70;
        add(js, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        add(chatInput, c);
        KeyBindings(this);
	}

	private void KeyBindings(JPanel panel) {
		panel.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "upPressed");
		panel.getActionMap().put("upPressed", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				appendToPane("skata",1);
			}
		});
    }

    public void appendToPane(String msg, int choice) {

        StyledDocument doc = chatHistory.getStyledDocument();

        Style style = chatHistory.addStyle("I'm a Style", null);

        switch(choice){
        case 0:{
        	StyleConstants.setBold(style, false);
            StyleConstants.setUnderline(style, false);
            StyleConstants.setFontSize(style, 15);
            StyleConstants.setFontFamily(style, Font.DIALOG);
            StyleConstants.setItalic(style, true);
            StyleConstants.setForeground(style, Color.WHITE);
            break;
        }
        case 1:{
        	StyleConstants.setBold(style, false);
            StyleConstants.setUnderline(style, false);
            StyleConstants.setFontSize(style, 15);
            StyleConstants.setFontFamily(style, Font.DIALOG);
            StyleConstants.setItalic(style, true);
            StyleConstants.setForeground(style, Color.orange);
            break;
        }
        case 2:{
        	StyleConstants.setBold(style, false);
            StyleConstants.setUnderline(style, false);
            StyleConstants.setFontSize(style, 15);
            StyleConstants.setFontFamily(style, Font.DIALOG);
            StyleConstants.setItalic(style, true);
            StyleConstants.setForeground(style, Color.CYAN);
            break;
        }
        case 3:{
        	StyleConstants.setBold(style, false);
            StyleConstants.setUnderline(style, true);
            StyleConstants.setFontSize(style, 15);
            StyleConstants.setFontFamily(style, Font.DIALOG);
            StyleConstants.setItalic(style, true);
            StyleConstants.setForeground(style, Color.PINK);
            break;
        }
        case 4:{
        	StyleConstants.setBold(style, true);
            StyleConstants.setUnderline(style, true);
            StyleConstants.setFontSize(style, 15);
            StyleConstants.setFontFamily(style, Font.DIALOG);
            StyleConstants.setItalic(style, true);
            StyleConstants.setForeground(style, Color.RED);
            break;
        }
        case 5:{
        	StyleConstants.setBold(style, false);
            StyleConstants.setUnderline(style, false);
            StyleConstants.setFontSize(style, 15);
            StyleConstants.setFontFamily(style, Font.DIALOG);
            StyleConstants.setItalic(style, true);
            StyleConstants.setForeground(style, Color.MAGENTA);
            break;
        }
        case 6:{
        	StyleConstants.setBold(style, false);
            StyleConstants.setUnderline(style, false);
            StyleConstants.setFontSize(style, 15);
            StyleConstants.setFontFamily(style, Font.DIALOG);
            StyleConstants.setItalic(style, true);
            StyleConstants.setForeground(style, Color.RED);
            break;
        }
        case 7:{
        	StyleConstants.setBold(style, false);
            StyleConstants.setUnderline(style, false);
            StyleConstants.setFontSize(style, 15);
            StyleConstants.setFontFamily(style, Font.DIALOG);
            StyleConstants.setItalic(style, true);
            StyleConstants.setForeground(style, Color.blue);
            break;
        }
        case 8:{
        	StyleConstants.setBold(style, false);
            StyleConstants.setUnderline(style, false);
            StyleConstants.setFontSize(style, 15);
            StyleConstants.setFontFamily(style, Font.DIALOG);
            StyleConstants.setItalic(style, true);
            StyleConstants.setForeground(style, Color.GRAY);
            break;
        }
        case 9:{
        	StyleConstants.setBold(style, false);
            StyleConstants.setUnderline(style, false);
            StyleConstants.setFontSize(style, 15);
            StyleConstants.setFontFamily(style, Font.DIALOG);
            StyleConstants.setItalic(style, true);
            StyleConstants.setForeground(style, Color.LIGHT_GRAY);
            break;
        }
        }
        
        
        try { doc.insertString(doc.getLength(), msg,style); }
        catch (BadLocationException e){
            System.out.println(e.getStackTrace());
        }

    }

}


