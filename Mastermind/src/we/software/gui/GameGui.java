package we.software.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import we.software.mastermind.Client;
import we.software.mastermind.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Bill on 06-Apr-17.
 * This class is the frame that hosts each game.
 */
public class GameGui extends JFrame{

    private MainMenu previous;
    private final int WIDTH = 1024;
    private final int HEIGHT = WIDTH / 12*9;
    private ButtonListener btnListener = new ButtonListener();
    private MenuButton exitButton, optionsButton, backButton, sendButton;   //Funcionality buttons
    public HistoryPanel turnHistory;                                       //The panel that holds all the turns of the game
    private ChatGui chatGui;                                                //The chat
    private KeyInput kp;
    private Client client;                                                  //The client for the chat to work
    public SelectionButton selectionBtn1, selectionBtn2, selectionBtn3, selectionBtn4, checkBtn;
    private MenuButton redBtn, greenBtn, blueBtn, yellowBtn, whiteBtn, blackBtn;
    private SelectionButton sBtn;
    public NumbersPanel numbersPanel;
    public Game game;

    private int selectedBtn = -1;
    private int turn = 1;
    private int[] turnGuess = {0, 0, 0, 0};
    private boolean notValid = false;
    
    public GameGui(MainMenu previous){
    	// edw tha prepei na dimiourgritai ena instance Game
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
        client = new Client();

        kp = new KeyInput();


        turnHistory = new HistoryPanel();
        numbersPanel = new NumbersPanel();

        chatGui = new ChatGui();
        chatGui.start();
        
        this.getRootPane().setDefaultButton(sendButton);
        
        selectionBtn1.setColored(1);
        selectionBtn1.setUnselected();
        add(exitButton);
        add(optionsButton);
        add(backButton);
        add(sendButton);
        add(selectionBtn1);
        add(selectionBtn2);
        add(selectionBtn3);
        add(selectionBtn4);
        add(checkBtn);
        add(blackBtn);
        add(whiteBtn);
        add(yellowBtn);
        add(redBtn);
        add(greenBtn);
        add(blueBtn);
        add(turnHistory);
        add(numbersPanel);
        add(chatGui);

        setSize(WIDTH, HEIGHT);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        
        /*try {
        	client.startListening(chatGui);
			client.logMeIn("test1", "test1");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    }
    public void hscores(){
    	//Jpanel hscores
    }

    /**
     * In this method all buttons of the frame are initialized.
     * ~Great functionality comes with great responsibility.~
     */
    private void setUpButtons(){

        exitButton = new MenuButton("exit.png", 1001, 5, 0, 0);
        exitButton.addActionListener(btnListener);

        optionsButton = new MenuButton("ingameoptions.png", 885, 210, 0, 0);
        optionsButton.addActionListener(btnListener);

        backButton = new MenuButton("backtomenu.png", 947, 210, 0, 0);
        backButton.addActionListener(btnListener);

        sendButton = new MenuButton("send.png", 635, 722, 0, 0);
        sendButton.addActionListener(btnListener);

        selectionBtn1 = new SelectionButton("glassButton.png", 263, 450);
        selectionBtn1.addActionListener(btnListener);

        selectionBtn2 = new SelectionButton("glassButton.png", 387, 450);
        selectionBtn2.addActionListener(btnListener);

        selectionBtn3 = new SelectionButton("glassButton.png", 510, 450);
        selectionBtn3.addActionListener(btnListener);

        selectionBtn4 = new SelectionButton("glassButton.png", 635, 450);
        selectionBtn4.addActionListener(btnListener);

        checkBtn = new SelectionButton("CheckButton.png", 15, 450);
        checkBtn.addActionListener(btnListener);

        redBtn = new MenuButton("redbtn.png", 332, 365 , 0, 0);
        redBtn.addActionListener(btnListener);

        greenBtn = new MenuButton("greenbtn.png", 394, 365, 0, 0);
        greenBtn.addActionListener(btnListener);

        blueBtn = new MenuButton("bluebtn.png", 458, 365, 0, 0);
        blueBtn.addActionListener(btnListener);

        yellowBtn = new MenuButton("yellowbtn.png", 522, 365, 0, 0);
        yellowBtn.addActionListener(btnListener);

        whiteBtn = new MenuButton("whitebtn.png", 583, 365, 0, 0);
        whiteBtn.addActionListener(btnListener);

        blackBtn = new MenuButton("blackbtn.png", 632, 365, 0, 0);
        blackBtn.addActionListener(btnListener);
        
    }

    public class NumbersPanel extends JPanel{

        private ArrayList<BufferedImage> numbers;
        private int round = 1;

        public NumbersPanel(){

            numbers = (ArrayList<BufferedImage>) PreloadImages.getNumgers().clone();

            setBounds(240, 381, numbers.get(round-1).getWidth(), numbers.get(round-1).getHeight());
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g){
            g.drawImage(numbers.get(round-1), 0, 0, null);
        }

        public void changeRound(){

            round++;
            if(round > 10) round = 10;
            setBounds(240, 381, numbers.get(round-1).getWidth(), numbers.get(round-1).getHeight());
            repaint();
        }
    }

    /**
     * Special panel that shows the guess and the corresponding evaluation for each turn.
     */
    public class HistoryPanel extends JPanel{

        private BufferedImage whitePeg, blackPeg, greenPeg, bluePeg, yellowPeg, redPeg, evalLU, evalLD, evalRU, evalRD,
                bevalLU, bevalLD, bevalRU, bevalRD;
        private ArrayList<BufferedImage> rounds;
        private ArrayList<BufferedImage> evaluation;

        public HistoryPanel(){

            rounds = new ArrayList();
            evaluation = new ArrayList();

            loadImages();

            setBounds(785, 300, 250, 500);
            setOpaque(false);

        }

        /**
         *  To show each turn in the special history panel, the code overrides paintComponent to paint the contents of
         *  the two arrayLists, rounds and evaluation which hold the guess and evaluation of the guess for each turn.
         *  Anytime a round is added to the arrayLists the code calls for the repaint method which repaints the content
         *  of the arrayLists.
         */
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);

            int counter = 0;        //Counts peg. Resets when it hits 4.
            int evalCounter = 0;    //Counts evaluation. Resets when it hits 4.
            int round_X = 50;       //Represents the width value for the colors. Updates up to 4 colors then resets.
            int eval_X = 175;       //Represents the width value for the evaluation. Updates up to 2 and then resets.
            int round_Y = 391;
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


        public void addToRounds(int id){

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

        private void clearLists(){

            rounds.clear();
            evaluation.clear();
        }
    }

    public void setInvisible(){

        setVisible(false);
        restartFrame();
    }

    public void restartFrame(){

        if(sBtn != null){
            sBtn.setUnselected();
            sBtn = null;
        }
        selectionBtn1.setUncolored();
        selectionBtn2.setUncolored();
        selectionBtn3.setUncolored();
        selectionBtn4.setUncolored();
        turn = 1;
        numbersPanel.round = 1;
        numbersPanel.repaint();
        turnHistory.clearLists();
    }


    class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == selectionBtn1){

