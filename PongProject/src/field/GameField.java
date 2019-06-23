package field;

import java.util.ArrayList;
import java.util.List;

import gameObjects.BallObject;
import gameObjects.MovingObject;
import gameObjects.PlayerObject;
import gameObjects.PlayerObject.PLAYER_INDEX;

public class GameField {
	
	static final int WIDTH = 800;
	static final int HEIGHT = 600;
	static final int PLAYER_X_OFFSET = 100;
	
	PlayerObject firstPlayer;
	PlayerObject secondPlayer;
	BallObject ball;
	List<MovingObject> movingObjects;
	
	public GameField()
	{
		firstPlayer = new PlayerObject(PLAYER_INDEX.FIRST_PLAYER);
		firstPlayer.setPosition(PLAYER_X_OFFSET, (HEIGHT / 2) - (firstPlayer.getHeight() / 2));
		secondPlayer = new PlayerObject(PLAYER_INDEX.SECOND_PLAYER);
		secondPlayer.setPosition(WIDTH - PLAYER_X_OFFSET - secondPlayer.getWidth(), (HEIGHT / 2) - (secondPlayer.getHeight() / 2));
		ball = new BallObject();
		ball.setPosition((WIDTH / 2) - (ball.getWidth() / 2), (HEIGHT / 2) - (ball.getWidth() / 2));
		ball.SetToDegree(270);
		
		movingObjects = new ArrayList<MovingObject>();
		movingObjects.add(firstPlayer);
		movingObjects.add(secondPlayer);
		movingObjects.add(ball);
	}
	
	//Getters
	public int getWidth() { return WIDTH; }
	public int getHeight() { return HEIGHT; }
	
	public PlayerObject getFirstPlayer() { return firstPlayer; }
	public PlayerObject getSecondPlayer() { return secondPlayer; }
	public BallObject getBall() { return ball; }
	public List<MovingObject> getAllObjects() { return movingObjects; }

}