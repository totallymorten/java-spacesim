package spacesim.gui.screens;

public abstract class ScreenPart implements Renderable
{
	int posX = 0;
	int posY = 0;

	public ScreenPart(int x, int y)
	{
		this.posX = x;
		this.posY = y;
	}
}
