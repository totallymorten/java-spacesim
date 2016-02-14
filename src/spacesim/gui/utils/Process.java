package spacesim.gui.utils;

import spacesim.entities.Entity;

public abstract class Process extends Entity
{
	private long waitTime = 0; // wait time in milliseconds

	public void update(long ms)
	{
		if (waitTime > 0)
		{
			if (waitTime > ms)
			{
				waitTime -= ms;
				return;
			}
			else
				waitTime = 0;
		}
		
		work(ms);
	}

	/**
	 * Suspends the process for 'ms' milliseconds until calling 'work()' again.
	 * 
	 * @param ms
	 */
	public void suspendProcess(long ms)
	{
		waitTime = ms;
	}
	
	public abstract void work(long ms);
}
