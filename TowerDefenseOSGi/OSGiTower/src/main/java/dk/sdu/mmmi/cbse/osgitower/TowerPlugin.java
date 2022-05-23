package dk.sdu.mmmi.cbse.osgitower;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SpriteLoaderPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class TowerPlugin implements IGamePluginService {

    private String towerID;

    public TowerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        Entity tower = createTower(gameData);
        towerID = world.addEntity(tower);
    }

    private Entity createTower(GameData gameData) {
        Entity towerThing = new Tower();
        float deceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 5;
//        float x = gameData.getDisplayWidth() / 3f;
        float x = 0;
//        float y = gameData.getDisplayHeight() / 3f;
        float y = 200;
        float radians = 3.1415f / 2;
        towerThing.add(new LifePart(3));
        towerThing.setRadius(4);
        towerThing.add(new MovingPart(deceleration, acceleration, maxSpeed, rotationSpeed));
        towerThing.add(new PositionPart(x, y, radians));
        SpriteLoaderPart spriteLoaderPart = new SpriteLoaderPart("images/v3.png", 0*16, 12*16, 16, 16, false, false);
        towerThing.add(spriteLoaderPart);
        return towerThing;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(towerID);
    }

}
