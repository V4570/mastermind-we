package we.software.gui;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class RoundTimerPanel extends JPanel implements Runnable{
	Timer timer;
	JLabel timerLabel;
	int count;
	public RoundTimerPanel(int count, int width , int height, int xPos, int yPos, boolean presetedText){
		//BorderLayout borders = new BorderLayout();
		this.count = count;
		this.setSize(width, height);
		this.setLocation(xPos, yPos);
		this.setOpaque(false);
		if(presetedText){
		timerLabel = new JLabel(Integer.toString(count), SwingConstants.CENTER);
		}else{
			timerLabel = new JLabel();
		}
			
		timerLabel.setOpaque(false);
		timerLabel.setFont(new Font("Arial",Font.PLAIN,28));
		timerLabel.setForeground(Color.WHITE);
		this.add(timerLabel);
		
	}
	
	public void run(){
		TimeClass tc = new TimeClass(count);
		timer = new Timer(1000, tc);
		timer.start();
		
		
	}
	
	public class TimeClass implements ActionListener{
		int counter;
		
		public TimeClass(int counter){
			this.counter = counter;
			
			
		}
		
		public void actionPerformed(ActionEvent tc){
			
			if(counter >= 1){
				timerLabel.setText(Integer.toString(counter));
				System.out.println(counter);
			}else{
				timer.stop();
				timerLabel.setText("0");
			}
			counter--;
		}
	}
	

}
