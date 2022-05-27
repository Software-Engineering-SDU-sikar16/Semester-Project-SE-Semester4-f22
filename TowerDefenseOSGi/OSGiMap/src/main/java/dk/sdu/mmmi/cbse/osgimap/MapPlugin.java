package dk.sdu.mmmi.cbse.osgimap;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class MapPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {

        try {
            TmxMapLoader tmxMapLoader = new TmxMapLoader();
            gameData.tiledMap = tmxMapLoader.load("../assets/maps/map1.tmx");
            //timeout for 2 seconds before running the code
            Thread.sleep(2000);
            gameData.orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(gameData.tiledMap);
         } catch (Exception e) {
            System.out.println("Retrying to start plugin after failure");
        }

    }

    @Override
    public void stop(GameData gameData, World world) {
        //call functionality to remove the map from the game, so ti not visible anymore

        gameData.tiledMap = null;
        gameData.orthogonalTiledMapRenderer = null;

    }
}
