package spacesim.gui.screens;

import java.awt.Graphics;
import java.util.ArrayList;

public abstract class Screen implements Renderable
{
	ArrayList<ScreenPart> screenParts = new ArrayList<ScreenPart>();
	
	public Screen()
	{
	}
	
	
	public void addNavMenu()
	{
		addPart(new ScreenMenuPart(10,630));		
	}
	
	public void addPart(ScreenPart part)
	{
		screenParts.add(part);
	}
	
	public void renderParts(Graphics g)
	{
		for (ScreenPart part : screenParts)
		{
			part.render(g);
		}
	}
}
