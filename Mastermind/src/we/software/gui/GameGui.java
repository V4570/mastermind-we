package we.software.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Bill on 06-Apr-17.
 */
public class GameGui extends JFrame{

    private MainMenu previous;
    private final int WIDTH = 1024;
    private final int HEIGHT = WIDTH / 12*9;
    private ButtonListener btnListener = new ButtonListener();
    private MenuButton exitButton, optionsButton, backButton, sendButton;
    private JTextField chatInput;
    private JTextArea chatHistory;
    private JPanel chat;
    private HistoryPanel turnHistory;
    private ChatGui chatGui;
    
    public GameGui(MainMenu previous){

        this.previous = previous;
        setUpButtons();
        initFrame();
        
    }

    private void initFrame(){

        try {
            setIconImage(ImageIO.read(LoadAssets.load("master.png")));
            setContentPane(new JLabel(new ImageIcon(ImageIO.read(LoadAssets.load("GameGui.png")))));
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }


        turnHistory = new HistoryPanel();
        //initChat();
        chatGui = new ChatGui();


        add(exitButton);
        add(optionsButton);
        add(backButton);
        add(sendButton);
        add(turnHistory);
        add(chatGui.chat);

        setUndecorated(true);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    public void hscores(){
    	//Jpanel hscores
    }

    /*private void initChat(){
    	
        chat = new JPanel(new GridBagLayout());
        chat.setBounds(1, 641, 623, 125);
        chat.setOpaque(false);
        
        chatInput = new JTextField();
        Font f1 = new Font("Dialog",Font.PLAIN ,15);
        chatInput.setFont(f1);
        chatInput.setForeground(Color.white);
        chatInput.setOpaque(false);
        
        
        Font f2 = new Font("Dialog",Font.ITALIC ,15);
        chatHistory = new JTextArea(chat.getWidth(),0);
        chatHistory.setFont(f2);
        chatHistory.setForeground(Color.white);
        chatHistory.setLineWrap(true);
        chatHistory.setEditable(false);
        chatHistory.setOpaque(false);
        
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
        //c.weightx = 0.0;
        c.ipady = 70;
        //c.weighty = 1.0;
        chat.add(js, c);
        
        //c.gridheight = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        chat.add(chatInput,c);
        
    }*/

    private void setUpButtons(){

        exitButton = new MenuButton("exit.png", 1001, 5, 0);
        exitButton.addActionListener(btnListener);

        optionsButton = new MenuButton("ingameoptions.png", 885, 213, 0);
        optionsButton.addActionListener(btnListener);

        backButton = new MenuButton("backtomenu.png", 947, 213, 0);
        backButton.addActionListener(btnListener);

        sendButton = new MenuButton("send.png", 635, 722, 0);
        sendButton.addActionListener(btnListener);
    }

    class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == optionsButton){

            }
            else if(e.getSource() == backButton){

                setVisible(false);      //Sets this frame as not visible
                previous.setFrameVisible();     //Sets the previous as frame visible
            }
            else if(e.getSource() == exitButton){

                System.exit(0);
            }
            else if(e.getSource() == sendButton){
            	
            	chatGui.appendToPane("You: "+chatGui.chatInput.getText()+"\n", Color.WHITE);
            	chatGui.chatInput.setText("");
            }
        }
    }

    class HistoryPanel extends JPanel{

        private BufferedImage whitePeg, blackPeg, greenPeg, bluePeg, yellowPeg, redPeg, evalLU, evalLD, evalRU, evalRD,
                bevalLU, bevalLD, bevalRU, bevalRD;
        private ArrayList<BufferedImage> rounds;
        private ArrayList<BufferedImage> evaluation;

        public HistoryPanel(){

            rounds = new ArrayList();
            evaluation = new ArrayList();

            loadImages();

            for(int i = 0; i < 2; i ++){
                test();
            }

            setBounds(785, 300, 250, 500);
            setOpaque(false);

        }

        private void test(){

            evaluation.add(bevalLU);
            evaluation.add(bevalRU);
            evaluation.add(bevalLD);
            evaluation.add(bevalRD);

            rounds.add(whitePeg);
            rounds.add(redPeg);
            rounds.add(yellowPeg);
            rounds.add(bluePeg);
        }

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);

            int counter = 0;        //Counts peg. Resets when it hits 4.
            int evalCounter = 0;    //Counts evaluation. Resets when it hits 4.
            int round_X = 50;        //The horizontal position in the history panel. Updates up to 4 pegs then resets.
            int eval_X = 175;
            int round_Y = 391;       //The vertical position in the history panel. Updates for every line.
            int eval_Y = 385;

            for(BufferedImage img : rounds){

                if(counter > 3){        //Resets round_X to start of line, moves one line up and resets the counter.
                    round_Y -= 38;
                    round_X = 50;
                    counter = 0;
                }

                g.drawImage(img, round_X, round_Y, null);
                round_X += 31;
                counter ++;
            }

            for(BufferedImage img : evaluation){


                if(evalCounter > 3){
                    eval_X = 175;
                    eval_Y -= 38;
                    evalCounter = 0;
                }
                if(evalCounter == 2){
                    g.drawImage(img, 175, eval_Y +13, null);
                }
                else if(evalCounter == 3){
                    g.drawImage(img, 175+19, eval_Y + 13, null);
                }
                else g.drawImage(img, eval_X, eval_Y, null);

                evalCounter++;
                eval_X += 19;
            }
        }


        private void addToRounds(int id){

            switch (id){
                case 0:
                    rounds.add(null);
                    break;
                case 1:
                    rounds.add(redPeg);
                    break;
                case 2:
                    rounds.add(greenPeg);
                    break;
                case 3:
                    rounds.add(bluePeg);
                    break;
                case 4:
                    rounds.add(yellowPeg);
                    break;
                case 5:
                    rounds.add(whitePeg);
                    break;
                case 6:
                    rounds.add(blackPeg);
                    break;
            }
        }

        private void loadImages(){

            try {

                whitePeg = ImageIO.read(LoadAssets.load("Pegs_Evaluation/white.png"));
                blackPeg = ImageIO.read(LoadAssets.load("Pegs_Evaluation/black.png"));
                greenPeg = ImageIO.read(LoadAssets.load("Pegs_Evaluation/green.png"));
                bluePeg = ImageIO.read(LoadAssets.load("Pegs_Evaluation/blue.png"));
                yellowPeg = ImageIO.read(LoadAssets.load("Pegs_Evaluation/yellow.png"));
                redPeg = ImageIO.read(LoadAssets.load("Pegs_Evaluation/red.png"));
                evalLU = ImageIO.read(LoadAssets.load("Pegs_Evaluation/checkleftup.png"));
                bevalLU = ImageIO.read(LoadAssets.load("Pegs_Evaluation/bcheckleftup.png"));
                evalLD = ImageIO.read(LoadAssets.load("Pegs_Evaluation/checkleftdown.png"));
                bevalLD = ImageIO.read(LoadAssets.load("Pegs_Evaluation/bcheckleftdown.png"));
                evalRU = ImageIO.read(LoadAssets.load("Pegs_Evaluation/checkrightup.png"));
                bevalRU = ImageIO.read(LoadAssets.load("Pegs_Evaluation/bcheckrightup.png"));
                evalRD = ImageIO.read(LoadAssets.load("Pegs_Evaluation/checkrightdown.png"));
                bevalRD = ImageIO.read(LoadAssets.load("Pegs_Evaluation/bcheckrightdown.png"));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void clearList(){
            rounds.clear();
        }
    }


}
