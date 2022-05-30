package GamePlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import helper.Constants;
import helper.Resources;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class EnemyManager
{
	public static Array<EnemyEntity> Enemies = new Array<EnemyEntity>();
	
	public Array<EnemyEntity> enemiesOnScreen = new Array<EnemyEntity>();
	
	public Random EnemyRandomNumberGenerator;
	
	public Vector2 EnemiesSpawnPosition;
	
	public EnemyManager()
	{
		enemiesOnScreen = new Array<>();
		EnemyRandomNumberGenerator = new Random();
		EnemiesSpawnPosition = new Vector2(Constants.TileMapHelper.Start.x, Constants.TileMapHelper.Start.y); //set the start position to the tile position.
		
		
		// todo move enemies somewhere outside of the window to make sure we don't load them in the middle of the screen.
		LoadAllEnemies();
	
	}
	
	
	// todo implement wave manager and spawn enemies there.
	public void SpawnEnemy(EnemyEntity.EnemyType Type)
	{
//		Path currentRelativePath = Paths.get("");
//		String s = currentRelativePath.toAbsolutePath().toString();
//		System.out.println("Current absolute path is: " + s);

//		Gdx.app.log("AssetPath", Gdx.files.internal("entities/enemies/sheet_0.png").file().getAbsolutePath());

		int randomSheetIndex = EnemyRandomNumberGenerator.nextInt(23 - 1);
		
		EnemyEntity enemy = new EnemyEntity(EnemiesSpawnPosition.x, EnemiesSpawnPosition.y, 24, 24, Type);
		enemy.AddAnimation("default", Resources.LoadTexture("entities/enemies/sheet_" + randomSheetIndex + ".png"), 1, 4, 12, Animation.PlayMode.NORMAL);

		// enemies are already loaded and created, all we have to do is to move the enemies in the correct place.
		//EnemyEntity enemy = Enemies.get(EnemyRandomNumberGenerator.nextInt(23 - 1));
		enemy.setPosition(EnemiesSpawnPosition);
		enemy.SetEnabled(true);
		enemiesOnScreen.add(enemy);
	}
	
	public void LoadAllEnemies()
	{
	/*	for (int i = 0; i < 23; i++)
		{
			EnemyEntity enemy = new EnemyEntity(50 + (i * 50), 158 + (i), 24, 24, EnemyEntity.EnemyType.normal);
			enemy.AddAnimation("default", Resources.LoadTexture("entities/enemies/sheet_" + i + ".png "), 1, 4, 12, Animation.PlayMode.NORMAL);
			enemy.SetEnabled(false);
			Enemies.add(enemy);
		}*/
	}
	
	
	public void SteerEnemies()
	{
		for (EnemyEntity enemy : enemiesOnScreen)
		{
		/*	Tile tile = Constants.TileMapHelper.GetNextEnemyPathTileFromCurrentTileAt(enemy.getX(), enemy.getY());
			
			if (tile == null)
			{
				Constants.Health.SubstractHealth();
				enemy.SetDidReachEndTile();
			} else {
				// move to next tile.
				enemy.setPosition(tile.x, tile.y);
			}
			*/
		}
	}
}
