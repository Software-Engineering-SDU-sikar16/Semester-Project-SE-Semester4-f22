package Screens;

import Algorithms.EnemyQuadTree;
import Entities.Entity;
import GamePlay.Bullet;
import GamePlay.EnemyEntity;
import GamePlay.Turret;
import GamePlay.WaveManager;
import Overlays.Overlay;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import helper.Constants;
import helper.DrawUtil;
import helper.MouseOperator;
import helper.TileMapHelper;
//import objects.player.Enemy;

import static helper.Constants.PPM;

public class GameScreen extends ScreenAdapter
{
	private SpriteBatch batch;
	private Box2DDebugRenderer box2DDebugRenderer;
	private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
	
	public GameScreen()
	{
		this.batch = new SpriteBatch();
		Constants.World = new World(new Vector2(0, 0), false); //gravity is -9.81f in y direction
		this.box2DDebugRenderer = new Box2DDebugRenderer();
		Constants.TileMapHelper = new TileMapHelper(this);
		this.orthogonalTiledMapRenderer = Constants.TileMapHelper.setupMap();
		Constants.EnemyQuadTree = new EnemyQuadTree();
		Constants.WaveManager = new WaveManager();
	}
	
	private void update()
	{
		Constants.WaveManager.OnUpdate();
		if (!Constants.IsPauseScreenVisible)
		{
			Constants.World.step(1 / 60f, 6, 2);
			//	cameraUpdate(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			Constants.Camera.update();
			batch.setProjectionMatrix(Constants.Camera.combined);
			orthogonalTiledMapRenderer.setView(Constants.Camera);
		}
		
		Constants.EnemyManager.SteerEnemies();
		Entity.UpdateAllEntities();
		Overlay.UpdateAllOverlays();
		Turret.TryBuildTurret();
		// update turrets
		for (Turret turret : Turret.Turrets)
		{
			//use quad tree for better performance.
			Array<EnemyEntity> hitEntities = Constants.EnemyQuadTree.Query(turret.hitRect);
			for (EnemyEntity enemy : hitEntities)
			{
				if (enemy.IsDead())
				{
					continue;
				}
				turret.EnemyIsClose(enemy);
			}
		}
		
		
		Constants.EnemyQuadTree.OnUpdate();
		
		// update bullets
		//loop through all our active bullets
		for (Bullet bullet : Constants.ActiveBullets)
		{
			bullet.OnUpdate(); // update bullet
		}
		
		// loop through bullets again
		for (Bullet bullet : Constants.ActiveBullets)
		{
			Vector2 bulletPos = bullet.getPosition();
			if (bulletPos.x > Constants.GlobalWidth || bulletPos.x < -50 || bulletPos.y < -50 || bulletPos.y > Constants.GlobalHeight)
			{
				// bullet is off screen so free it and then remove it
				Constants.BulletPool.free(bullet); // place back in pool
				Constants.ActiveBullets.removeValue(bullet, true); // remove bullet from our array so we don't render it anymore
			}
		}
		
	
	}
	
	private void cameraUpdate(int width, int height)
	{
		Vector3 position = Constants.Camera.position;
		Constants.Camera.viewportHeight = height;
		Constants.Camera.viewportWidth = width;
		// camera.position.set(position);
		Constants.Camera.position.set(width / 2f, height / 2f, 0); //by default camera position on (0,0,0)
		//camera.position.set(new Vector3(0, 0, 0));
		Constants.Camera.update();
	}
	
	
	@Override
	public void render(float delta)
	{
		this.update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		//	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		
		
		orthogonalTiledMapRenderer.render();
		
		batch.begin();
		// render objects
//		this.enemy.getSprite().draw(batch);
//		this.enemy.getSprite().translate(20, 20);
		
		
		batch.end();
		box2DDebugRenderer.render(Constants.World, Constants.Camera.combined.scl(PPM));
		
		
		// DEBUG
		
		
		Entity.RenderAllEntities();
		
		Overlay.RenderAllOverlays();
		
		// render bullets
		
		
		// update bullets
		//loop through all our active bullets
		for (Bullet bullet : Constants.ActiveBullets)
		{
			bullet.OnRender(); // update bullet
		}
		
		
		//Constants.EnemyQuadTree.OnRender();
		
		
		
		Vector2 mousePosition = MouseOperator.GetMouseWorldPosition();
		Constants.batch.begin();
		if (Constants.Coins < Turret.TurretPriceInCoins)
		{
			DrawUtil.DrawText(Constants.batch, Constants.ScoreUIFont, "" + Turret.TurretPriceInCoins, mousePosition.x + 20, mousePosition.y - 5, new Color(1.0f, 0.2f, 0.2f, 1.0f));
		}
		else
		{
			DrawUtil.DrawText(Constants.batch, Constants.ScoreUIFont, "" + Turret.TurretPriceInCoins, mousePosition.x + 20, mousePosition.y - 5, new Color(1.0f, 1.0f, 1.0f, 1.0f));
		}
		Constants.batch.end();
		
		Constants.Stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
		Constants.Stage.draw();
	}
	
	public World getWorld()
	{
		return Constants.World;
	}
	
//	public void setEnemy(Enemy enemy)
//	{
//		this.enemy = enemy;
//	}
}
