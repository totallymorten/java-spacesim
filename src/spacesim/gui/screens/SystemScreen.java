package spacesim.gui.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import spacesim.SpaceSimPanel;
import spacesim.entities.vesselparts.Engine;
import spacesim.entities.vesselparts.PowerBuffer;
import spacesim.entities.vesselparts.Reactor;
import spacesim.entities.vessels.Ship;
import spacesim.gui.utils.GUIUtils;
import spacesim.tools.Tools;

public class SystemScreen extends Screen 
{
	private Ship ship = SpaceSimPanel.ship;
	
	private int textPosX = 10;
	private int textPosY = 10;

	
	public SystemScreen()
	{
		addNavMenu();
	}


	public void render(Graphics renderG) 
	{
		renderG.setFont(new Font("Courier New",Font.PLAIN, 16));

		// setting background
		renderG.setColor(Color.black);
		renderG.fillRect(0, 0, SpaceSimPanel.WIDTH, SpaceSimPanel.HEIGHT);
		
		renderG.setColor(Color.green);
		// REACTOR INFO
		Reactor reactor = ship.getReactor();
		String[] boxtext = new String[2];
		String title = "REACTOR";
		boxtext[0] = "Reactor level: " + Tools.nf(reactor.getLevel()) + " % (" + Tools.nf(reactor.calcOutput()) + " GW)";
		boxtext[1] = "Reactor fuel: "+Tools.nf(reactor.getFuel())+" units ("+Tools.nf(reactor.calcFuelFrac())+" %)";
		GUIUtils.drawBox(renderG, title, boxtext, textPosX, textPosY, 450, 100);
		
		// ENGINE INFO
		Engine engine = ship.getEngine();
		title = "ENGINE";
		boxtext = new String[] {
				"Level: " + Tools.nf(engine.getLevel()) + " % ("+Tools.nf(engine.getCurrentForceOutput())+" N)",
				"Energy usage: " + Tools.nf(engine.calcEnergyConsumption()) + " GW",
				"State: " + engine.getState()
		};
		GUIUtils.drawBox(renderG, title, boxtext, textPosX, textPosY + 120, 450, 100);
		
		// Power buffer
		PowerBuffer buf = ship.getPowerBuffer();
		title = "POWER BUFFER";
		boxtext = new String[] {
				"Stored power: "+Tools.nf(buf.getStoredPower())+" GWhrs ("+Tools.nf((buf.getStoredPower()/buf.getStorageCapacity() * 100))+" %)"
		};
		GUIUtils.drawBox(renderG, title, boxtext, 500, textPosY, 500, 60);
		
		renderParts(renderG);
	}

}
