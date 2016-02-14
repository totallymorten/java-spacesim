package spacesim.entities.vesselparts;

import spacesim.tools.Tools;

public class PowerBuffer extends ElectricalComponent implements EnergySupplier, EnergyConsumer
{

	double storedPower = 0; // GWhrs
	double storageCapacity; // GWhrs
	
	public PowerBuffer(double storageCapacity)
	{
		this.storageCapacity = storageCapacity;
	}
	
	public void update(long ms)
	{
		if (storedPower < storageCapacity)
		{
			double diff = Tools.GWhrs2GWperiodms(storageCapacity - storedPower);
			consume(diff); // get all available power
		}
	}

	public double checkPower()
	{
		return Tools.GWhrs2GWperiodms(storedPower);
	}

	public double supply(double gw)
	{
		double suppliedPower = 0;
		if (gw == -1)
		{
			suppliedPower = checkPower();
		}
		else if (gw > checkPower())
		{
			suppliedPower = checkPower();
		}
		else
			suppliedPower = gw;
		
		storedPower -= Tools.GWperiodms2GWhrs(suppliedPower);
		
		return suppliedPower;
	}
	
	public double consume(double gw)
	{
		double power = usePower(gw);
		storedPower += Tools.GWperiodms2GWhrs(power);
		return power;
	}

	public double getStoredPower()
	{
		return storedPower;
	}

	public double getStorageCapacity()
	{
		return storageCapacity;
	}

	
	
}
