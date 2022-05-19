package dk.sdu.mmmi.cbse.osgienemy;

import com.badlogic.gdx.physics.box2d.Body;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SpriteLoaderPart;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {
    private String enemyID;

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        Entity enemy = createEnemyShip(gameData);
        //EnemyEntity = ee = new EnemyEntity(3, 3, new Body())
        enemyID = world.addEntity(enemy);
        
    }

    private Entity createEnemyShip(GameData gameData) {
        Entity enemyShip = new Enemy();

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 3;
        float y = gameData.getDisplayHeight() / 3;
        float radians = 3.1415f / 2;
        enemyShip.add(new LifePart(3));
        enemyShip.setRadius(4);
        enemyShip.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        enemyShip.add(new PositionPart(x, y, radians));
        SpriteLoaderPart spriteLoaderPart = new SpriteLoaderPart("images/v3.png", 0, 192, 16, 16, true, true);
        enemyShip.add(spriteLoaderPart);
        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemyID);
    }

}
