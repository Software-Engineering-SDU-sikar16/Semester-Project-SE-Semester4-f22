package GamePlay;

import Entities.AnimatedSprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.particles.influencers.RegionInfluencer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import helper.BodyHelperService;
import helper.Constants;
import helper.MouseOperator;
import helper.Resources;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static helper.Constants.PPM;

public class Turret extends AnimatedSprite
{
	public static Array<Turret> Turrets = new Array<Turret>();
	public static HashMap<Vector2, Turret> turretPositions = new HashMap<Vector2, Turret>();
	public static Random random = new Random();
	
	public AnimatedSprite sprite;
	public Body body;
	
	public boolean IsShooting = false;
	public Vector2 EnemyPosition;
	public float EnemyDistance = 0;
	
	public Circle circle; // this circle is for checking whether the enemy is getting close to the turret.
	
	public Rectangle rect; // this rectangle is for checking whether the mouse is hovering over the turret
	
	public Turret(int x, int y)
	{
		super(x, y, 50, 50);
		
		Texture turret1 = Resources.LoadTexture("turrets/4shot.png");
		sprite = new AnimatedSprite(turret1, x,y);
		
		int width = turret1.getWidth();
		int height = turret1.getHeight();
		
		setSize(width, height);
		
		circle = new Circle(x, y, 90);
		rect = new Rectangle(x, y, width, height);
		
		int initialDelay = random.nextInt(500) * 10; // start after 1 seconds
		int period = 500;        // repeat every 0.5 seconds
		Timer timer = new Timer();
		TimerTask task = new TimerTask()
		{
			public void run()
			{
				SpawnBullet();
			}
		};
		timer.schedule(task, initialDelay, period);
		
		
		body = BodyHelperService.createBody(x + width / 2, y + height / 2, width, height, false, Constants.World);
		
		
		body.setBullet(true);
		
		setTexture(turret1);
		Turrets.add(this);
		turretPositions.put(new Vector2(x, y), this);
	}
	
	private void SpawnBullet()
	{
	}
	
	@Override
	public void OnUpdate(float DeltaTime)
	{
		super.OnUpdate(DeltaTime);
		
	}
	
	@Override
	public void OnRender()
	{
		super.OnRender();
		
	/*	Constants.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		Constants.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1);
		Constants.shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
		Constants.shapeRenderer.end();*/
		
		Vector2 mousePosition = MouseOperator.GetMouseWorldPosition();
		if (rect.contains(mousePosition))
		{
			Constants.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			Constants.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1);
			Constants.shapeRenderer.circle(getEntityX(), getEntityY(), 100);
			Constants.shapeRenderer.end();
		}
		
		if (IsShooting)
		{
			
			Vector2 Origin = new Vector2(getX(), getY());
			Origin = MouseOperator.ScreenToWorldPoint(Origin);
			if (body.getPosition().x > Constants.GlobalWidth || body.getPosition().x < 0 || body.getPosition().y < 0 || body.getPosition().y > Constants.GlobalHeight)
			{
				
				//body.setTransform(Origin.x / 100, Origin.y / 100, body.getAngle());
			}
			
			Vector2 Direction = Origin.sub(EnemyPosition);
			Vector2 endpoint = Origin.add(Direction).scl(1);
			EnemyDistance++;
			
			sprite.translate(endpoint.x, endpoint.y);
			//body.setLinearVelocity(endpoint);
		} else {
			sprite.setPosition(0,0);
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
				if (Constants.Coins - 500 < 0) // if the user has money to build this turret
				{
					return;
				}
				Constants.Coins -= 500;
				
				if (!turretPositions.containsKey(TilePos))
				{
					new Turret((int) TilePos.x, (int) TilePos.y);
				}
			}
		}
	}
	
	public void EnemyIsClose(EnemyEntity enemy)
	{
		if (enemy == null)
		{
			IsShooting = false;
		}
		else
		{
			IsShooting = true;
			System.out.println("Is Shooting");
			EnemyPosition = new Vector2(enemy.getX(), enemy.getY());
			EnemyDistance = EnemyPosition.len();
		}
		
		
	}
}

