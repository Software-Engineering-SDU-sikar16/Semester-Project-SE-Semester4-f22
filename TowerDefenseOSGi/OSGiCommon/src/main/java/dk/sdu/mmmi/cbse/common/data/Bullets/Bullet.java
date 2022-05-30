package dk.sdu.mmmi.cbse.common.data.Bullets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Resources;
import dk.sdu.mmmi.cbse.common.data.World;

public class Bullet implements Pool.Poolable
{
	public static Texture BulletTexture = Resources.LoadTexture("../assets/turrets/bullet_4.png");
	
	public Vector2 targetDirection;
	public Vector2 position;
	public Vector2 size;
	
	public float dx = 0;
	public float dy = 0;
	
	Texture texture;
	
	private Rectangle rect;
	
	public Bullet()
	{
		texture = BulletTexture;
		
		position = new Vector2(0, 0);
		size = new Vector2(BulletTexture.getWidth(), BulletTexture.getHeight());
		//setTexture(BulletTexture);
		//sprite.AddAnimation("default", BulletTexture, 1, 8, 5, Animation.PlayMode.NORMAL);
		rect = new Rectangle(0, 0, BulletTexture.getWidth(), BulletTexture.getHeight());
	}
	
	public void setPosition(float x, float y)
	{
		position.set(x, y);
		rect.setPosition(x, y);
	}
	
	public Vector2 getPosition()
	{
		return new Vector2(position.x, position.y);
	}
	
	
	@Override
	public void reset()
	{
		targetDirection.set(0, 0);
		texture = null;
	}
	
	
	public void fireBullet(Vector2 origin, Vector2 enemyPosition)
	{
		position.set(origin.x, origin.y);
		this.targetDirection = enemyPosition;
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
		if (texture != null)
		{
			gameData.GlobalSpriteBatch.draw(texture, position.x, position.y, size.x, size.y);
		}
		
		//super.OnRender(gameData, world);
		
		gameData.GlobalShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		gameData.GlobalShapeRenderer.setColor(Color.WHITE);
		gameData.GlobalShapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		gameData.GlobalShapeRenderer.end();
		
	}
	
	
	public void OnUpdate(GameData gameData, World world)
	{
		position.add(dx, dy);
		
		rect.setPosition(position.x, position.y);
		
		Array<Entity> hitEntities = gameData.enemyQuadTree.Query(rect);
		for (Entity entity : hitEntities)
		{
			entity.OnCollision(gameData, world, null);
		}
		
	}
	
	
}
