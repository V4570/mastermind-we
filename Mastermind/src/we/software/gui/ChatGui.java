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

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class ChatGui {

	public JTextField chatInput;
	public JTextPane chatHistory;
	public JPanel chat;

	public ChatGui() {
		chat = new JPanel(new GridBagLayout());
		chat.setBounds(1, 641, 623, 125);
		chat.setOpaque(false);

		chatInput = new JTextField();
		Font f1 = new Font("Dialog", Font.PLAIN, 15);
		chatInput.setFont(f1);
		chatInput.setForeground(Color.white);
		chatInput.setOpaque(false);

		Font f2 = new Font("Dialog", Font.ITALIC, 15);
		chatHistory = new JTextPane();
		chatHistory.setFont(f2);
		chatHistory.setAutoscrolls(true);
		//chatHistory.setText("testttttttttttttttttt");
		// chatHistory.setForeground(Color.white);
		// chatHistory.setLineWrap(true);
		chatHistory.setEditable(false);
		chatHistory.setOpaque(false);

		// chatHistory.setMargin(new Insets(5, 5, 5, 5));
		// StyledDocument doc = chatHistory.getStyledDocument();

		
		DefaultCaret caret = (DefaultCaret)chatHistory.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE); 
		JScrollPane js = new JScrollPane(chatHistory); 
		js.getViewport().setOpaque(false);
		js.setOpaque(false); js.setVisible(true); 
		js.setAutoscrolls(true);
		

		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 1;
		// c.weightx = 0.0;
		c.ipady = 70;
		// c.weighty = 1.0;
		chat.add(js, c);

		// c.gridheight = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 1;
		chat.add(chatInput, c);
	}

	public void appendToPane(String msg, Color c) {
		StyledDocument doc = chatHistory.getStyledDocument();
		
		Style style = chatHistory.addStyle("I'm a Style", null);

		StyleConstants.setForeground(style,c);
		 try { doc.insertString(doc.getLength(), msg,style); }
	        catch (BadLocationException e){
	        	System.out.println(e.getStackTrace());
	        }

	}

	}


