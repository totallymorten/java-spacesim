package spacesim.entities;

import spacesim.entities.vesselparts.ElectricalComponent;
import spacesim.entities.vesselparts.Engine;
import spacesim.entities.vesselparts.PowerBuffer;
import spacesim.entities.vesselparts.Reactor;
import math.Vector3d;

public abstract class Vessel extends PhysicalEntity
{
	private Vector3d direction = new Vector3d(1,0,0);
	private Vector3d destination = new Vector3d(0,0,0);
	private String destinationName = "";
	private Engine engine;
	private Reactor reactor;
	private PowerBuffer powerBuffer;
	
	public Vessel(double mass, Vector3d pos)
	{
		super(mass, pos);
	}
	
	public void update(long ms)
	{
		if (reactor != null)
		{
			reactor.update(ms);
		}
		
		if (powerBuffer != null)
		{
			powerBuffer.update(ms);
		}

		if (engine != null)
		{
			engine.update(ms);
			acceleration = direction.mult(engine.getCurrentForceOutput() / mass);
		}
		
		super.update(ms);
	}	
	
	
	public Reactor getReactor()
	{
		return reactor;
	}

	public void setReactor(Reactor reactor)
	{
		this.reactor = reactor;
	}

	public Engine getEngine()
	{
		return engine;
	}
	
	public void setEngine(Engine engine)
	{
		this.engine = engine;

		if (getPowerBuffer() != null) ElectricalComponent.connect(getEngine(), getPowerBuffer());
	}

	public Vector3d getDirection()
	{
		return direction;
	}

	public void setDirection(Vector3d direction)
	{
		this.direction = direction;
	}

	public PowerBuffer getPowerBuffer()
	{
		return powerBuffer;
	}

	public void setPowerBuffer(PowerBuffer powerBuffer)
	{
		this.powerBuffer = powerBuffer;
		
		if (getReactor() != null) ElectricalComponent.connect(getPowerBuffer(), getReactor());
	}

	public Vector3d getDestination()
	{
		return destination;
	}

	public void setDestination(Vector3d destination)
	{
		this.destination = destination;
		
		this.setDirection(destination.sub(position).unit());
	}

	public String getDestinationName()
	{
		return destinationName;
	}

	public void setDestinationName(String destinationName)
	{
		this.destinationName = destinationName;
	}

}
