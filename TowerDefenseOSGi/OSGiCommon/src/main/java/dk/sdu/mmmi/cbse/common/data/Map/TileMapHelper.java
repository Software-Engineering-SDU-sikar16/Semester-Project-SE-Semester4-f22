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
    public static HashMap<Vector2, Tile> EnemyPath = new HashMap<Vector2, Tile>(); //HashMap to store the path of the enemy.
    public static HashMap<Vector2, Tile> BuildableTiles = new HashMap<Vector2, Tile>();
    public static TiledMap tiledMap; // the map
    public int MapWidth; // Width in Tiles
    public int MapHeight; // Height in Tiles
    public int TilePixelWidth; //How Wide one Tile is in Pixels
    public int TilePixelHeight; //How High one Tile is in Pixels
    public int MapPixelWidth; //How Wide the Map is in Pixels
    public int MapPixelHeight; //How High the Map is in Pixels
    public Tile Start = null; //Start Tile
    public Tile End = null; //End Tile

    public OrthogonalTiledMapRenderer setupMap(GameData gameData, String TilemapPath) {
        tiledMap = new TmxMapLoader().load(TilemapPath); //Load the Tilemap from the Path given in the constructor of the class

        MapProperties prop = tiledMap.getProperties();
        MapWidth = prop.get("width", Integer.class); // Get the width of the map in tiles
        MapHeight = prop.get("height", Integer.class); // height in tiles
        TilePixelWidth = prop.get("tilewidth", Integer.class); //How Wide one Tile is in Pixels
        TilePixelHeight = prop.get("tileheight", Integer.class); //How High one Tile is in Pixels
        System.out.println("Tile Width: " + TilePixelWidth);

        MapPixelWidth = MapWidth * TilePixelWidth; //How Wide the Map is in Pixels
        MapPixelHeight = MapHeight * TilePixelHeight; // Height in Pixels

        System.out.println("Loaded TileMap " + TilemapPath + "\nMapWidth: " + MapWidth + "\nMapHeight: " + MapHeight + "\nTilePixelWidth: " + TilePixelWidth + "\nTilePixelHeight: " + TilePixelHeight + "\nMapPixelWidth: " + MapPixelWidth + "\nMapPixelHeight: " + MapPixelHeight);

        CalculateGraph(gameData); //Calculate the graph of the map
        BuildHashmapOfBuildableAreas(); //Build the hashmap of buildable areas
        return new OrthogonalTiledMapRenderer(tiledMap); //Return the renderer for the map
    }

    private void BuildHashmapOfBuildableAreas() { //Build the hashmap of buildable areas
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

    public void CalculateGraph(GameData gameData) { //Calculate the graph of the map
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

        End = PreviousTile; //Set the end tile to the last tile
        EnemyPath.put(new Vector2(End.x, End.y), End); //Add the end tile to the hashmap of enemies path
        gameData.GameMapPath = gameData.GameMapGraph.FindPath(Start, End); //Find the path from the start tile to the end tile and set it to the game data
    }


    public boolean IsTileAtPositionAValidBuildableTile(Vector2 vector) { //Check if the tile at the position is a valid buildable tile
        return BuildableTiles.containsKey(vector); //Return if the hashmap contains the tile
    }

    // Gets the next tile we should be heading to from the position of the current tile. e.g. input is Tile xy of A => output i


}
