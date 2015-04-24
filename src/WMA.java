import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class WMA extends JFrame
{
		
	WiimotePanel wiimotepanel;
	
	WMA()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 800);
		setVisible(true);
		setLocationRelativeTo(null);
		
		wiimotepanel = new WiimotePanel();
		add(wiimotepanel);
	}
	
	
	public static void main(String args[])
	{
		
		EventQueue.invokeLater(new Runnable()
		{
			
			public void run()
			{
				WMA wma = new WMA();
			}
			
			
		});
		
	}
}


