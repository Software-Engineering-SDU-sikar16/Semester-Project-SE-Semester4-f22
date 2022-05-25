package GamePlay;

import Entities.AnimatedSprite;
import Map.Tile;
import Overlays.Health;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import helper.Constants;
import helper.Util;

public class EnemyEntity extends AnimatedSprite
{
	private boolean DidReachEndTile = false;
	private float enemySpeed = 40.0f;
	private Health health;
	public enum EnemyType
	{
		normal, boss, superboss, elite, kingkong,
	}
	
	private EnemyType Type = EnemyType.normal;
	public int TileIndex = 0;

	public boolean IsDead() {
		return health.IsDead();
	}
	
	public EnemyEntity(float x, float y, int width, int height, EnemyType type)
	{
		super(x, y, width, height);
		this.Type = type;
		
		if (Type == EnemyType.normal)
		{
			health = new Health((int) x + 6, (int) y + height + 18, width / 2, height / 2, 1);
		}
		else if (Type == EnemyType.boss)
		{
			health = new Health((int) x + 4, (int) y + height + 18, width / 2, height / 2, 5, 2);
		}
		else if (Type == EnemyType.superboss)
		{
			health = new Health((int) x + 2, (int) y + height + 18, width / 2, height / 2, 5, 3);
		}
		else if (Type == EnemyType.elite)
		{
			health = new Health((int) x, (int) y + height + 18, width / 2, height / 2, 5, 4);
		}
		else if (Type == EnemyType.kingkong)
		{
			health = new Health((int) x - 3, (int) y + height + 18, width / 2, height / 2, 5, 5);
		}
	}
	
	@Override
	public void setPosition(Vector2 pos)
	{
		super.setPosition(pos);
		SetHealthPositionAndWidth(pos.x, pos.y, getWidth() / 2, getHeight() / 2);
	}
	
	@Override
	public void translate(float xAmount, float yAmount)
	{
		super.translate(xAmount, yAmount);
		SetHealthPositionAndWidth(getX() + xAmount, getY() + yAmount, getWidth() / 2, getHeight() / 2);
	}
	
	public void translate(Vector2 vector)
	{
		translate(vector.x, vector.y);
	}
	
	private void SetHealthPositionAndWidth(float x, float y, float width, float height)
	{
		if (Type == EnemyType.normal)
		{
			health.setPosition((int) x + 6, (int) y + height + 18);
		}
		else if (Type == EnemyType.boss)
		{
			health.setPosition((int) x + 4, (int) y + height + 18);
		}
		else if (Type == EnemyType.superboss)
		{
			health.setPosition((int) x + 2, (int) y + height + 18);
		}
		else if (Type == EnemyType.elite)
		{
			health.setPosition((int) x, (int) y + height + 18);
		}
		else if (Type == EnemyType.kingkong)
		{
			health.setPosition((int) x - 3, (int) y + height + 18);
		}
		health.setSize(width, height);
	}
	
	@Override
	public void OnRender()
	{
		if (!this.IsEnabled()) return;
		super.OnRender();
		
		TileIndex = MathUtils.clamp(TileIndex, 0, Constants.GameMapPath.getCount() - 1);
		Tile tile = Constants.GameMapPath.get(TileIndex);
		if (tile == null) {}
		else
		{
			Constants.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			Constants.shapeRenderer.rect(tile.x, tile.y, Constants.TileMapHelper.TilePixelWidth, Constants.TileMapHelper.TilePixelHeight);
			Constants.shapeRenderer.end();
		}
	}
	
	
	public void GotHit(Bullet bullet)
	{
		health.SubstractHealth();
		if (health.IsDead())
		{
			health.SetEnabled(false);
			setPosition(new Vector2(-50, -50));
			//play coin sound.
			// add coins
			// remove entity from screen.
			Constants.Coins += 50;
			this.SetEnabled(false);
		}
	}
	
	
	@Override
	public void OnUpdate(float DeltaTime)
	{
		if (!this.IsEnabled()) return;
		super.OnUpdate(DeltaTime);
		if (health.IsDead())
		{
			return;
		}
		
		if (Constants.TileMapHelper != null)
		{
			
			TileIndex = MathUtils.clamp(TileIndex, 0, Constants.GameMapPath.getCount() - 1);
			Tile tile = Constants.GameMapPath.get(TileIndex);
			
			if (tile == null)
			{
			
			}
			else if (tile == Constants.TileMapHelper.End)
			{
				Constants.Health.SubstractHealth();
				SetDidReachEndTile();
				this.SetEnabled(false);
			}
			else
			{
				
				Vector2 target = new Vector2(tile.x, tile.y);
				double distance = Util.distance(getPosition(), target);
				if (distance < 2f)
				{
					TileIndex++;
				}
				else
				{
					Vector2 origin = getPosition();
					Vector2 direction = target.sub(origin);
					direction.nor();
					direction.scl(enemySpeed * Gdx.graphics.getDeltaTime());
					setPosition(origin.add(direction));
				}
			}
		}
	}

	public void SetDidReachEndTile()
	{
		DidReachEndTile = true;
		setPosition(Constants.EnemyManager.EnemiesSpawnPosition);
	}
}
