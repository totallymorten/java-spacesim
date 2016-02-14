package spacesim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JPanel;

import math.Vector3d;

import spacesim.entities.Updateable;
import spacesim.entities.vesselparts.Engine;
import spacesim.entities.vesselparts.EngineState;
import spacesim.entities.vesselparts.PowerBuffer;
import spacesim.entities.vesselparts.Reactor;
import spacesim.entities.vessels.Ship;
import spacesim.gui.screens.NavScreen;
import spacesim.gui.screens.Screen;
import spacesim.gui.screens.ScreenMenuPart;
import spacesim.gui.screens.SystemScreen;
import spacesim.gui.utils.EngineBurnTimer;
import spacesim.gui.utils.StopShipWorker;

public class SpaceSimPanel extends JPanel implements Runnable
{
	private static final long serialVersionUID = 1L;

	private SpaceSim wc;
	public static long period;
	
	private volatile boolean running;

	public static int WIDTH = 1024;
	public static int HEIGHT = 768;
	
	Thread animator;
	
	
	private Ship playerShip;
	
	public SpaceSimPanel(SpaceSim wc, long period)
	{
		this.wc = wc;
		SpaceSimPanel.period = period;
		
		init();
		
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		
		setFocusable(true);
		readyForTermination();
	}

	private void init()
	{
		SpaceSimPanel.ship = new Ship(1000, new Vector3d(0,0,0));
		this.playerShip = SpaceSimPanel.ship;

		playerShip.setReactor(new Reactor(10,1000,1000,0.1,0.005));
		playerShip.setPowerBuffer(new PowerBuffer(1));
		playerShip.setEngine(new Engine(0.0003,0.01));
		
		SpaceSimPanel.addUpdateable(playerShip);
		
		currentScreen = new SystemScreen();
	}
	
	
	@Override
	public void addNotify()
	{
		log("addNotify()");
		requestFocus();
		super.addNotify();
		startGame();
	}



	public void startGame()
	{
		animator = new Thread(this);
		animator.start();
	}

	String text = "";

	public void readyForTermination()
	{
		addKeyListener(
			new KeyAdapter()
			{

				@Override
				public void keyPressed(KeyEvent e)
				{
					int keycode = e.getKeyCode();
					
					if (keycode == KeyEvent.VK_ESCAPE)
						running = false;
					
					
					
					else if (keycode == KeyEvent.VK_BACK_SPACE)
					{
						if (text.length() > 0)
							text = text.substring(0, text.length() -1); // delete last letter
					}
					
					else if (keycode == KeyEvent.VK_ENTER)
					{
						parseInput(text);
						text = "";
					}
					
					else if (keycode == KeyEvent.VK_F1)
					{
						currentScreen = new NavScreen();
					}
					
					else if (keycode == KeyEvent.VK_F2)
					{
						currentScreen = new SystemScreen();
					}
					
					else if (!(keycode == KeyEvent.VK_SHIFT))
					{
						text += ((char)(keycode + e.getModifiers()));
					}
				}
				
			}
		);
	}
	
	
	public void parseInput(String input)
	{
		StringTokenizer tok = new StringTokenizer(input, " ");
		
		String token = tok.nextToken();
		
		System.out.println("token is: ["+token+"]");
		
		if (token.equals("REACTOR"))
		{
			token = tok.nextToken();
			
			System.out.println("token is: ["+token+"]");
			if (token.equals("SET"))
			{
				token = tok.nextToken();
				
				System.out.println("token is: ["+token+"]");

				double newLevel = Double.parseDouble(token);
				SpaceSimPanel.ship.getReactor().setSetting(newLevel);
				
				System.out.println("Reactor now set to level ["+newLevel+"] ["+playerShip.getReactor().getLevel()+"]");
			}
		}
		else if (token.equals("ENGINE"))
		{
			token = tok.nextToken();
			
			System.out.println("token is: ["+token+"]");
			if (token.equals("SET"))
			{
				token = tok.nextToken();
				
				System.out.println("token is: ["+token+"]");

				double newLevel = Double.parseDouble(token);
				SpaceSimPanel.ship.getEngine().setSetting(newLevel);
				
				System.out.println("Engine now set to level ["+newLevel+"] ["+playerShip.getEngine().getLevel()+"]");
			}
			else if (token.equals("IDLE"))
			{
				playerShip.getEngine().setState(EngineState.IDLE);
			}
			else if (token.equals("BURN"))
			{
				playerShip.getEngine().setState(EngineState.BURN);
				handleBurnTime(tok);
			}
			else if (token.equals("RETRO"))
			{
				playerShip.getEngine().setState(EngineState.RETRO_BURN);
				handleBurnTime(tok);
			}
		}
		else if (token.equals("NAV"))
		{
			token = tok.nextToken();
			
			if (token.equals("DIRECTION"))
			{
				double x = Double.parseDouble(tok.nextToken());
				double y = Double.parseDouble(tok.nextToken());
				double z = Double.parseDouble(tok.nextToken());
				
				ship.setDirection(new Vector3d(x,y,z).unit());
			}
			else if (token.equals("DESTINATION"))
			{
				double x = Double.parseDouble(tok.nextToken());
				double y = Double.parseDouble(tok.nextToken());
				double z = Double.parseDouble(tok.nextToken());
				
				ship.setDestination(new Vector3d(x,y,z));
			}
			else if (token.equals("STOP"))
			{
				SpaceSimPanel.addUpdateable(new StopShipWorker(SpaceSimPanel.ship));
			}
		}
	}
	
