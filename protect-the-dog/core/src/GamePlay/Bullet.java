package GamePlay;

import Entities.AnimatedSprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import helper.Constants;
import helper.Resources;


public class Bullet extends AnimatedSprite implements Pool.Poolable
{
	public static Texture BulletTexture = Resources.LoadTexture("turrets/bullet_4.png");
	
	public Vector2 TargetDirection;
	public float dx = 0;
	public float dy = 0;
	
	private Rectangle rect;
	
	public Bullet()
	{
		super(0, 0, 0, 0);
		setSize(BulletTexture.getWidth(), BulletTexture.getHeight());
		//setTexture(BulletTexture);
		AddAnimation("default", BulletTexture, 1, 8, 5, Animation.PlayMode.NORMAL);
		rect = new Rectangle(0, 0, getWidth(), getHeight());
	}
	
	
	@Override
	public void reset()
	{
		TargetDirection.set(0, 0);
		setTexture(null);
	}
	
	public void OnUpdate()
	{
		translate(dx, dy);
		
		
		rect.setPosition(getPosition());
		
		Array<EnemyEntity> hitEntities = Constants.EnemyQuadTree.Query(rect);
		for (EnemyEntity entity : hitEntities)
		{
			entity.GotHit(this);
		}
		
	
		
		//check collision with entities
		
		
		
	/*	Vector2 origin = getPosition();
		Vector2 target = TargetDirection;
		Vector2 direction = target.sub(origin);
		direction.nor();
		direction.scl(bulletSpeed * Gdx.graphics.getDeltaTime());
		Vector2 result = origin.add(direction);
		setPosition(result.x, result.y);
		*/
		
		
	/*	Vector2 origin = getPosition();
		TargetDirection = TargetDirection.nor();
		Vector2 endpoint = origin.add(TargetDirection).scl(bulletSpeed * Gdx.graphics.getDeltaTime());
		
		//Vector2 endpoint = Util.Lerp(getPosition(), direction, 0.5f * Gdx.graphics.getDeltaTime());
		//	setPosition(endpoint.x, endpoint.y);
		
		translate(endpoint.x, endpoint.y);*/
	}
	
	
	@Override
	public void OnRender()
	{
		super.OnRender();
		
	/*	Constants.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		Constants.shapeRenderer.setColor(Color.WHITE);
		Constants.shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		Constants.shapeRenderer.end();*/
		
	}
	
	public void fireBullet(Vector2 origin, Vector2 enemyPosition)
	{
		
		
		setPosition(origin);
		this.TargetDirection = enemyPosition;
		float angle = enemyPosition.sub(origin).angleDeg();
		
		//	float angle = (float)Math.atan(direction.x / direction.y);
		
		//	float angle = 180;
		double angleInRads = Math.toRadians(angle);
		dx = (float) Math.cos(angleInRads);
		dy = (float) Math.sin(angleInRads);
		
	}
	
}
