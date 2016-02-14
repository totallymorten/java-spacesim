package spacesim.tools;

import java.text.NumberFormat;
import java.util.Locale;

import spacesim.SpaceSimPanel;

public abstract class Tools
{
	private static NumberFormat nf = NumberFormat.getInstance(Locale.US);

	static
	{
		Tools.nf.setMaximumFractionDigits(2);
		Tools.nf.setMinimumFractionDigits(2);
		
	}
	
	public static String nf(double d)
	{
		return Tools.nf.format(d);
	}

	// distance formatting method (if > 1.000 m then km)
	public static String nf_dist(double d)
	{
		if (d < 1000 && d > -1000)
			return Tools.nf.format(d) + " m";
		else
			return	Tools.nf.format(d/1000) + " km";
	}

	//add speed formatting method (if > 1.000 m/s then km/s)
	public static String nf_speed(double d)
	{
		if (d < 1000 && d > -1000)
			return Tools.nf.format(d) + " m/s";
		else
			return	Tools.nf.format(d/1000) + " km/s";
	}
	
	public static double GWhrs2GWperiodms(double GWhrs)
	{
		return GWhrs * (60 * 60 * 1000) / SpaceSimPanel.period;
	}
	
	public static double GWperiodms2GWhrs(double GWperiodms)
	{
		return GWperiodms / (60 * 60 * 1000) * SpaceSimPanel.period;
	}
	
}
