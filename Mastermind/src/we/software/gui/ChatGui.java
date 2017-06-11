package we.software.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.*;

import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

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

import we.software.mastermind.Client;

public class ChatGui extends JPanel{

	public JTextField chatInput;
	public JTextPane chatHistory;
	public Thread chatThread;
	public boolean chatRunning = false;
	//private KeyInput kp;
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	private ArrayList<String> commands = new ArrayList<String>();
	private int previousCommand=-1;
	private Client client;

	public ChatGui() {

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

	public void setBoundsForMainMenu(){
	    setBounds(680, 595, 600, 125);
    }

    public void setBoundsForGameGui(){
        setBounds(1, 595, 640, 125);
    }

	public void setClient(Client client){
		this.client = client;
	}

	private void KeyBindings(JPanel panel) {
		panel.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "upPressed");
		panel.getActionMap().put("upPressed", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if(previousCommand>0){
					previousCommand--;
					chatInput.setText(commands.get(previousCommand));
					
				}else if(previousCommand==0){
					chatInput.setText(commands.get(previousCommand));
				}
			}
		});
		
		panel.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "downPressed");
		panel.getActionMap().put("downPressed", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if(previousCommand<commands.size()-1){
					previousCommand++;
					chatInput.setText(commands.get(previousCommand));
					
				}else if(previousCommand==commands.size()-1){
					chatInput.setText("");
				}
			}
		});
		
		panel.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "enterPressed");
		panel.getActionMap().put("enterPressed", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if(!(chatInput.getText().equals("") || chatInput.getText().equals(" "))) {
					addCommandToList(chatInput.getText());
                    try {
                        if (!client.equals(null)) {

                            chatHandler((chatInput.getText()));
                        } else {
                            appendToPane("System: ", 2);
                            appendToPane("You are not connected to the server.\n", 0);
                            chatInput.setText("");
                        }
                    } catch (Exception e1) {
                        appendToPane("System: ", 2);
                        appendToPane("You are not connected to the server.\n", 0);
                        chatInput.setText("");
                    }
                }
            }
			
		});
    }
	
	public void addCommandToList(String command){
		commands.add(command);
		previousCommand=commands.size();
		if(commands.size()>=40){
			commands.remove(0);
		}
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

    private void chatHandler(String chatmsg) throws IOException {
        if(chatmsg.startsWith("pm")){
            String[] msg = chatInput.getText().split(":",3);
            if(msg.length<3){
                appendToPane("System: ", 2);
                appendToPane("Check your syntax. If you need help just type 'help' or '?'.\n", 0);
                chatInput.setText("");
            }
            else{
                client.sendMessage(msg[1],msg[2]);
                appendToPane("To "+msg[1]+": ", 1);
                appendToPane(msg[2]+"\n", 0);
                chatInput.setText("");
            }
        }
        else if(chatmsg.startsWith("all")){
            String[] msg = chatInput.getText().split(":",2);
            client.sendAllMessage(msg[1]);
            appendToPane("To everyone: ", 1);
            appendToPane(msg[1]+"\n", 0);
            chatInput.setText("");
        }
        else if(chatmsg.equals("?") || chatmsg.equals("help")){
            appendToPane("? or help -->get all option\n", 8);
            appendToPane("pm:name:message -->send pm message to a name\n", 8);
            appendToPane("all:message -->send global message\n", 8);
            appendToPane("invite:name -->send a game invitation\n", 8);
            appendToPane("invite:accept:name -->accept an invitation\n", 8);
            appendToPane("invite:decline:name -->decline an invitation\n", 8);
            appendToPane("users -->get online users at current time\n", 8);
            appendToPane("highscores -->refresh hisghscores in up right corner\n", 8);
            chatInput.setText("");
        }
        else if(chatmsg.startsWith("invite")){
            String[] msg = chatInput.getText().split(":",2);
            if(msg.length==2){
                client.sendGameRequest(msg[1]);
                chatInput.setText("");
            }
            else if(msg.length==3){
                if(msg[1].equals("accept")){
                    if(client.getPending().contains(msg[2])){
                        client.acceptGameRequest(msg[2]);
                        chatInput.setText("");
                    }else{
                        appendToPane("System: ", 2);
                        appendToPane("There is no invitation from this player.\n", 0);
                        chatInput.setText("");
                    }
                }else if(msg[1].equals("decline")){
                    if(client.getPending().contains(msg[2])){
                        client.rejectGameRequest(msg[2]);
                        chatInput.setText("");
                        client.clearPending();
                    }else{
                        appendToPane("System: ", 2);
                        appendToPane("There is no invitation from this player.\n", 0);
                        chatInput.setText("");
                        client.clearPending();
                    }
                }
            }
        }
        else if(chatmsg.equals("users")){
            client.getOnlinePlayers();
            chatInput.setText("");
        }
        else if(chatmsg.equals("highscores")){
            client.getHighScore();
            chatInput.setText("");
        }
        else{
            appendToPane("System: ", 2);
            appendToPane("Check your syntax. If you need help just type 'help' or '?'.\n", 0);
            chatInput.setText("");
        }
    }
}


