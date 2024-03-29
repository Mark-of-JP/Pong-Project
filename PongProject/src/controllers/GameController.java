package controllers;

import java.awt.event.WindowEvent;

import field.FieldHolder;
import ui.GameWindow;

public class GameController{
	
	//Enum used to switch game screen
	public enum GAME_SCREEN
	{
		MENU, GAME, WINNING_SCREEN
	}
	
	//GameController singleton
	private static GameController INSTANCE;
	
	//Game objects and window
	private GameWindow gameWindow;
	private GAME_SCREEN currentGameScreen;
	private FieldHolder field;
	
	//Variables used for running the game
	boolean isRunning = true;
	
	//Controllers
	GraphicsController graphics;
	InputController input;
	LogicController logic;
	
	//Thread and loop used for logic
	private Thread gameThread;
	
	public GameController()
	{
		//Ensures there is only one instance of game controller
		if(INSTANCE != null)
			INSTANCE.gameWindow.dispatchEvent(new WindowEvent(INSTANCE.gameWindow, WindowEvent.WINDOW_CLOSING));
		
		INSTANCE = this;
		//Sets the default screen
		currentGameScreen = GAME_SCREEN.MENU;
	}
	
	public void playPong()
	{	
		gameWindow = new GameWindow();
		
		field = new FieldHolder(gameWindow);
		
		graphics = new GraphicsController();
		input = new InputController(gameWindow);
		logic = new LogicController();
		
		//Starts the game loop
		startThread();
	}
	
	/**Resets the field values and switches screens*/
	public void setGameScreen(GAME_SCREEN _screen)
	{
		switch(_screen)
		{
		case MENU:
			field.resetMenuField(gameWindow);
			break;
			
		case GAME:
			field.resetGameField();
			break;
			
		default:
			break;
		}
		
		currentGameScreen = _screen;
	}
	
	//Runs the game loop of the game
	private void doGameLoop()
	{
		long lastTime = System.currentTimeMillis();
		//The last time used for the time delta for logi
		long timeDeltaLastTime = System.currentTimeMillis();
		int targetFPS = 30;
		int desiredDelay = 1000 / targetFPS;
		
		while(isRunning)
		{	
			//Display Graphics
			graphics.render(currentGameScreen, gameWindow, field);
			//Runs the logic of the current screen
			double timeDelta = (System.currentTimeMillis() - timeDeltaLastTime) / 1000.0;
			logic.doLogic(currentGameScreen, gameWindow, field, input.getInputs(), timeDelta);
			timeDeltaLastTime = System.currentTimeMillis();
			//Resets inputs that need to be reset
			input.resetInputs();
			
			//Delays the game by the desired time to ensure the desired FPS
			long waitTime = desiredDelay - (System.currentTimeMillis() - lastTime);
			try { if(waitTime > 0) Thread.sleep(waitTime); } catch(Exception e) { e.printStackTrace(); }
			lastTime = System.currentTimeMillis();
		}
	}
	
	//Instantiates the thread and runs the game loop within the thread
	private void startThread()
	{
		gameThread = new Thread()
		{
			@Override
			public void run()
			{
				doGameLoop();
			}
		};
		
		gameThread.start();
	}
	
	public static GameController getInstance() { return INSTANCE; }
	
	public GameWindow getGameWindow() { return gameWindow; }
	public FieldHolder getField() { return field; }
	
	public void stopGame() { isRunning = false; }
}
