package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IGamePluginService { // Interface for the game plugin service
    void start(GameData gameData, World world); // Functionality to start the plugin (called when the game starts)

    void stop(GameData gameData, World world); // Functionality to stop the plugin (unload assets)
}
