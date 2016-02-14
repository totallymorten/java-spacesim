package spacesim.entities.vesselparts;

import spacesim.SpaceSimPanel;

public class Engine extends VesselPart implements EnergyConsumer
{
	double maxEnergyConsumption = 0; // energy consumed at max force (GW)
	double energyPrLevel = 0;
	EngineState state = EngineState.IDLE;
	
	double powerConsumed = 0; // power last consumed
	
	public Engine(double maxEnergyConsumption, double dLevel)
	{
		super(dLevel);
		this.maxEnergyConsumption = maxEnergyConsumption;
		
		energyPrLevel = maxEnergyConsumption / 100;
		
	}

	public void update(long ms)
	{
		super.update(ms);
		
		if (state != EngineState.IDLE)
			consume(calcEnergyConsumption());
		else
			powerConsumed = 0;
	}
	
	public double getCurrentForceOutput()
	{
		if (state == EngineState.IDLE)
			return 0;
		else
			return calcForceOutput();
	}
	
	public double calcForceOutput()
	{
		//return maxForce * level * 0.01;
		// multiplying with 10^9 for GW
		double force = Math.sqrt(100 * powerConsumed * 1e9 / SpaceSimPanel.ship.getMass()) * SpaceSimPanel.ship.getMass();
		
		return (state == EngineState.RETRO_BURN) ? -force : force;
	}
	
	public double calcSettingForceOutput()
	{
		//return maxForce * level * 0.01;
		// multiplying with 10^9 for GW
		double force = Math.sqrt(100 * calcEnergyConsumption() * 1e9 / SpaceSimPanel.ship.getMass()) * SpaceSimPanel.ship.getMass();
		
		return (state == EngineState.RETRO_BURN) ? -force : force;
	}
	
	
	public double calcEnergyConsumption()
	{
		return energyPrLevel * level;
	}

	public double consume(double gw)
	{
		powerConsumed = usePower(gw);
		
		return powerConsumed;
	}

	@Override
	public double getMaxAvailableLevel()
	{
		return availablePower() / energyPrLevel;
	}

	public EngineState getState()
	{
		return state;
	}

	public void setState(EngineState state)
	{
		this.state = state;
	}

}
