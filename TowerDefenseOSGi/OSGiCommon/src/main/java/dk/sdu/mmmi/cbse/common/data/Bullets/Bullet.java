package dk.sdu.mmmi.cbse.common.data.Bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Resources;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.components.EntityPart;

public class Bullet implements Pool.Poolable
{
	public static Texture BulletTexture = Resources.LoadTexture("turrets/bullet_4.png");
	
	public Vector2 TargetDirection;
	public float dx = 0;
	public float dy = 0;
	
	Sprite sprite;
	
	private Rectangle rect;
	
	public Bullet()
	{
		sprite.setPosition(0, 0);
		sprite.setSize(BulletTexture.getWidth(), BulletTexture.getHeight());
		//setTexture(BulletTexture);
		//sprite.AddAnimation("default", BulletTexture, 1, 8, 5, Animation.PlayMode.NORMAL);
		rect = new Rectangle(0, 0, BulletTexture.getWidth(), BulletTexture.getHeight());
	}
	
	public void setPosition(float x, float y)
	{
		sprite.setPosition(x, y);
		rect.setPosition(x, y);
	}
	
	public Vector2 getPosition()
	{
		return new Vector2(sprite.getX(), sprite.getY());
	}
	
	
	@Override
	public void reset()
	{
		TargetDirection.set(0, 0);
		sprite.setTexture(null);
	}
	
	
	public void fireBullet(Vector2 origin, Vector2 enemyPosition)
	{
		sprite.setPosition(origin.x, origin.y);
		this.TargetDirection = enemyPosition;
		float angle = enemyPosition.sub(origin).angleDeg();
		
		//	float angle = (float)Math.atan(direction.x / direction.y);
		
		//	float angle = 180;
		double angleInRads = Math.toRadians(angle);
		dx = (float) Math.cos(angleInRads);
		dy = (float) Math.sin(angleInRads);
		
	}
	
	
	void OnCreate(GameData gameData, World world)
	{
	
	}
	
	public void OnRender(GameData gameData, World world)
	{
		if (sprite != null)
		{
			sprite.draw(gameData.GlobalSpriteBatch);
		}
		
		//super.OnRender(gameData, world);
		
			/*	Constants.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		Constants.shapeRenderer.setColor(Color.WHITE);
		Constants.shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		Constants.shapeRenderer.end();*/
		
	}
	
	
	public void OnUpdate(GameData gameData, World world)
	{
		sprite.translate(dx, dy);
		
		rect.setPosition(sprite.getX(), sprite.getY());
		
	/*
		Array<EnemyEntity> hitEntities = gameData.EnemyQuadTree.Query(rect);
		for (EnemyEntity entity : hitEntities)
		{
			entity.GotHit(this);
		}*/
		
	}
	
/*	@Override
	public void process(GameData gameData, World world, Entity entity)
	{
		OnUpdate(gameData, world);
		OnRender(gameData, world);
	}*/
}
