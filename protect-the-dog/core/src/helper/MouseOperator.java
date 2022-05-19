package helper;

import Map.Tile;
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
		
		Constants.Camera.unproject(UnprojectVector.set(mx, my, 0.0f));
		WorldMousePosition.set(UnprojectVector.x, UnprojectVector.y);
		float r = (float) TILE_HEIGHT / TILE_WIDTH;
		
		float mapx = WorldMousePosition.x / TILE_WIDTH;
		float mapy = WorldMousePosition.y / TILE_HEIGHT;
		
		WorldPosition = new Vector2(mapx - 0.5f, mapy + 0.5f); // -.5/+.5 because the drawing isn't aligned to the tile, it's aligned to the image
		
		int tileX = (int) WorldPosition.x;
		int tileY = (int) WorldPosition.y;
		
		return TileUnderMouseWorldPosition.set(tileX, tileY);
	}
	
	
	public static TiledMapTileLayer.Cell GetTileUnderMousePosition()
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
		
		
		TiledMapTileLayer layer = (TiledMapTileLayer) Constants.TileMapHelper.tiledMap.getLayers().get(0);
		
		TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);
		if (cell != null)
		{
			if (Gdx.input.justTouched())
			{
				Tile graphTile = new Tile(tileX * TILE_WIDTH, tileY * TILE_HEIGHT, "Tile " + cell.getTile().getProperties().getKeys().toString());
				Constants.GameMapGraph.AddTile(graphTile);
			}
		}
		
		TileUnderMouseWorldPosition.set(tileX, tileY);
		return cell;
	}
}
