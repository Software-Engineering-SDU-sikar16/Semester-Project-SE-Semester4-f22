package GamePlay;

import Entities.AnimatedSprite;
import helper.Constants;

public class EnemyEntity extends AnimatedSprite
{
	private boolean DidReachEndTile = false;
	public EnemyEntity(float x, float y, int width, int height)
	{
		super(x, y, width, height);
	}
	
	public  void SetDidReachEndTile()
	{
		DidReachEndTile = true;
		setPosition(Constants.EnemyManager.EnemiesSpawnPosition);
	}
}