                selectionBtn1.staySelected();
                if(sBtn != null && sBtn != selectionBtn1) sBtn.setUnselected();
                selectedBtn = 0;
                sBtn = selectionBtn1;
            }
            else if(e.getSource() == selectionBtn2){

                selectionBtn2.staySelected();
                if(sBtn != null && sBtn != selectionBtn2) sBtn.setUnselected();
                selectedBtn = 1;
                sBtn = selectionBtn2;
            }
            else if(e.getSource() == selectionBtn3){

                selectionBtn3.staySelected();
                if(sBtn != null && sBtn != selectionBtn3) sBtn.setUnselected();
                selectedBtn = 2;
                sBtn = selectionBtn3;
            }
            else if(e.getSource() == selectionBtn4){

                selectionBtn4.staySelected();
                if(sBtn != null && sBtn != selectionBtn4) sBtn.setUnselected();
                selectedBtn = 3;
                sBtn = selectionBtn4;
            }
            else if(e.getSource() == checkBtn){

                if(sBtn != null){
                    sBtn.setUnselected();
                    sBtn = null;
                }

                for(int i=0; i<turnGuess.length; i++){

                    if(turnGuess[i] == 0){
                        notValid = true;
                        break;
                    }
                }

                if(!notValid && turn <= 10){

                    for(int i=0; i<turnGuess.length; i++){
                        turnHistory.addToRounds(turnGuess[i]);
                        turnGuess[i] = 0;
                    }
                    selectionBtn1.setUncolored();
                    selectionBtn2.setUncolored();
                    selectionBtn3.setUncolored();
                    selectionBtn4.setUncolored();
                    turnHistory.repaint();

                    turn += 1;
                    numbersPanel.changeRound();
                }

                notValid = false;
            }
            else if(e.getSource() == redBtn){

                if(sBtn != null){

                    sBtn.setColored(0);
                    sBtn.setUnselected();
                    turnGuess[selectedBtn] = 1;
                    /*if(game.gameType==0){
                    	game.p1.addPin(selectedBtn, 1);
                    }
                    else if(game.gameType==1){
                    	if(!client.isCodeMaker()){
                    	try {
							client.sendGamePin(selectedBtn, 1);
						} catch (IOException e1) {
							System.out.println("Can t send game pin");
						}
                    	}
                    	else{
                    		client.addPin(selectedBtn, 1);
                    	}
                    }*/
                    sBtn = null;
                }
            }
            else if(e.getSource() == greenBtn){

                if(sBtn != null){

                    sBtn.setColored(1);
                    sBtn.setUnselected();
                    turnGuess[selectedBtn] = 2;
                    /*if(game.gameType==0){
                	game.p1.addPin(selectedBtn, 2);
                }
                else if(game.gameType==1){
                	if(!client.isCodeMaker()){
                	try {
						client.sendGamePin(selectedBtn, 2);
					} catch (IOException e1) {
						System.out.println("Can t send game pin");
					}
                	}
                	else{
                		client.addPin(selectedBtn, 2);
                	}
                }*/
                    sBtn = null;
                }
            }
            else if(e.getSource() == blueBtn){

                if(sBtn != null){

                    sBtn.setColored(2);
                    sBtn.setUnselected();
                    turnGuess[selectedBtn] = 3;
                    /*if(game.gameType==0){
                	game.p1.addPin(selectedBtn, 3);
                }
                else if(game.gameType==1){
                	if(!client.isCodeMaker()){
                	try {
						client.sendGamePin(selectedBtn, 3);
					} catch (IOException e1) {
						System.out.println("Can t send game pin");
					}
                	}
                	else{
                		client.addPin(selectedBtn, 3);
                	}
                }*/
                    sBtn = null;
                }
            }
            else if(e.getSource() == yellowBtn){

                if(sBtn != null){

                    sBtn.setColored(3);
                    sBtn.setUnselected();
                    turnGuess[selectedBtn] = 4;
                    /*if(game.gameType==0){
                	game.p1.addPin(selectedBtn, 4);
                }
                else if(game.gameType==1){
                	if(!client.isCodeMaker()){
                	try {
						client.sendGamePin(selectedBtn, 4);
					} catch (IOException e1) {
						System.out.println("Can t send game pin");
					}
                	}
                	else{
                		client.addPin(selectedBtn, 4);
                	}
                }*/
                    sBtn = null;
                }
            }
            else if(e.getSource() == whiteBtn){

                if(sBtn != null){

                    sBtn.setColored(4);
                    sBtn.setUnselected();
                    turnGuess[selectedBtn] = 5;
                    /*if(game.gameType==0){
                	game.p1.addPin(selectedBtn, 5);
                }
                else if(game.gameType==1){
                	if(!client.isCodeMaker()){
                	try {
						client.sendGamePin(selectedBtn, 5);
					} catch (IOException e1) {
						System.out.println("Can t send game pin");
					}
                	}
                	else{
                		client.addPin(selectedBtn, 5);
                	}
                }*/
                    sBtn = null;
                }
            }
            else if(e.getSource() == blackBtn){

                if(sBtn != null){

                    sBtn.setColored(5);
                    sBtn.setUnselected();
                    turnGuess[selectedBtn] = 6;
                    /*if(game.gameType==0){
                	game.p1.addPin(selectedBtn, 6);
                }
                else if(game.gameType==1){
                	if(!client.isCodeMaker()){
                	try {
						client.sendGamePin(selectedBtn, 6);
					} catch (IOException e1) {
						System.out.println("Can t send game pin");
					}
                	}
                	else{
                		client.addPin(selectedBtn, 6);
                	}
                }*/
                    sBtn = null;
                }
            }
            else if(e.getSource() == backButton){

                setVisible(false);                          //Sets this frame as not visible
                previous.setFrameVisible(GameGui.this);     //Sets the previous as frame visible
                if(sBtn != null){

                    sBtn.setUnselected();
                    sBtn = null;
                }
            }
            else if(e.getSource() == optionsButton){

                if(sBtn != null){

                    sBtn.setUnselected();
                    sBtn = null;
                }
            }
            else if(e.getSource() == exitButton) {

                System.exit(0);
            }
            else if((e.getSource() == sendButton) && !(chatGui.chatInput.getText().equals("") || chatGui.chatInput.getText().equals(" "))){
                try{
                    client.sendMessage(chatGui.chatInput.getText());
                    chatGui.appendToPane("You: "+chatGui.chatInput.getText()+"\n", 0);
                    chatGui.chatInput.setText("");
            	/*}
            		else{
            		chatGui.appendToPane("Message couldn t send...\n", Color.RED);
            		chatGui.appendToPane("Either your message format isn t right(receiver:message)\n",Color.RED);
            		chatGui.appendToPane("or you have lost connection with Server...\n", Color.RED);
            		chatGui.chatInput.setText("");
            	}*/
                }catch(IOException ie){
                    System.out.println(ie.getStackTrace());
                }
                if(sBtn != null){

                    sBtn.setUnselected();
                    sBtn = null;
                }

            }
        }
    }
}
