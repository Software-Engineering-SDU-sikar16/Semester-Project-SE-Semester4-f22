package helper;

import Map.GameMapGraph;
import Map.Tile;
import Screens.GameScreen;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import objects.player.Enemy;

import static helper.Constants.PPM;

public class TileMapHelper
{
	
	public TiledMap tiledMap;
	private GameScreen gameScreen;
	
	public int MapWidth; // Width in Tiles
	public int MapHeight; // Height in Tiles
	public int TilePixelWidth; //How Wide one Tile is in Pixels
	public int TilePixelHeight; //How High one Tile is in Pixels
	public int MapPixelWidth;
	public int MapPixelHeight;
	
	public Tile Start = null;
	public Tile End = null;
	
	
	public TileMapHelper(GameScreen gameScreen)
	{
		this.gameScreen = gameScreen;
	}
	
	public OrthogonalTiledMapRenderer setupMap()
	{
		String TilemapPath = "maps/map1.tmx";
		tiledMap = new TmxMapLoader().load("maps/map1.tmx");
		parseMapObjects(tiledMap.getLayers().get("objects").getObjects());
		
		TiledMapTileLayer tileid = (TiledMapTileLayer) tiledMap.getLayers().get(0);
		
		TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
		
		System.out.print(tileid.getCell(0, 0).getTile().getObjects().toString());
		
		
		MapProperties prop = tiledMap.getProperties();
		
		System.out.println();
		MapWidth = prop.get("width", Integer.class);
		MapHeight = prop.get("height", Integer.class);
		TilePixelWidth = prop.get("tilewidth", Integer.class);
		TilePixelHeight = prop.get("tileheight", Integer.class);
		
		MapPixelWidth = MapWidth * TilePixelWidth;
		MapPixelHeight = MapHeight * TilePixelHeight;
		
		
		System.out.println("Loaded TileMap " + TilemapPath + "\nMapWidth: " + MapWidth + "\nMapHeight: " + MapHeight + "\nTilePixelWidth: " + TilePixelWidth + "\nTilePixelHeight: " + TilePixelHeight + "\nMapPixelWidth: " + MapPixelWidth + "\nMapPixelHeight: " + MapPixelHeight);
		
		CalculateGraph();
		
		return new OrthogonalTiledMapRenderer(tiledMap);
	}
	
	public void CalculateGraph()
	{
		Constants.GameMapGraph = new GameMapGraph();
		
		Tile PreviousTile = null;
		
		MapLayer layer = tiledMap.getLayers().get("PathLayer");
		
		MapObjects mapObjects = layer.getObjects();
		
		for (MapObject mapObject : mapObjects)
		{
			if (mapObject instanceof RectangleMapObject)
			{
				RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
				// now you can get the position of the rectangle like this:
				Rectangle rectangle = rectangleMapObject.getRectangle();
				int x = (int) rectangle.getX();
				int y = (int) rectangle.getY();
				
				// create a graph tile for each object.
				Tile graphTile = new Tile(x, y, rectangleMapObject.getName());
				Constants.GameMapGraph.AddTile(graphTile);
				
				if (Start == null)
				{
					Start = graphTile;
				}
				
				if (PreviousTile != null)
				{
					Constants.GameMapGraph.ConnectTiles(PreviousTile, graphTile);
				}
				PreviousTile = graphTile;
				
			}
		}
		
		End = PreviousTile;
		Constants.GameMapPath = Constants.GameMapGraph.FindPath(Start, End);
	
	}

	private void parseMapObjects(MapObjects mapObjects)
	{
		for (MapObject mapObject : mapObjects)
		{
			if (mapObject instanceof PolygonMapObject)
			{
				createStaticBody((PolygonMapObject) mapObject);
			}
			
			
			if (mapObject instanceof RectangleMapObject)
			{
				Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
				String rectangleName = mapObject.getName();
				
				boolean findEnemy = rectangleName.equals("enemy");
				
				if (findEnemy)
				{
					Body body = BodyHelperService.createBody(
							rectangle.getX() + rectangle.getWidth() / 2,
							rectangle.getY() + rectangle.getHeight() / 2,
							rectangle.getWidth(),
							rectangle.getHeight(),
							false,
							gameScreen.getWorld());
					gameScreen.setEnemy(new Enemy(rectangle.getWidth(), rectangle.getHeight(), body));
				}
				
			}
			
		}
		
	}
	
	private void createStaticBody(PolygonMapObject polygonMapObject)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		Body body = gameScreen.getWorld().createBody(bodyDef);
		Shape shape = createPolygonShape(polygonMapObject);
		body.createFixture(shape, 1000f);
		System.out.println(body.getPosition());
		shape.dispose();
	}
	
	private Shape createPolygonShape(PolygonMapObject polygonMapObject)
	{
		float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
		Vector2[] worldVertices = new Vector2[vertices.length / 2];
		for (int i = 0; i < vertices.length / 2; i++)
		{
			Vector2 current = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
			worldVertices[i] = current;
		}
		PolygonShape shape = new PolygonShape();
		shape.set(worldVertices);
		return shape;
	}
}
