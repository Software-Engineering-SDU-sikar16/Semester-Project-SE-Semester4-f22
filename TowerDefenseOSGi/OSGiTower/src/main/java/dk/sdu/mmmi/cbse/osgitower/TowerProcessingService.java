package dk.sdu.mmmi.cbse.osgitower;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.SpriteLoaderPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class TowerProcessingService implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity tower : world.getEntities(Tower.class)) {
        }
    }
}
