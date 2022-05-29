package dk.sdu.mmmi.cbse.osgimap;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class MapProcessingService implements  IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        if (gameData.orthogonalTiledMapRenderer != null) {
            gameData.camera.update();
            gameData.orthogonalTiledMapRenderer.setView(gameData.camera);
            gameData.orthogonalTiledMapRenderer.render();
        }
    }

}