package spacesim.entities.vesselparts;

import java.util.ArrayList;

import spacesim.entities.Entity;

public abstract class ElectricalComponent extends Entity
{
	private ArrayList<EnergyConsumer> connectedConsumers = new ArrayList<EnergyConsumer>();
	private ArrayList<EnergySupplier> connectedSuppliers = new ArrayList<EnergySupplier>();
	
	public void connect(ElectricalComponent ec)
	{
		if (ec instanceof EnergyConsumer) connectedConsumers.add((EnergyConsumer) ec);
		if (ec instanceof EnergySupplier) connectedSuppliers.add((EnergySupplier) ec);
	}
	
	public static void connect(ElectricalComponent ec1, ElectricalComponent ec2)
	{
		ec1.connect(ec2);
		ec2.connect(ec1);
	}
	
	/**
	 * Used for the consumption of power in this object. Draws power from all
	 * connected suppliers
	 * 
	 * @param gw - the required amount of watts
	 * @return totalGw - the acquired amount of watts
	 */
	public double usePower(double gw)
	{
		double totalGw = 0;
		
		for (EnergySupplier supplier : connectedSuppliers)
		{
			if (totalGw == gw)
				return totalGw;
			
			totalGw += supplier.supply(gw - totalGw);
		}
		
		return totalGw;
	}
	
	/**
	 * Returns the maximal available power to this component
	 * 
	 * @return totalGw - the power available for consumption for this component
	 */
	public double availablePower()
	{
		double totalGw = 0;
		
		for (EnergySupplier supplier : connectedSuppliers)
		{
			totalGw += supplier.checkPower();
		}
		
		return totalGw;		
	}
}
