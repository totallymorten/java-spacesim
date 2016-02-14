package spacesim;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class SpaceSim extends JFrame implements WindowListener
{
	public static int DEFAULT_FPS = 20;
	SpaceSimPanel sp;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public SpaceSim(long period)
	{
		this.addWindowListener(this);
		sp = new SpaceSimPanel(this, period);
		this.setSize(sp.getPreferredSize());
		this.setContentPane(sp);
		this.setVisible(true);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		int fps = DEFAULT_FPS;
		long period = (long) (1000.0 / fps); // ms
		
		SpaceSim wc = new SpaceSim(period);
	}

	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	public void windowClosing(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	public void windowDeactivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	public void windowOpened(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

}
