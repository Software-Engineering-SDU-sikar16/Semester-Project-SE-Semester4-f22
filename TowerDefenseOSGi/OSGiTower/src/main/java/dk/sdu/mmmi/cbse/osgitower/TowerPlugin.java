package dk.sdu.mmmi.cbse.osgitower;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class TowerPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        world.CreateAllTowers(gameData);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.DisposeAllTowers(gameData);
    }
}
