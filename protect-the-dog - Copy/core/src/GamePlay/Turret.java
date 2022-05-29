package GamePlay;

import Entities.AnimatedSprite;
import Map.Tile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import helper.Constants;
import helper.MouseOperator;
import helper.Resources;

import java.util.HashMap;
import java.util.Random;


public class Turret extends AnimatedSprite
{
	
	public static Array<Turret> Turrets = new Array<Turret>();
	public static Array<AnimatedSprite> bullets = new Array<AnimatedSprite>(100); // global list of bullets //make a bullet service?
	public static HashMap<Vector2, Turret> turretPositions = new HashMap<Vector2, Turret>();
	public static Random random = new Random();
	public boolean IsShooting = false;
	public Vector2 EnemyPosition;
	public float TurretShootSpeedInSeconds = 1.5f; // lower numbers are faster
	public Rectangle hitRect;
	float timeSinceLastBullet = 0;
	public static int TurretPriceInCoins = 500;
	public Circle circle; // this circle is for checking whether the enemy is getting close to the turret.
	public Rectangle rect; // this rectangle is for checking whether the mouse is hovering over the turret
	private EnemyEntity TargetEnemy;
	
	public Turret(int x, int y)
	{
		super(x, y, 50, 50);
		
		Texture turret1 = Resources.LoadTexture("turrets/4shot.png");
		
		int width = turret1.getWidth();
		int height = turret1.getHeight();
		
		setSize(width, height);
		
		float radius = 100;
		circle = new Circle(getEntityX(), getEntityY(), radius);
		rect = new Rectangle(x, y, width, height);
		
		float radiusTimesTwoCircumfrence = 2 * radius;
		float radiusSquared = radiusTimesTwoCircumfrence * radiusTimesTwoCircumfrence;
		float rectCircleSize = (float) Math.sqrt(radiusSquared);
		hitRect = new Rectangle(getEntityX() - rectCircleSize / 2, getEntityY() - rectCircleSize / 2, rectCircleSize, rectCircleSize);
		
		
		setTexture(turret1);
		Turrets.add(this);
		turretPositions.put(new Vector2(x, y), this);
	}
	
	private void SpawnBullet()
	{
		if (IsShooting)
		{
			Bullet bullet = Constants.BulletPool.obtain();
			Vector2 bulletPos = new Vector2(getX() + getWidth() / 4, getY() + getHeight() / 2 - 2);
			
			bullet.fireBullet(bulletPos, EnemyPosition);
			Constants.ActiveBullets.add(bullet);
		}
	}
	
	@Override
	public void OnUpdate(float DeltaTime)
	{
		super.OnUpdate(DeltaTime);
		
		if (TargetEnemy == null || TargetEnemy.IsDead())
		{
			return;
		}
		
		if (IsShooting)
		{
			timeSinceLastBullet += Gdx.graphics.getDeltaTime();
			if (timeSinceLastBullet > TurretShootSpeedInSeconds + (random.nextInt(500) * 0.001f))
			{
				SpawnBullet();
				timeSinceLastBullet = 0;
			}
			else {}
		}
	}
	
	
	@Override
	public void OnRender()
	{
		super.OnRender();
		
		Vector2 mousePosition = MouseOperator.GetMouseWorldPosition();
		if (rect.contains(mousePosition))
		{
			Constants.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			Constants.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1);
			if (IsShooting)
			{
				Constants.shapeRenderer.setColor(0.0f, 1.0f, 0.0f, 1);
			}
			Constants.shapeRenderer.circle(circle.x, circle.y, circle.radius);
			Constants.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1);
			Constants.shapeRenderer.end();
		}
		
	}
	
	public static void TryBuildTurret()
	{
		
		Vector2 TilePos = MouseOperator.GetTilePositionUnderMousePosition();
		
		Constants.MouseTileSelector.setPosition(TilePos.x, TilePos.y);
		Constants.MouseTileSelector.setSize(Constants.TileMapHelper.TilePixelWidth, Constants.TileMapHelper.TilePixelHeight);
		
		
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !Constants.IsBuildingTurret)
		{
			if (Constants.TileMapHelper.IsTileAtPositionAValidBuildableTile(TilePos))
			{
				if (Constants.Coins - TurretPriceInCoins < 0) // if the user has money to build this turret
				{
					return;
				}
				Constants.Coins -= TurretPriceInCoins;
				
				if (!turretPositions.containsKey(TilePos))
				{
					new Turret((int) TilePos.x, (int) TilePos.y);
				}
			}
		}
	}
	
	public void EnemyIsClose(EnemyEntity enemy)
	{
		this.TargetEnemy = enemy;
		if (enemy == null)
		{
			IsShooting = false;
		}
		else
		{
			IsShooting = true;
			
			// be smart and look ahead one tile and shoot at the one tile ahead of the enemy!
			int tileIndex = MathUtils.clamp(enemy.TileIndex + 1, 0, Constants.GameMapPath.getCount() - 1);
			Tile tile = Constants.GameMapPath.get(tileIndex);
			// can do smarter ai, but this is fine for now.
			
			EnemyPosition = new Vector2(tile.x, tile.y);
		}
	}
}
