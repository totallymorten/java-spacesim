package spacesim.entities.vesselparts;

public interface EnergySupplier
{
	public abstract double supply(double gw);
	public abstract double checkPower();
}
