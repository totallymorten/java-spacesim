package spacesim.gui.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import spacesim.SpaceSimPanel;
import spacesim.entities.vessels.Ship;
import spacesim.gui.utils.GUIUtils;
import spacesim.tools.Tools;

public class NavScreen extends Screen
{
	Ship ship = SpaceSimPanel.ship;
	int textPosX = 10;
	int textPosY = 10;
	
	public NavScreen()
	{
		addNavMenu();
	}
	
	public void render(Graphics renderG)
	{
		int x = textPosX;
		int y = textPosY;
		
		renderG.setFont(new Font("Courier New",Font.PLAIN, 16));

		// setting background
		renderG.setColor(Color.black);
		renderG.fillRect(0, 0, SpaceSimPanel.WIDTH, SpaceSimPanel.HEIGHT);
		
		renderG.setColor(Color.green);

		// Ship info
		String title = "SHIP";
		String[] boxtext = new String[] {
		"Ship position:",
		"         [x: " + Tools.nf_dist(ship.getPosition().x) + "]",
		"         [y: " + Tools.nf_dist(ship.getPosition().y) + "]",
		"         [z: " + Tools.nf_dist(ship.getPosition().z) + "]",
		"Ship speed:",
		"         [x: " + Tools.nf_speed(ship.getSpeed().x) + "]",
		"         [y: " + Tools.nf_speed(ship.getSpeed().y) + "]",
		"         [z: " + Tools.nf_speed(ship.getSpeed().z) + "]",
		"Ship acceleration:",
		"         [x: " + Tools.nf_dist(ship.getAcceleration().x) + "/s^2]",
		"         [y: " + Tools.nf_dist(ship.getAcceleration().y) + "/s^2]",
		"         [z: " + Tools.nf_dist(ship.getAcceleration().z) + "/s^2]"
		};
		GUIUtils.drawBox(renderG, title, boxtext, textPosX, textPosY, 450, 300);

		y += 320;
		
		title = "NAVIGATION";
		boxtext = new String[] {
			"Direction:",
			"     [x: "+Tools.nf(ship.getDirection().x)+"]",
			"     [y: "+Tools.nf(ship.getDirection().y)+"]",
			"     [z: "+Tools.nf(ship.getDirection().z)+"]",
			"Destination ["+ship.getDestinationName()+"]",
			"     [x: "+Tools.nf_dist(ship.getDestination().x)+"]",
			"     [y: "+Tools.nf_dist(ship.getDestination().y)+"]",
			"     [z: "+Tools.nf_dist(ship.getDestination().z)+"]",
			"Distance:",
			"     ["+Tools.nf_dist(ship.getPosition().distance(ship.getDestination()))+"]"
		};
		GUIUtils.drawBox(renderG, title, boxtext, x, y, 450, 250);
		
		renderParts(renderG);
	}

}
