package spacesim.entities.vesselparts;

public class Reactor extends VesselPart implements EnergySupplier
{
	int maxOutput = 0; // GW
	double fuel = 0; // units
	double maxFuel = 0; // units
	double fuelEnergiRate = 0; // GWh / unit
		
	double currentOutput = 0;
	double currentFuelUsage = 0;
	
	public Reactor(int maxOutput, double fuel, double maxFuel, double fuelEnergiRate, double dLevel)
	{
		super(dLevel);
		this.maxOutput = maxOutput;
		this.fuel = fuel;
		this.maxFuel = maxFuel;
		this.fuelEnergiRate = fuelEnergiRate;	
		
	}

	public void update(long ms)
	{
		super.update(ms);
		updateFuel(ms);
		currentOutput = calcOutput();
	}
	
	private void updateFuel(long ms)
	{
		double GWmsPrUnit = fuelEnergiRate * (60 * 60 * 1000);
		double consumpsionGWms = level * 0.01 * maxOutput;
		double units = consumpsionGWms/GWmsPrUnit;
		
		if ((fuel - units) < 0)
		{
			double lessfactor = fuel / units;
			units = fuel;
			level *= lessfactor;
		}
	
		currentFuelUsage = units;
		
		fuel -= units;
	}
	
	public double calcFuelFrac()
	{
		return fuel/maxFuel * 100;
	}
	
	
	public double calcOutput()
	{
		return level * 0.01 * maxOutput;
	}

	public int getMaxOutput() {
		return maxOutput;
	}

	public void setMaxOutput(int maxOutput) {
		this.maxOutput = maxOutput;
	}

	public double getDOutput() {
		return dLevel;
	}

	public void setDOutput(double output) {
		dLevel = output;
	}

	public double getFuel() {
		return fuel;
	}

	public void setFuel(double fuel) {
		this.fuel = fuel;
	}

	public double getMaxFuel() {
		return maxFuel;
	}

	public void setMaxFuel(double maxFuel) {
		this.maxFuel = maxFuel;
	}

	public double getFuelEnergiRate() {
		return fuelEnergiRate;
	}

	public void setFuelEnergiRate(double fuelEnergiRate) {
		this.fuelEnergiRate = fuelEnergiRate;
	}

	@Override
	public double getMaxAvailableLevel()
	{
		return 100;
	}

	public double supply(double gw)
	{
		if (gw == -1)
		{
			return currentOutput;
		}
		else if (gw > currentOutput)
			return currentOutput;
		else
			return gw;
	}

	public double checkPower()
	{
		return currentOutput;
	}
}
