package helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MouseOperator
{
	public static Vector3 UnprojectVector = new Vector3();
	public static Vector2 WorldMousePosition = new Vector2();
	public static Vector2 WorldPosition = new Vector2();
	public static Vector2 TileUnderMouseWorldPosition = new Vector2();
	public static Vector2 MousePosition = new Vector2();
	
	
	public static Vector2 ScreenToWorldPoint(float x, float y)
	{
		Constants.Camera.unproject(UnprojectVector.set(x, y, 0.0f));
		WorldMousePosition.set(UnprojectVector.x, UnprojectVector.y);
		return new Vector2(UnprojectVector.x, UnprojectVector.y);
	}
	
	public static Vector2 ScreenToWorldPoint(Vector2 ScreenPoint)
	{
		Constants.Camera.unproject(UnprojectVector.set(ScreenPoint.x, ScreenPoint.y, 0.0f));
		WorldMousePosition.set(UnprojectVector.x, UnprojectVector.y);
		return new Vector2(UnprojectVector.x, UnprojectVector.y);
	}
	
	
	public static Vector2 GetMouseScreenPosition()
	{
		int mx = Gdx.input.getX();
		int my = Gdx.input.getY();
		return MousePosition.set(mx, my);
	}
	
	public static Vector2 GetMouseWorldPosition()
	{
		int mx = Gdx.input.getX();
		int my = Gdx.input.getY();
		
		Constants.Camera.unproject(UnprojectVector.set(mx, my, 0.0f));
		WorldMousePosition.set(UnprojectVector.x, UnprojectVector.y);
		
		return WorldMousePosition;
	}
	
	public static Vector2 GetTilePositionUnderMousePosition()
	{
		int mx = Gdx.input.getX();
		int my = Gdx.input.getY();
		
		int TILE_HEIGHT = Constants.TileMapHelper.TilePixelHeight;
		int TILE_WIDTH = Constants.TileMapHelper.TilePixelWidth;
		
		int tileIndexX = (int) Math.floor(mx / TILE_WIDTH + 0.5f);
		int tileIndexY = (int) Math.floor(my / TILE_HEIGHT + 0.5f);
		
		
		TiledMapTileLayer tileid = (TiledMapTileLayer) Constants.TileMapHelper.tiledMap.getLayers().get(0);
		
		
		
		
	//	Vector2 WorldPoint = ScreenToWorldPoint(tile.getOffsetX(), tile.getOffsetX());
		Vector2 WorldPoint = new Vector2( tileIndexX * TILE_WIDTH,  ((tileid.getHeight() -1) - tileIndexY) * TILE_HEIGHT);
		
		
	/*	Vector2 cell = screenToCell(mx, my);
		System.out.println("selectedCell = " + cell.toString());
		
		//if you want to get the tile and the cell
		TiledMapTileLayer layer = (TiledMapTileLayer) Constants.TileMapHelper.tiledMap.getLayers().get(0);
		System.out.println("layer Width: " + layer.getWidth() + "\n layer Height: " + layer.getHeight());
		
		TiledMapTileLayer.Cell tileCell = layer.getCell((int) cell.x, (int) cell.y);
		if (tileCell != null) {
		
		//flip the tile just so you have a visual to make sure your selected the right tile
		TiledMapTile tile = tileCell.getTile();
		tileCell.setFlipHorizontally(!tileCell.getFlipHorizontally());
		return TileUnderMouseWorldPosition.set(tile.getTextureRegion().getRegionX(), tile.getTextureRegion().getRegionY());
		}
		*/
		
		return TileUnderMouseWorldPosition.set(WorldPoint.x, WorldPoint.y);
	}
	
	/*
	public static Vector2 worldToCell(float x, float y)
	{
		
		int TILE_HEIGHT = Constants.TileMapHelper.TilePixelHeight;
		int TILE_WIDTH = Constants.TileMapHelper.TilePixelWidth;
		
		float halfTileWidth = TILE_WIDTH * 0.5f;
		float halfTileHeight = TILE_HEIGHT * 0.5f;
		
		float row = (1.0f / 2) * (x / halfTileWidth + y / halfTileHeight);
		float col = (1.0f / 2) * (x / halfTileWidth - y / halfTileHeight);
		
		return new Vector2((int) col, (int) row);
	}
	
	public static Vector2 screenToWorld(float x, float y)
	{
		Vector3 touch = new Vector3(x, y, 0);
		Constants.Camera.unproject(touch);
		touch.mul(Constants.Camera.view.inv());
		touch.mul(Constants.Camera.view);
		return new Vector2(touch.x, touch.y);
	}*/
	
	
/*	public static Vector2 screenToCell(float x, float y)
	{
		int TILE_HEIGHT = Constants.TileMapHelper.TilePixelHeight;
		Vector2 world = screenToWorld(x, y);
		world.y -= TILE_HEIGHT * 0.5f;
		return worldToCell(world.x, world.y);
	}
	*/
	
	public static Vector2 GetTilePositionUnderMousePosition2()
	{
		int mx = Gdx.input.getX();
		int my = Gdx.input.getY();
		
		int TILE_HEIGHT = Constants.TileMapHelper.TilePixelHeight;
		int TILE_WIDTH = Constants.TileMapHelper.TilePixelWidth;
		
		Constants.Camera.unproject(UnprojectVector.set(mx, my, 0.0f));
		WorldMousePosition.set(UnprojectVector.x, UnprojectVector.y);
		
		float mapx = WorldMousePosition.x / TILE_WIDTH + 0.5f;
		float mapy = WorldMousePosition.y / TILE_HEIGHT + 0.5f;
		
		WorldPosition = new Vector2(mapx, mapy);
		
		int tileX = (int) WorldPosition.x;
		int tileY = (int) WorldPosition.y;
		
		TileUnderMouseWorldPosition.set(tileX, tileY);
		return TileUnderMouseWorldPosition;
	/*
		if (Gdx.input.justTouched())
		{
			Tile graphTile = new Tile(tileX * TILE_WIDTH, tileY * TILE_HEIGHT, "Tile " +  cell.getTile().getId());
			Constants.GameMapGraph.AddTile(graphTile);
		}*/
		
	/*	if (Gdx.input.justTouched())
		{
			
			
			TiledMapTileLayer layer = (TiledMapTileLayer) Constants.TileMapHelper.tiledMap.getLayers().get("background");
			
			TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);
			
			if (cell != null)
			{
				int cellId = cell.getTile().getId();
				if (cellId == 82 || cellId == 87 || cellId == 142 || cellId == 41)
				{
					if (Gdx.input.justTouched())
					{
						Tile graphTile = new Tile(tileX * TILE_WIDTH, tileY * TILE_HEIGHT, "Tile " + cell.getTile().getId());
						Constants.GameMapGraph.AddTile(graphTile);
					}
				}
				
			}
			TileUnderMouseWorldPosition.set(tileX, tileY);
		}*/
		
	}
}
