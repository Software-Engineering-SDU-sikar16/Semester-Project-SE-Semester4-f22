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

        MapPixelWidth = MapWidth * TilePixelWidth; //How Wide the Map is in Pixels
        MapPixelHeight = MapHeight * TilePixelHeight; // Height in Pixels

        System.out.println("Loaded TileMap " + TilemapPath + "\nMapWidth: " + MapWidth + "\nMapHeight: " + MapHeight + "\nTilePixelWidth: " + TilePixelWidth + "\nTilePixelHeight: " + TilePixelHeight + "\nMapPixelWidth: " + MapPixelWidth + "\nMapPixelHeight: " + MapPixelHeight);

        CalculateGraph(gameData); //Calculate the graph of the map
        BuildHashmapOfBuildableAreas(); //Build the hashmap of buildable areas
        return new OrthogonalTiledMapRenderer(tiledMap); //Return the renderer
    }

    private void BuildHashmapOfBuildableAreas() {
        MapLayer layer = tiledMap.getLayers().get("PlacementLayer"); //Get the layer with the buildable areas
        MapObjects mapObjects = layer.getObjects(); //Get the objects in the layer

        for (MapObject mapObject : mapObjects) { //For each object in the layer (buildable area)
            if (mapObject instanceof RectangleMapObject) { //If the object is a rectangle
                RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject; //Cast the object to a rectangle
                // now you can get the position of the rectangle like this:
                Rectangle rectangle = rectangleMapObject.getRectangle(); //Get the rectangle
                int x = (int) rectangle.getX(); //Get the x position of the rectangle
                int y = (int) rectangle.getY(); //Get the y position of the rectangle

                Tile graphTile = new Tile(x, y, rectangleMapObject.getName()); //Create a new tile with the x and y position and the name of the object
                BuildableTiles.put(new Vector2(x, y), graphTile); //Add the tile to the hashmap of buildable areas
            }
        }
    }

    public void CalculateGraph(GameData gameData) {
        gameData.GameMapGraph = new GameMapGraph(); //Create a new graph
        Tile PreviousTile = null; //Create a new previous tile
        MapLayer layer = tiledMap.getLayers().get("PathLayer"); //Get the layer with the path
        MapObjects mapObjects = layer.getObjects(); //Get the objects in the layer

        for (MapObject mapObject : mapObjects) { //For each object in the layer (path)
            if (mapObject instanceof RectangleMapObject) { //If the object is a rectangle
                RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject; //Cast the object to a rectangle
                // now you can get the position of the rectangle like this:
                Rectangle rectangle = rectangleMapObject.getRectangle(); //Get the rectangle
                int x = (int) rectangle.getX(); //Get the x position of the rectangle
                int y = (int) rectangle.getY(); //Get the y position of the rectangle

                // create a graph tile for each object.
                Tile graphTile = new Tile(x, y, rectangleMapObject.getName()); //Create a new tile with the x and y position and the name of the object
                gameData.GameMapGraph.AddTile(graphTile); //Add the tile to the graph

                EnemyPath.put(new Vector2(x, y), graphTile); //Add the tile to the hashmap of enemies path

                if (Start == null) { //If the start tile is null
                    Start = graphTile; //Set the start tile to the current tile
                }

                if (PreviousTile != null) { //If the previous tile is not null
                    gameData.GameMapGraph.ConnectTiles(PreviousTile, graphTile); //Connect the previous tile to the current tile
                }
                PreviousTile = graphTile; //Set the previous tile to the current tile

            }
        }

        End = PreviousTile;
        EnemyPath.put(new Vector2(End.x, End.y), End);
        gameData.GameMapPath = gameData.GameMapGraph.FindPath(Start, End);
    }
    

    public boolean IsTileAtPositionAValidBuildableTile(Vector2 vector) {
        return BuildableTiles.containsKey(vector);
    }

    // Gets the next tile we should be heading to from the position of the current tile. e.g. input is Tile xy of A => output i


}
