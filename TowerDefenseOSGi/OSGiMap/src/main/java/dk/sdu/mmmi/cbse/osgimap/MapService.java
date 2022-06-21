package dk.sdu.mmmi.cbse.osgimap;


import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.cbse.common.data.Map.GameMapGraph;
import dk.sdu.mmmi.cbse.common.data.Map.Tile;

import java.util.HashMap;

public class MapService {

    public static TiledMap tiledMap;
    public static HashMap<Vector2, Tile> enemyPath = new HashMap<Vector2, Tile>(); // This is the path that the enemy will follow.
    public static HashMap<Vector2, Tile> buildableTiles = new HashMap<Vector2, Tile>(); // This is a hashmap of all the tiles that are buildable.
    public static GraphPath<Tile> gameMapPath; 
    public final float PPM = 32.0f; // Pixels per meter
    public Tile start = null; // The start tile.
    public Tile end = null; // The end tile
    public GameMapGraph gameMapGraph; // This is the graph we will use to find the path.
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer; // This is the renderer for the map.

    public static boolean IsTileAtPositionAValidBuildableTile(Vector2 vector) {
        return buildableTiles.containsKey(vector); // Returns true if the tile is a valid buildable tile.
    }

    public void CalculateGraph() {
        gameMapGraph = new GameMapGraph(); // We create a new GameMapGraph object.
        Tile previousTile = null; // The previous tile.
        MapLayer layer = tiledMap.getLayers().get("PathLayer"); // We get the layer with the name "PathLayer".
        MapObjects mapObjects = layer.getObjects(); // Get all objects in the layer.

        for (MapObject mapObject : mapObjects) { // For each object in the layer.
            if (mapObject instanceof RectangleMapObject) { // If the object is a rectangle.
                RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject; // We get the rectangle object.
                // now you can get the position of the rectangle like this:
                Rectangle rectangle = rectangleMapObject.getRectangle(); // We get the rectangle.
                int x = (int) rectangle.getX(); // We get the x position.
                int y = (int) rectangle.getY(); // We get the y position.

                // create a graph tile for each object.
                Tile graphTile = new Tile(x, y, rectangleMapObject.getName()); // We create a new graph tile.
                gameMapGraph.AddTile(graphTile); // We add the graph tile to the graph.

                enemyPath.put(new Vector2(x, y), graphTile); // We add the graph tile to the enemy path.

                if (start == null) { // If the start is null, we set the start to the graph tile.
                    start = graphTile;
                }

                if (previousTile != null) { // If the previous tile is not null, we connect the previous tile to the graph tile.
                    gameMapGraph.ConnectTiles(previousTile, graphTile);
                }
                previousTile = graphTile; // We set the previous tile to the graph tile.

            }
        }

        end = previousTile; // We set the end to the previous tile.
        enemyPath.put(new Vector2(end.x, end.y), end); // We add the end to the enemy path.
        gameMapPath = gameMapGraph.FindPath(start, end); // We find the path.
    }

    public Tile GetEnemyPathTileAt(Vector2 vector) {
        return enemyPath.get(vector); // We get the enemy path tile at the vector.
    }

    public Tile GetEnemyPathTileAt(float x, float y) {
        return enemyPath.get(new Vector2(x, y)); // We get the enemy path tile at the vector.
    }

    // Gets the next tile we should be heading to from the position of the current tile. e.g. input is Tile xy of A => output is Tile B;
    public Tile GetNextEnemyPathTileFromCurrentTileAt(float x, float y) { // We get the next enemy path tile from the current tile at the vector.
        Tile currentTile = enemyPath.get(new Vector2(x, y)); // We get the current tile.

        if (gameMapPath.getCount() - 1 >= currentTile.index + 1) { // If the current tile is not the last tile in the path.
            Tile nextTile = gameMapPath.get(currentTile.index + 1); // We get the next tile.
            return nextTile; // We return the next tile.
        } else { 
            return null; // We return null.
        }

    }

}