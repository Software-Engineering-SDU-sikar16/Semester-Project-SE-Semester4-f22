package dk.sdu.mmmi.cbse.osgimap;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import dk.sdu.mmmi.cbse.common.services.IMapService;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MapService implements IMapService {

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    @Override
    public void createMap() {
        // Finds the location of where the function looks for files
//        Path currentRelativePath = Paths.get("..");
//        String s = currentRelativePath.toAbsolutePath().toString();
//        System.out.println("Current absolute path is: " + s);

        tiledMap = new TmxMapLoader().load("../assets/maps/map1.tmx");
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    @Override
    public void updateMap(OrthographicCamera camera) {
        if (orthogonalTiledMapRenderer == null) {
            createMap();
        }
        orthogonalTiledMapRenderer.setView(camera);
        orthogonalTiledMapRenderer.render();
    }
    public GameMapGraph gameMapGraph;
    public void CalculateGraph()
    {
        Constants.GameMapGraph = new GameMapGraph();

        Tile previousTile = null;

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

                EnemyPath.put(new Vector2(x, y), graphTile);

                if (Start == null)
                {
                    Start = graphTile;
                }

                if (previousTile != null)
                {
                    Constants.GameMapGraph.ConnectTiles(previousTile, graphTile);
                }
                previousTile = graphTile;

            }
        }

        End = previousTile;
        EnemyPath.put(new Vector2(End.x, End.y), End);
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

    public Tile GetEnemyPathTileAt(Vector2 vector)
    {
        return EnemyPath.get(vector);
    }

    public boolean IsTileAtPositionAValidBuildableTile(Vector2 vector)
    {
        return BuildableTiles.containsKey(vector);
    }

    public Tile GetEnemyPathTileAt(float x, float y)
    {
        return EnemyPath.get(new Vector2(x, y));
    }

    // Gets the next tile we should be heading to from the position of the current tile. e.g. input is Tile xy of A => output is Tile B;
    public Tile GetNextEnemyPathTileFromCurrentTileAt(float x, float y)
    {
        Tile currentTile = EnemyPath.get(new Vector2(x, y));

        if (Constants.GameMapPath.getCount() - 1 >= currentTile.index + 1)
        {
            Tile nextTile = Constants.GameMapPath.get(currentTile.index + 1);
            return nextTile;
        }
        else
        {
            return null;
        }

    }

}