package GamePlay;

import Entities.AnimatedSprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import helper.Constants;
import helper.MouseOperator;
import helper.Resources;

public class Player extends AnimatedSprite
{
	
	private float Speed = 2.0f;
	
	private int IsMoving = 0;
	
	public Player()
	{
		super(MouseOperator.ScreenToWorldPoint(Constants.GlobalWidth / 2, Constants.GlobalHeight / 2), 150, 150);
		
		
		
		AddAnimation("default", Resources.LoadTexture("entities/enemies/enemies/sheet_0.png "), 1, 4, 12, Animation.PlayMode.NORMAL);
		
		SetZIndex(100);
	}
	
	
	@Override
	public void OnUpdate(float DeltaTime)
	{
		super.OnUpdate(DeltaTime);
		
		IsMoving = 0;
		//Vector2 pos = MouseOperator.GetMouseWorldPosition();
		//setPosition(pos.x, pos.y);
		
		if (Gdx.input.isKeyPressed(Input.Keys.A))
		{
			translateX(-1 * Speed);
			IsMoving++;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.D))
		{
			translateX(1 * Speed);
			IsMoving++;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.W))
		{
			translateY(1 * Speed);
			IsMoving++;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.S))
		{
			translateY(-1 * Speed);
			IsMoving++;
		}
		
		
		// increase speed if holding shift
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
		{
			Speed = 4.0f;
		}
		else
		{
			Speed = 2.0f;
		}
		
		
		// if not moving, don't animate.
		if (IsMoving > 0)
		{
			SetAnimationSpeed(12.0f);
		}
		else
		{
			SetAnimationSpeed(0.0f);
		}
		
		
	}
	
	@Override
	public void OnRender()
	{
		super.OnRender();
		
		
		TryBuildTurret();
	}
	
	private void TryBuildTurret()
	{
		// look at tiles from the players position to aroudn the player and highlight them.
		
		/*		Constants.batch.begin();*/
		
	
		Vector2 TilePos = MouseOperator.GetTilePositionUnderMousePosition();
		
		Constants.MouseTileSelector.setPosition(TilePos.x, TilePos.y);
		Constants.MouseTileSelector.setSize(Constants.TileMapHelper.TilePixelWidth, Constants.TileMapHelper.TilePixelHeight);
		
		
		Constants.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		Constants.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1);
		Constants.shapeRenderer.circle(getEntityX(), getEntityY(), 90);
		Constants.shapeRenderer.end();
		
		
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Constants.IsBuildingTurret)
		{
			Constants.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			Constants.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1);
			Constants.shapeRenderer.circle(getEntityX(), getEntityY(), 90);
			Constants.shapeRenderer.end();
			
			TiledMapTileLayer layer = (TiledMapTileLayer) Constants.TileMapHelper.tiledMap.getLayers().get("background");
			TiledMapTileLayer.Cell cell = layer.getCell((int) TilePos.x, (int) TilePos.y);
			
			
			int TILE_HEIGHT = Constants.TileMapHelper.TilePixelHeight;
			int TILE_WIDTH = Constants.TileMapHelper.TilePixelWidth;
			
			if (cell != null)
			{
				int cellId = cell.getTile().getId();
				if (cellId == 82 || cellId == 87 || cellId == 142 || cellId == 41)
				{
					if (Gdx.input.justTouched())
					{
						//Tile graphTile = new Tile((int)TilePos.x * TILE_WIDTH, (int)TilePos.y * TILE_HEIGHT, "Tile " + cell.getTile().getId());
						//Constants.GameMapGraph.AddTile(graphTile);
					}
				}
				
			}
		}
		
		if (Gdx.input.justTouched())
		{
		
		}

		
		
		

/*

		batch.begin();
		Constants.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		Constants.font.draw(batch, "" + index, x, y);
		//font.draw(batch, name, x, y);
		batch.end();
		
		Constants.batch.end();
*/
	
	
	}
	
}
