package dk.sdu.mmmi.cbse.osgienemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SpriteLoaderPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {

    private String enemyID;

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        Entity enemy = createEnemy(gameData);
        enemyID = world.addEntity(enemy);
    }

    private Entity createEnemy(GameData gameData) {
        Entity enemyThing = new Enemy();
        float deceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 3f;
        float y = gameData.getDisplayHeight() / 3f;
        float radians = 3.1415f / 2;
        enemyThing.add(new LifePart(3));
        enemyThing.setRadius(4);
        enemyThing.add(new MovingPart(deceleration, acceleration, maxSpeed, rotationSpeed));
        enemyThing.add(new PositionPart(x, y, radians));
        SpriteLoaderPart spriteLoaderPart = new SpriteLoaderPart("images/v3.png", 2*16, 9*16, 16, 16, false, false);
        enemyThing.add(spriteLoaderPart);
        return enemyThing;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemyID);
    }

}
