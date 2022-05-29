package dk.sdu.mmmi.cbse.osgienemy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.EnemyType;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

import java.util.Random;

public class EnemyManager
{
	public static Array<Entity> Enemies = new Array<Entity>();
	
	public Array<Entity> enemiesOnScreen = new Array<Entity>();
	
	public Random EnemyRandomNumberGenerator;
	
	public Vector2 EnemiesSpawnPosition;
	
	public EnemyManager(GameData gameData)
	{
		enemiesOnScreen = new Array<>();
		EnemyRandomNumberGenerator = new Random();
		EnemiesSpawnPosition = new Vector2(gameData.TileMapHelper.Start.x, gameData.TileMapHelper.Start.y); //set the start position to the tile position.
		
		
	}
	
	
	// todo implement wave manager and spawn enemies there.
	public void SpawnEnemy(EnemyType Type)
	{
		int randomSheetIndex = EnemyRandomNumberGenerator.nextInt(23 - 1);
		
		Enemy enemy = new Enemy(EnemiesSpawnPosition.x, EnemiesSpawnPosition.y, 24, 24, Type);
		//	enemy.AddAnimation("default", Resources.LoadTexture("entities/enemies/sheet_" + randomSheetIndex + ".png"), 1, 4, 12, Animation.PlayMode.NORMAL);
		
		enemy.setPosition(EnemiesSpawnPosition);
		enemy.SetEnabled(true);
		enemiesOnScreen.add(enemy);
	}
	
	
}
