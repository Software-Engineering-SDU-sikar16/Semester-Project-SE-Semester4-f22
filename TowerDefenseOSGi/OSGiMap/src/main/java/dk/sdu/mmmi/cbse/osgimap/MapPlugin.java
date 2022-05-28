package dk.sdu.mmmi.cbse.osgimap;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class MapPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        // call functionality to add the map to the game, so it is visible
        GameData.assetManager.setLoader(TiledMap.class, new TmxMapLoader());
        GameData.loadAsset("../assets/maps/map1.tmx", TiledMap.class);
        GameData.assetManager.finishLoading();
    }

    @Override
    public void stop(GameData gameData, World world) {
        // call functionality to remove the map from the game, so it is no longer visible
       // GameData.assetManager.unload("../assets/maps/map1.tmx");
    }
}
