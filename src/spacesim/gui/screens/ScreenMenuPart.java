package spacesim.gui.screens;

import java.awt.Graphics;

public class ScreenMenuPart extends ScreenPart
{
	public ScreenMenuPart(int x, int y)
	{
		super(x,y);
	}
	
	public void render(Graphics g)
	{
		g.drawRoundRect(posX, posY, 100, 30, 120,40);
		g.drawString("F1 - NAV", posX + 10, posY + 20);
		
		int offset = 130;

		g.drawRoundRect(posX + offset, posY, 100, 30, 120,40);
		g.drawString("F2 - SYS", posX + 10 + offset, posY + 20);
}

}
