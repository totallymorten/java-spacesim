package spacesim.gui.utils;

import spacesim.entities.vesselparts.Engine;
import spacesim.entities.vesselparts.EngineState;

public class EngineBurnTimer extends Timer
{
	private EngineState es;
	private Engine engine;
	
	public EngineBurnTimer(double time, EngineState es, Engine engine)
	{
		super(time);
		this.es = es;
		this.engine = engine;
	}
	
	
	@Override
	public void timerDoneAction()
	{
		engine.setState(es);
	}

}
