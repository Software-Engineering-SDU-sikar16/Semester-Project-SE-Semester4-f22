package dk.sdu.mmmi.cbse.osgimap;


import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class MapProcessingService implements  IEntityProcessingService {








    @Override
    public void process(GameData gameData, World world) {
        if (gameData.orthogonalTiledMapRenderer != null) {
            gameData.orthogonalTiledMapRenderer.setView(gameData.camera);
            gameData.orthogonalTiledMapRenderer.render();
        }

    }

//    public void CalculateGraph()
//    {
//        Constants.GameMapGraph = new GameMapGraph();
//
//        Tile previousTile = null;
//
//        MapLayer layer = tiledMap.getLayers().get("PathLayer");
//
//        MapObjects mapObjects = layer.getObjects();
//
//        for (MapObject mapObject : mapObjects)
//        {
//            if (mapObject instanceof RectangleMapObject)
//            {
//                RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
//                // now you can get the position of the rectangle like this:
//                Rectangle rectangle = rectangleMapObject.getRectangle();
//                int x = (int) rectangle.getX();
//                int y = (int) rectangle.getY();
//
//                // create a graph tile for each object.
//                Tile graphTile = new Tile(x, y, rectangleMapObject.getName());
//                Constants.GameMapGraph.AddTile(graphTile);
//
//                EnemyPath.put(new Vector2(x, y), graphTile);
//
//                if (Start == null)
//                {
//                    Start = graphTile;
//                }
//
//                if (previousTile != null)
//                {
//                    Constants.GameMapGraph.ConnectTiles(previousTile, graphTile);
//                }
//                previousTile = graphTile;
//
//            }
//        }
//
//        End = previousTile;
//        EnemyPath.put(new Vector2(End.x, End.y), End);
//        Constants.GameMapPath = Constants.GameMapGraph.FindPath(Start, End);
//
//    }

}