	private static ArrayList<Updateable> addRequests = new ArrayList<Updateable>();
	
	public static synchronized void addUpdateable(Updateable u)
	{
		SpaceSimPanel.addRequests.add(u);
	}
	
	private void handleBurnTime(StringTokenizer tok)
	{
		if (!tok.hasMoreElements())
			return;
		
		String token = tok.nextToken();
		
		int seconds = Integer.parseInt(token);
		
		EngineBurnTimer bt = new EngineBurnTimer(seconds * 1000, EngineState.IDLE, SpaceSimPanel.ship.getEngine());
		SpaceSimPanel.addUpdateable(bt);
	}
	
	public void run()
	{
		log("run() called");
		
		running = true;
		long time;
		while (running)
		{
			time = time(); // getting time before runnning game loop
			gameUpdate();
			gameRender();
			paintScreen();
			time = time(time); // time = the time spend updating game
			
			try
			{
				//log("sleeping for " + period + " ms");
				Thread.sleep(getSleepTime(period,time));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		log("exiting run()");
		System.exit(0);
	}

	private long getSleepTime(long period, long time)
	{
		long sleeptime = 0;
		
		if (time <= period)
			sleeptime = period - time;
		
		//System.out.println("sleepting for ["+sleeptime+"] ms. time is ["+time+"]");
		
		return sleeptime;
	}
	
	private long time()
	{
		return time(0);
	}
	
	private long time(long prevTime)
	{
		return System.nanoTime() / (long)1e6 - prevTime;
	}
	
	private void paintScreen()
	{
		Graphics g;
		
		try
		{
			g = this.getGraphics();
			g.drawImage(renderImg,0,0,null);
			Toolkit.getDefaultToolkit().sync();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	private Graphics renderG;
	private Image renderImg = null;

	
	Screen currentScreen;
	
	private void gameRender()
	{
		if (renderImg == null)
		{
			renderImg = createImage(WIDTH, HEIGHT);
			return;
		}
		else
			renderG = renderImg.getGraphics();
		
		currentScreen.render(renderG);
		
		renderG.drawString("> " + text + "_", textPosX, 700);
	}

	private int textPosX = 10;
	//private int textPosY = 100;
	
	public static Ship ship;
	
	private static ArrayList<Updateable> updateables = new ArrayList<Updateable>();
	
	private static ArrayList<Updateable> removalRequests = new ArrayList<Updateable>();
	
	private static synchronized void checkRemovalRequests()
	{
		for (Updateable u : removalRequests)
		{
			SpaceSimPanel.updateables.remove(u);
		}
		
		SpaceSimPanel.removalRequests.clear();
	}
	
	private static synchronized void checkAddRequests()
	{
		for (Updateable u : addRequests)
		{
			SpaceSimPanel.updateables.add(u);
		}
		
		SpaceSimPanel.addRequests.clear();
	}

	public static synchronized void addRemovalRequest(Updateable u)
	{
		SpaceSimPanel.removalRequests.add(u);
	}
	
	private void gameUpdate()
	{
		SpaceSimPanel.checkRemovalRequests();
		SpaceSimPanel.checkAddRequests();
		
		for (Updateable u : SpaceSimPanel.updateables)
		{
			u.update(period);
		}
	}
	
	public void log(String s)
	{
		System.out.println(this.getClass().getName() + ": " + s);
	}
	
	
}
