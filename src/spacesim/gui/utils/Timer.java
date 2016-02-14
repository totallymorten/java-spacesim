package spacesim.gui.utils;

import spacesim.SpaceSimPanel;
import spacesim.entities.Updateable;

public abstract class Timer implements Updateable
{
	private double time;
	private double timeLeft;
	
	public Timer(double time)
	{
		this.time = time;
		this.timeLeft = time;
	}

	public void update(long ms)
	{
		//System.out.println("Timer.update(): timer now ["+timeLeft+"]");
		
		timeLeft -= ms;
		
		if (timeLeft <= 0)
		{
			timerDoneAction();
			SpaceSimPanel.addRemovalRequest(this);
		}
		
	}
	
	public abstract void timerDoneAction();
}
