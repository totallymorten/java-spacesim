package math;

import spacesim.tools.Tools;

public class Vector3d
{
	public double x;
	public double y;
	public double z;
	
	private double length = -1;
	
	public Vector3d(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3d mult(Vector3d other)
	{
		return new Vector3d(this.x * other.x, this.y * other.y, this.z * other.z);
	}
	
	public Vector3d add(Vector3d other)
	{
		return new Vector3d(this.x + other.x, this.y + other.y, this.z + other.z);
	}
	

	public Vector3d mult(double other)
	{
		return new Vector3d(this.x * other, this.y * other, this.z * other);
	}
	
	public Vector3d sub(Vector3d other)
	{
		return new Vector3d(this.x - other.x, this.y - other.y, this.z - other.z);
	}
	
	public Vector3d div(double other)
	{
		return new Vector3d(x / other, y / other, z / other);
	}
	
	public double length()
	{
		if (length == -1)
		{
			length = Math.abs(Math.sqrt(x * x + y * y + z * z)); 
		}
		
		return length;
	}
	
	public Vector3d unit()
	{
		double length = length();
		
		return new Vector3d(x / length, y / length, z / length);
	}

	public double distance(Vector3d other)
	{
		return other.sub(this).length();
	}
	
	@Override
	public String toString()
	{
		return "Vector3d [x: "+Tools.nf(x)+"], [y: "+Tools.nf(y)+"], [z: "+Tools.nf(z)+"]";
	}
	
	
	
}
