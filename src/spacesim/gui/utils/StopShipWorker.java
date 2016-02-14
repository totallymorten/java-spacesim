package spacesim.gui.utils;

import math.Vector3d;
import spacesim.SpaceSimPanel;
import spacesim.entities.vesselparts.Engine;
import spacesim.entities.vesselparts.EngineState;
import spacesim.entities.vessels.Ship;

public class StopShipWorker extends Process
{
	private Ship ship;
	private Engine engine;

	private Vector3d prevSpeed = null;
	
	public StopShipWorker(Ship ship)
	{
		this.ship = ship;
		this.engine = ship.getEngine();
		this.prevSpeed = ship.getSpeed();
		
		ship.setDirection(ship.getSpeed().mult(-1).unit());
		
	}
	
	public void work(long ms)
	{
		double force = engine.calcSettingForceOutput();
		Vector3d acceleration = ship.getDirection().mult(force / ship.getMass());
		Vector3d v_acc = acceleration.mult(SpaceSimPanel.period * 1e-3);
		
		// calculate no of updates until speed <= 0
		double updatesToZeroSpeed = ship.getSpeed().length() / v_acc.length();
		

		// if updatesToZero is > 2 then burn engine else set idle
		if (updatesToZeroSpeed > 2)
			engine.setState(EngineState.BURN);
		else
			engine.setState(EngineState.IDLE);
		
		// if updatesToZero < 10 then reduce engine setting
		if (updatesToZeroSpeed < 10)
			engine.setSetting(engine.getLevel() / 2);
		
		// if updatesToZero > 30 then increase engine setting
		else if (updatesToZeroSpeed > 30)
			engine.setSetting(engine.getLevel() * 2);
		
		System.out.println("speed ["+ship.getSpeed().length()+"]");
		System.out.println("v_acc ["+v_acc.length()+"]");
		System.out.println("engine setting ["+engine.getSetting()+"]");
		System.out.println("engine level ["+engine.getLevel()+"]");
		System.out.println("*****************************************");
		
		if (ship.getSpeed().length() == 0)
		{
			engine.setState(EngineState.IDLE);
			SpaceSimPanel.addRemovalRequest(this);
			ship.setDirection(ship.getDirection().mult(-1).unit()); // reset to original course
			engine.setSetting(0);
			return;				
		}
		
		if (ship.getSpeed().length() > prevSpeed.length())
		{
			System.out.println("stopping, v > v_prev ["+ship.getSpeed().length()+"] ["+prevSpeed.length()+"]");
			
			engine.setState(EngineState.IDLE);
			SpaceSimPanel.addRemovalRequest(this);
			return;								
		}
		
		prevSpeed = ship.getSpeed();
	}
}
