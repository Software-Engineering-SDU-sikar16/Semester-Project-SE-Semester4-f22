package dk.sdu.mmmi.cbse.osgimap;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.AnimatedSpritePart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;


public class MapPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        // call functionality to add the map to the game, so it is visible
        gameData.assetManager.setLoader(TiledMap.class, new TmxMapLoader());
        gameData.loadAsset("../assets/maps/map1.tmx", TiledMap.class);
        gameData.assetManager.finishLoading();

        gameData.MouseTileSelector = new AnimatedSpritePart();
    }

    @Override
    public void stop(GameData gameData, World world) {
        // call functionality to remove the map from the game, so it is no longer visible
        gameData.assetManager.unload("../assets/maps/map1.tmx");
    }
}
