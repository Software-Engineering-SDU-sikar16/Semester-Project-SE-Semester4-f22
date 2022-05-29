package dk.sdu.mmmi.cbse.common.data.GamePlay;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.*;

import java.util.Random;

public class EnemyManager
{
	public static Array<Entity> Enemies = new Array<Entity>();
	
	public Array<Entity> enemiesOnScreen = new Array<Entity>();
	
	
	public Vector2 EnemiesSpawnPosition;
	
	public EnemyManager(GameData gameData)
	{
		enemiesOnScreen = new Array<>();
		EnemiesSpawnPosition = new Vector2(gameData.TileMapHelper.Start.x, gameData.TileMapHelper.Start.y); //set the start position to the tile position.
	}
	
	
	// todo implement wave manager and spawn enemies there.
	public void SpawnEnemy(World world, EnemyType Type)
	{
		Enemy enemy = new Enemy((int)EnemiesSpawnPosition.x, (int)EnemiesSpawnPosition.y, 24, 24, Type);
		
		enemy.setPosition(EnemiesSpawnPosition);
		enemy.SetEnabled(true);
		enemiesOnScreen.add(enemy);
	}
	
	
}