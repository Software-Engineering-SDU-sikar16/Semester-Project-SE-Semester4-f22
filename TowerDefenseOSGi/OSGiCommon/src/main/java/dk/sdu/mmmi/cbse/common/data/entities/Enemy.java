package dk.sdu.mmmi.cbse.common.data.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.cbse.common.data.*;
import dk.sdu.mmmi.cbse.common.data.Map.Tile;
import dk.sdu.mmmi.cbse.common.data.components.AnimatedSpritePart;
import dk.sdu.mmmi.cbse.common.data.components.HealthPart;
import dk.sdu.mmmi.cbse.common.data.components.TileIndexPart;
import dk.sdu.mmmi.cbse.common.data.helpers.Util;


public class Enemy extends Entity
{
	TileIndexPart tileIndexPart;
	
	private static float enemySpeed = 40.0f; // speed of enemy
	
	private EnemyType Type = EnemyType.normal; // default
	
	private boolean DidReachEndTile = false; // Used to check if the enemy reached the end tile.
	
	
	public boolean IsDead()
	{
		HealthPart healthPart = getPart(HealthPart.class);
		if (healthPart == null)
		{
			return true;
		}
		return healthPart.IsDead();
	}
	
	public Enemy(int x, int y, int width, int height, EnemyType Type)
	{
		super(x, y, width, height);
		this.Type = Type;
		
		int randomSheetIndex = MathUtils.random(23 - 1); // Get a random sheet index.
		
		AnimatedSpritePart spritePart = new AnimatedSpritePart(this, "default", Resources.LoadTexture("../assets/entities/enemies/sheet_" + randomSheetIndex + ".png"), 1, 4, 12, Animation.PlayMode.NORMAL);
		add(spritePart);
		
		HealthPart healthPart;
		if (Type == EnemyType.normal) // if the enemy is a normal enemy
		{
			healthPart = new HealthPart(this, 0, 18, width / 2, height / 2, 1); // create a health part
		}
		else if (Type == EnemyType.boss) // if the enemy is a boss
		{
			healthPart = new HealthPart(this, 5, 18, width / 2, height / 2, 2); // boss has 2 health
		}
		else if (Type == EnemyType.superboss) // if the enemy is a superboss
		{
			healthPart = new HealthPart(this, 5, 18, width / 2, height / 2, 3); // set health to 3
		}
		else if (Type == EnemyType.elite) // if the enemy is an elite
		{
			healthPart = new HealthPart(this, 5, 18, width / 2, height / 2, 4); // set health
		}
		else if (Type == EnemyType.kingkong) // if the enemy is a kingkong
		{
			healthPart = new HealthPart(this, 5, 18, width / 2, height / 2, 5); // set health
		}
		else
		{
			healthPart = new HealthPart(this, 5, 18, width / 2, height / 2, 6); // set health
		}
		add(healthPart);
		
		tileIndexPart = new TileIndexPart();
		add(tileIndexPart); // add tile index part
	}
	
	
	@Override
	public void OnCreate(GameData gameData, World world)
	{
	
	}
	
	
	@Override
	public void OnRender(GameData gameData, World world)
	{
		int TileIndex = MathUtils.clamp(tileIndexPart.TileIndex, 0, gameData.GameMapPath.getCount() - 1); // Clamp the tile index to the map size.
		Tile tile = gameData.GameMapPath.get(TileIndex); // Get the tile at the current index.
		if (tile == null) // If the tile is null, return.
		{
		}
		else
		{
			if (gameData.DEBUG)
			{
				gameData.GlobalShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
				gameData.GlobalShapeRenderer.rect(tile.x, tile.y, gameData.TileMapHelper.TilePixelWidth, gameData.TileMapHelper.TilePixelHeight);
				gameData.GlobalShapeRenderer.end();
			}
		}
		
		
	}
	
	@Override
	public void OnUpdate(GameData gameData, World world)
	{
		HealthPart healthPart = getPart(HealthPart.class);
		if (healthPart == null)
		{
			return;
		}
		if (healthPart.IsDead()) // if dead, dont do anything.
		{
			return;
		}
		
		if (gameData.TileMapHelper != null) // if the tilemap is loaded.
		{
			
			int TileIndex = MathUtils.clamp(tileIndexPart.TileIndex, 0, gameData.GameMapPath.getCount() - 1); // get the tile index
			Tile tile = gameData.GameMapPath.get(TileIndex); // get the tile
			
			if (tile == null) // reached unknown tile?
			{
				getTransform().setPosition(gameData.EnemiesSpawnPosition); // reset position
			}
			else if (tile == gameData.TileMapHelper.End) // reached end tile
			{
				gameData.UIHealth.SubstractHealth(); // substract life from UI
				SetDidReachEndTile(gameData, world); // set the enemy to dead.
			}
			else
			{
				
				Vector2 target = new Vector2(tile.x, tile.y); // tile.x + gameData.TileMapHelper.TilePixelWidth / 2, tile.y + gameData.TileMapHelper.TilePixelHeight / 2);
				double distance = Util.distance(getTransform().getPosition(), target); // get distance between enemy and target
				if (distance < 2f) // if enemy is close enough to target
				{
					tileIndexPart.TileIndex++; // advance to next tile
				}
				else
				{
					Vector2 origin = getTransform().getPosition();
					Vector2 direction = target.sub(origin);
					direction.nor();
					direction.scl(enemySpeed * Gdx.graphics.getDeltaTime());
					getTransform().setPosition(origin.add(direction)); // move enemy forward
				}
			}
		}
	}
	
	public void SetDidReachEndTile(GameData gameData, World world) // set the enemy to dead.
	{
		DidReachEndTile = true;
		getTransform().setPosition(gameData.EnemiesSpawnPosition); // reset position
		world.removeEntity(this); // remove entity from screen.
	}
	
	@Override
	public void OnDispose(GameData gameData, World world)
	{
	
	}
	
	@Override
	public void OnCollision(GameData gameData, World world, Entity other)
	{
		HealthPart healthPart = getPart(HealthPart.class);
		if (healthPart == null)
		{
			return;
		}
		
		healthPart.SubstractHealth(); // substract health
		if (healthPart.IsDead()) // if health is 0
		{
			
			if (Type == EnemyType.normal) // if the enemy is a normal enemy
			{
				gameData.Coins += 150; // add coins
			}
			else if (Type == EnemyType.boss) // if the enemy is a boss
			{
				gameData.Coins += 250; // add coins
			}
			else if (Type == EnemyType.superboss) // if the enemy is a superboss
			{
				gameData.Coins += 400; // add coins
			}
			else if (Type == EnemyType.elite) // if the enemy is an elite
			{
				gameData.Coins += 600; // add coins
			}
			else if (Type == EnemyType.kingkong) // if the enemy is a kingkong
			{
				gameData.Coins += 900; // add coins
			}
			//todo play coin sound.
			world.removeEntity(this); // remove entity
		}
	
	}
}
