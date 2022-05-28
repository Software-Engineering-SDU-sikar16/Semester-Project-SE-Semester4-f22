package dk.sdu.mmmi.cbse.osgimap;


import com.badlogic.gdx.ai.pfa.GraphPath;
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
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import dk.sdu.mmmi.cbse.common.data.entityparts.AnimatedSpritePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SpriteLoaderPart;
import dk.sdu.mmmi.cbse.common.services.IMapService;

import java.util.HashMap;

public class MapService {

    public static TiledMap tiledMap;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    public Tile start = null;
    public Tile end = null;
    public static HashMap<Vector2, Tile> enemyPath = new HashMap<Vector2, Tile>();
    public static HashMap<Vector2, Tile> buildableTiles = new HashMap<Vector2, Tile>();
    public GameMapGraph gameMapGraph;
    public static GraphPath<Tile> gameMapPath;
    public final float PPM = 32.0f;
    public static AnimatedSpritePart MouseTileSelector;

    public void CalculateGraph()
    {
        gameMapGraph = new GameMapGraph();

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
                gameMapGraph.AddTile(graphTile);

                enemyPath.put(new Vector2(x, y), graphTile);

                if (start == null)
                {
                    start = graphTile;
                }

                if (previousTile != null)
                {
                    gameMapGraph.ConnectTiles(previousTile, graphTile);
                }
                previousTile = graphTile;

            }
        }

        end = previousTile;
        enemyPath.put(new Vector2(end.x, end.y), end);
        gameMapPath = gameMapGraph.FindPath(start, end);

    }

//    private void parseMapObjects(MapObjects mapObjects)
//    {
//        for (MapObject mapObject : mapObjects)
//        {
//            if (mapObject instanceof PolygonMapObject)
//            {
//                createStaticBody((PolygonMapObject) mapObject);
//            }
//
//
//            if (mapObject instanceof RectangleMapObject)
//            {
//                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
//                String rectangleName = mapObject.getName();
//
//                boolean findEnemy = rectangleName.equals("enemy");
//
//                if (findEnemy)
//                {
//                    Body body = BodyHelperService.createBody(
//                            rectangle.getX() + rectangle.getWidth() / 2,
//                            rectangle.getY() + rectangle.getHeight() / 2,
//                            rectangle.getWidth(),
//                            rectangle.getHeight(),
//                            false,
//                            gameScreen.getWorld());
//                    gameScreen.setEnemy(new Enemy(rectangle.getWidth(), rectangle.getHeight(), body));
//                }
//            }
//        }
//    }
//
//    private void createStaticBody(PolygonMapObject polygonMapObject)
//    {
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.StaticBody;
//        Body body = gameScreen.getWorld().createBody(bodyDef);
//        Shape shape = createPolygonShape(polygonMapObject);
//        body.createFixture(shape, 1000f);
//        System.out.println(body.getPosition());
//        shape.dispose();
//    }

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

    public Tile GetEnemyPathTileAt(Vector2 vector)
    {
        return enemyPath.get(vector);
    }

    public static boolean IsTileAtPositionAValidBuildableTile(Vector2 vector)
    {
        return buildableTiles.containsKey(vector);
    }

    public Tile GetEnemyPathTileAt(float x, float y)
    {
        return enemyPath.get(new Vector2(x, y));
    }

    // Gets the next tile we should be heading to from the position of the current tile. e.g. input is Tile xy of A => output is Tile B;
    public Tile GetNextEnemyPathTileFromCurrentTileAt(float x, float y)
    {
        Tile currentTile = enemyPath.get(new Vector2(x, y));

        if (gameMapPath.getCount() - 1 >= currentTile.index + 1)
        {
            Tile nextTile = gameMapPath.get(currentTile.index + 1);
            return nextTile;
        }
        else
        {
            return null;
        }

    }

}