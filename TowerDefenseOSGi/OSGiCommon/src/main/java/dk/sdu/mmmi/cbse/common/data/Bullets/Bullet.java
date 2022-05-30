package dk.sdu.mmmi.cbse.common.data.Bullets;

import com.badlogic.gdx.Gdx;
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
	public static Texture BulletTexture = Resources.LoadTexture("../assets/turrets/bullet_2.png");
	
	public Vector2 targetDirection;
	public Vector2 position;
	public Vector2 size;
	
	public static float bulletSpeed = 70;
	
	public float dx = 0;
	public float dy = 0;
	
	Texture texture;
	
	private Rectangle rect;
	
	public Bullet()
	{
		texture = BulletTexture;
		size = new Vector2(BulletTexture.getWidth(), BulletTexture.getHeight());
		position = new Vector2(0,0);

		int bulletWidthSquared = BulletTexture.getWidth() * 2;
		int bulletHeightSquared = BulletTexture.getHeight() * 2;
		
		rect = new Rectangle(0, 0, bulletWidthSquared, bulletHeightSquared);
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
		gameData.GlobalSpriteBatch.begin();
		if (texture != null)
		{
			gameData.GlobalSpriteBatch.draw(texture, position.x, position.y, size.x, size.y);
		}
		gameData.GlobalSpriteBatch.end();
		
		//super.OnRender(gameData, world);
		
		if (gameData.DEBUG) {
			gameData.GlobalShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			gameData.GlobalShapeRenderer.setColor(Color.WHITE);
			gameData.GlobalShapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
			gameData.GlobalShapeRenderer.end();
		}
		
	}
	
	
	public void OnUpdate(GameData gameData, World world)
	{
		position.add(dx * Gdx.graphics.getDeltaTime() * bulletSpeed, dy * Gdx.graphics.getDeltaTime() * bulletSpeed);
		rect.setPosition(position.x, position.y);
		
		Array<Entity> hitEntities = gameData.enemyQuadTree.Query(rect);
		for (Entity entity : hitEntities)
		{
			entity.OnCollision(gameData, world, null);
		}
		
	}
	
	
}
