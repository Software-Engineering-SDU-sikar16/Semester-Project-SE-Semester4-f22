package dk.sdu.mmmi.cbse.common.data.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.Bullets.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Map.Tile;
import dk.sdu.mmmi.cbse.common.data.Resources;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.components.AnimatedSpritePart;
import dk.sdu.mmmi.cbse.common.data.components.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.components.HealthPart;
import dk.sdu.mmmi.cbse.common.data.components.TileIndexPart;
import org.w3c.dom.css.Rect;

import java.util.Random;


public class Tower extends Entity
{
	public static Random random = new Random();
	public boolean IsShooting = false;
	public Vector2 EnemyPosition;
	public float TurretShootSpeedInSeconds = 1.5f; // lower numbers are faster
	float timeSinceLastBullet = 0;
	public static int TurretPriceInCoins = 500;
	private Entity TargetEnemy;
	
	ColliderPart colliderPart;
	
	Circle circle;
	Rectangle rect;
	Rectangle hitRect;

	
	public Tower(int x, int y)
	{
		super(x, y, 50, 50);
		
		Texture turret1 = Resources.LoadTexture("../assets/turrets/4shot.png");
		
		int width = turret1.getWidth(); // width of the turret
		int height = turret1.getHeight(); // height of the turret
		
		getTransform().setSize(width, height); // set the size of the turret
		
		float radius = 100;
		circle = new Circle(getTransform().getCenterX(), getTransform().getCenterY(), radius); // create a circle for checking whether the enemy is getting close to the turret
		rect = new Rectangle(x, y, width, height);
		
		float radiusTimesTwoCircumfrence = 2 * radius;
		float radiusSquared = radiusTimesTwoCircumfrence * radiusTimesTwoCircumfrence;
		float rectCircleSize = (float) Math.sqrt(radiusSquared);
		hitRect = new Rectangle(getTransform().getCenterX() - rectCircleSize / 2, getTransform().getCenterY() - rectCircleSize / 2, rectCircleSize, rectCircleSize);
		
		AnimatedSpritePart animatedSprite = new AnimatedSpritePart(turret1); // create a new animated sprite part
		add(animatedSprite);
		
		colliderPart = new ColliderPart(); // create a new collider part
		colliderPart.Add("circle", circle);
		colliderPart.Add("rect", rect);
		colliderPart.Add("hitRect", hitRect);
	
		add(colliderPart);
	}
	
	@Override
	public void OnCreate(GameData gameData, World world)
	{
	
	}
	
	private void SpawnBullet(GameData gameData)
	{
		if (IsShooting)
		{
			Bullet bullet = gameData.BulletPool.obtain();
			Vector2 bulletPos = new Vector2(getTransform().getX() + getTransform().getWidth() / 4, getTransform().getY() + getTransform().getHeight() / 2 - 2);
			
			System.out.println("Shooting");
			bullet.fireBullet(bulletPos, EnemyPosition);
			gameData.ActiveBullets.add(bullet);
		}
	}
	
	@Override
	public void OnRender(GameData gameData, World world)
	{
		Vector2 mousePosition = gameData.mouseOperator.GetMouseWorldPosition(gameData);
		if (rect.contains(mousePosition))
		{
			gameData.GlobalShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			gameData.GlobalShapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1);
			if (IsShooting)
			{
				gameData.GlobalShapeRenderer.setColor(0.0f, 1.0f, 0.0f, 1);
			}
			gameData.GlobalShapeRenderer.circle(circle.x, circle.y, circle.radius);
			gameData.GlobalShapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1);
			gameData.GlobalShapeRenderer.end();
		}
		/*
		Texture tex = getTexture();
		if (tex != null)
		{
			gameData.GlobalSpriteBatch.begin();
			gameData.GlobalSpriteBatch.draw(tex, getTransform().getX(), getTransform().getY(), getTransform().getWidth(), getTransform().getHeight());
			gameData.GlobalSpriteBatch.end();
		}
		*/
	}
	
	@Override
	public void OnUpdate(GameData gameData, World world)
	{
		if (TargetEnemy == null)
		{
			return;
		}
		HealthPart healthPart = TargetEnemy.getPart(HealthPart.class);
		if (healthPart == null || healthPart != null && healthPart.GetHealth().IsDead())
		{
			return;
		}
		
		
		if (IsShooting)
		{
			timeSinceLastBullet += Gdx.graphics.getDeltaTime();
			if (timeSinceLastBullet > TurretShootSpeedInSeconds + (random.nextInt(500) * 0.001f))
			{
				System.out.println("Shooting");
				SpawnBullet(gameData);
				timeSinceLastBullet = 0;
				TargetEnemy = null;
			}
			else
			{
			}
		}
	}
	
	@Override
	public void OnDispose(GameData gameData, World world)
	{
	
	}
	
	
	public void EnemyIsClose(GameData gameData, Entity enemy)
	{
		this.TargetEnemy = enemy;
		if (enemy == null)
		{
			IsShooting = false;
		}
		else
		{
			IsShooting = true;
			
			
			TileIndexPart tileIndexPart = TargetEnemy.getPart(TileIndexPart.class);
			if (tileIndexPart == null)
			{
				return;
			}
			
			// be smart and look ahead one tile and shoot at the one tile ahead of the enemy!
			int tileIndex = MathUtils.clamp(tileIndexPart.TileIndex + 1, 0, gameData.GameMapPath.getCount() - 1);
			Tile tile = gameData.GameMapPath.get(tileIndex);
			// can do smarter ai, but this is fine for now.
			
			EnemyPosition = new Vector2(tile.x, tile.y);
		}
	}
	
	@Override
	public void OnCollision(GameData gameData, World world, Entity other)
	{
		EnemyIsClose(gameData, other);
	}
}
