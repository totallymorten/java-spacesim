package spacesim.entities;

import math.Vector3d;

public abstract class PhysicalEntity extends Entity
{
	Vector3d position;
	Vector3d speed = new Vector3d(0,0,0); // m/s
	double mass; // mass in kg
	Vector3d acceleration = new Vector3d(0,0,0); // m/s^2
	
	public PhysicalEntity(double mass, Vector3d position)
	{
		this.mass = mass;
		this.position = position;
	}
	

	public void update(long ms)
	{
		position = position.add(speed.mult(ms * 1e-3));
		speed = speed.add(acceleration.mult(ms * 1e-3));
		
		if (speed.length() < 1e-7)
		{
			speed = new Vector3d(0,0,0);
		}
			
	}
	
	public Vector3d getPosition()
	{
		return position;
	}
	public void setPosition(Vector3d position)
	{
		this.position = position;
	}
	public Vector3d getSpeed()
	{
		return speed;
	}
	public void setSpeed(Vector3d speed)
	{
		this.speed = speed;
	}
	
	public double getMass()
	{
		return mass;
	}


	public Vector3d getAcceleration()
	{
		return acceleration;
	}


	public void setAcceleration(Vector3d acceleration)
	{
		this.acceleration = acceleration;
	}
}
