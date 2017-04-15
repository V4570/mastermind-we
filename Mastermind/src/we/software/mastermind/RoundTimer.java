package we.software.mastermind;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class RoundTimer extends JPanel implements Runnable{
	Timer timer;
	JLabel timerLabel;
	int count;
	public RoundTimer(int count){
		this.count = count;
		timerLabel = new JLabel("30", SwingConstants.CENTER);
		timerLabel.setOpaque(false);
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
			counter--;
			if(counter >= 1){
				timerLabel.setText(Integer.toString(counter));
			}else{
				timer.stop();
				timerLabel.setText("0");
			}
			
		}
	}
	

}
