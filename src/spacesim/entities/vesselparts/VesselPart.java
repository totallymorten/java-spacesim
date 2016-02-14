package spacesim.entities.vesselparts;


public abstract class VesselPart extends ElectricalComponent
{
	double setting = 0; // current % setting
	double level = 0; // output in %
	double dLevel = 0; // delta level pr ms

	public VesselPart(double dLevel)
	{
		this.dLevel = dLevel;
	}
	
	public void update(long ms)
	{
		updateLevel(ms);
	}
	
	public abstract double getMaxAvailableLevel();
	
	private void updateLevel(long ms)
	{
		double delta = (setting < level) ? -dLevel : dLevel;
		
		double newLevel = ms * delta + level;
		double maxLevel = getMaxAvailableLevel();
		
		if (level > maxLevel)
		{
			newLevel = maxLevel;
		}
		else if (newLevel > maxLevel)
		{
			newLevel = level;
		}
		
		if (delta > 0 && newLevel > setting)
			newLevel = setting;
		else if (delta < 0 && newLevel < setting)
			newLevel = setting;
		
		level = newLevel;
	}

	public double getSetting()
	{
		return setting;
	}

	public void setSetting(double setting)
	{
		this.setting = setting;
	}

	public double getLevel()
	{
		return level;
	}

	public double getDLevel()
	{
		return dLevel;
	}
	
	

}
