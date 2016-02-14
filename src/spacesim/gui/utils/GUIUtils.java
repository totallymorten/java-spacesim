package spacesim.gui.utils;

import java.awt.Graphics;


public abstract class GUIUtils
{
	public static void drawBox(Graphics g, String title, String[] texts, int x, int y, int width, int height)
	{
		int titleboxHeight = (int) 30;
		int mainboxHeight = (int) height - 30;
		
		int textX = x + 10;
		int textY = y + 20;
		
		g.drawRect(x, y, width, titleboxHeight);
		g.drawRect(x, y+titleboxHeight, width, mainboxHeight);

		
		g.drawString(title, textX, textY);
		
		textY += titleboxHeight; 		
		for (String s : texts)
		{
			g.drawString(s, textX, textY);
			textY += 20;
		}
	}
}
