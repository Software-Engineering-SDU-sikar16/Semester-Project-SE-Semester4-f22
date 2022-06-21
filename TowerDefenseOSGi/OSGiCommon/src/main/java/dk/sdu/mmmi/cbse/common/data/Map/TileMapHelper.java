package dk.sdu.mmmi.cbse.common.data.Map;


import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.cbse.common.data.GameData;

import java.util.HashMap;


public class TileMapHelper {
    public static HashMap<Vector2, Tile> EnemyPath = new HashMap<Vector2, Tile>();
    public static HashMap<Vector2, Tile> BuildableTiles = new HashMap<Vector2, Tile>();
    public static TiledMap tiledMap;
    public int MapWidth; // Width in Tiles
    public int MapHeight; // Height in Tiles
    public int TilePixelWidth; //How Wide one Tile is in Pixels
    public int TilePixelHeight; //How High one Tile is in Pixels
    public int MapPixelWidth;
    public int MapPixelHeight;
    public Tile Start = null;
    public Tile End = null;

    public OrthogonalTiledMapRenderer setupMap(GameData gameData, String TilemapPath) {
        tiledMap = new TmxMapLoader().load(TilemapPath);
        TiledMapTileLayer tileid = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

//		System.out.print(tileid.getCell(0, 0).getTile().getObjects().toString());
//		System.out.println();

        MapProperties prop = tiledMap.getProperties();
        MapWidth = prop.get("width", Integer.class);
        MapHeight = prop.get("height", Integer.class);
        TilePixelWidth = prop.get("tilewidth", Integer.class);
        TilePixelHeight = prop.get("tileheight", Integer.class);
        System.out.println("Tile Width: " + TilePixelWidth);

        MapPixelWidth = MapWidth * TilePixelWidth;
        MapPixelHeight = MapHeight * TilePixelHeight;

        System.out.println("Loaded TileMap " + TilemapPath + "\nMapWidth: " + MapWidth + "\nMapHeight: " + MapHeight + "\nTilePixelWidth: " + TilePixelWidth + "\nTilePixelHeight: " + TilePixelHeight + "\nMapPixelWidth: " + MapPixelWidth + "\nMapPixelHeight: " + MapPixelHeight);

        CalculateGraph(gameData);
        BuildHashmapOfBuildableAreas();
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    private void BuildHashmapOfBuildableAreas() {
        MapLayer layer = tiledMap.getLayers().get("PlacementLayer");
        MapObjects mapObjects = layer.getObjects();

        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof RectangleMapObject) {
                RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
                // now you can get the position of the rectangle like this:
                Rectangle rectangle = rectangleMapObject.getRectangle();
                int x = (int) rectangle.getX();
                int y = (int) rectangle.getY();

                Tile graphTile = new Tile(x, y, rectangleMapObject.getName());
                BuildableTiles.put(new Vector2(x, y), graphTile);
            }
        }
    }

    public void CalculateGraph(GameData gameData) {
        gameData.GameMapGraph = new GameMapGraph();
        Tile PreviousTile = null;
        MapLayer layer = tiledMap.getLayers().get("PathLayer");
        MapObjects mapObjects = layer.getObjects();

        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof RectangleMapObject) {
                RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
                // now you can get the position of the rectangle like this:
                Rectangle rectangle = rectangleMapObject.getRectangle();
                int x = (int) rectangle.getX();
                int y = (int) rectangle.getY();

                // create a graph tile for each object.
                Tile graphTile = new Tile(x, y, rectangleMapObject.getName());
                gameData.GameMapGraph.AddTile(graphTile);

                EnemyPath.put(new Vector2(x, y), graphTile);

                if (Start == null) {
                    Start = graphTile;
                }

                if (PreviousTile != null) {
                    gameData.GameMapGraph.ConnectTiles(PreviousTile, graphTile);
                }
                PreviousTile = graphTile;

            }
        }

        End = PreviousTile;
        EnemyPath.put(new Vector2(End.x, End.y), End);
        gameData.GameMapPath = gameData.GameMapGraph.FindPath(Start, End);
    }


    public Tile GetEnemyPathTileAt(Vector2 vector) {
        return EnemyPath.get(vector);
    }

    public boolean IsTileAtPositionAValidBuildableTile(Vector2 vector) {
        return BuildableTiles.containsKey(vector);
    }

    public Tile GetEnemyPathTileAt(float x, float y) {
        return EnemyPath.get(new Vector2(x, y));
    }

    // Gets the next tile we should be heading to from the position of the current tile. e.g. input is Tile xy of A => output is Tile B;
    public Tile GetNextEnemyPathTileFromCurrentTileAt(GameData gameData, float x, float y) {
        Tile currentTile = EnemyPath.get(new Vector2(x, y));
        if (currentTile == null) {
            currentTile = Start;
        }

        if (gameData.GameMapPath.getCount() - 1 >= currentTile.index + 1) {
            Tile nextTile = gameData.GameMapPath.get(currentTile.index + 1);
            return nextTile;
        } else {
            return null;
        }
    }


}
