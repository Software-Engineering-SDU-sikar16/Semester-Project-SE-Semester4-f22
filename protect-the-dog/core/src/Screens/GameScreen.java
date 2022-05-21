package Screens;

import Entities.Entity;
import Map.Tile;
import Map.TilePath;
import Overlays.Overlay;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;
import helper.Constants;
import helper.MouseOperator;
import helper.TileMapHelper;
import objects.player.Enemy;

import static helper.Constants.PPM;

public class GameScreen extends ScreenAdapter
{
	private SpriteBatch batch;
	private World world;
	private Box2DDebugRenderer box2DDebugRenderer;
	
	private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
	
	private Enemy enemy;
	
	public GameScreen()
	{
		this.batch = new SpriteBatch();
		this.world = new World(new Vector2(0, 0), false); //gravity is -9.81f in y direction
		this.box2DDebugRenderer = new Box2DDebugRenderer();
		
		Constants.TileMapHelper = new TileMapHelper(this);
		this.orthogonalTiledMapRenderer = Constants.TileMapHelper.setupMap();
	}
	
	private void update()
	{
		
		if (!Constants.IsPauseScreenVisible)
		{
			world.step(1 / 60f, 6, 2);
			
		//	cameraUpdate(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			
			Constants.Camera.update();
			
			batch.setProjectionMatrix(Constants.Camera.combined);
			orthogonalTiledMapRenderer.setView(Constants.Camera);
			enemy.update();
			
		}
		
		Constants.EnemyManager.SteerEnemies();
		
		Entity.UpdateAllEntities();
		
		Overlay.UpdateAllOverlays();
		
		
	

	}
	
	private void cameraUpdate(int width, int height)
	{
		Vector3 position = Constants.Camera.position;
		position.x = Math.round(enemy.getBody().getPosition().x * PPM * 10) / 10f;
		position.y = Math.round(enemy.getBody().getPosition().y * PPM * 10) / 10f;
		
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
		this.enemy.getSprite().draw(batch);
		;
		this.enemy.getSprite().translate(20, 20);
		
		
		Vector2 pos =MouseOperator.GetMouseWorldPosition();
		MyGdxGame.sprite.SetPosition(pos.x, pos.y);
		
		
		batch.end();
		box2DDebugRenderer.render(world, Constants.Camera.combined.scl(PPM));
		
		
		// DEBUG
		
		
		for (TilePath street : Constants.GameMapGraph.Paths)
		{
			street.render(Constants.shapeRenderer);
		}
		
		// Draw all cities blue
		for (Tile city : Constants.GameMapGraph.Tiles)
		{
			city.render(Constants.shapeRenderer, Constants.batch, Constants.font, false);
		}
		
		// Draw cities in path green
		for (Tile city : Constants.GameMapPath)
		{
			city.render(Constants.shapeRenderer, Constants.batch, Constants.font, true);
		}
		
		
		Entity.RenderAllEntities();
		
		Overlay.RenderAllOverlays();
	
		
		Constants.Stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
		Constants.Stage.draw();
	}
	
	public World getWorld()
	{
		return world;
	}
	
	public void setEnemy(Enemy enemy)
	{
		this.enemy = enemy;
	}
}
