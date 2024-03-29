package dk.sdu.mmmi.cbse.osgitower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.components.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.components.HealthPart;
import dk.sdu.mmmi.cbse.common.data.entities.Tower;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class TowerProcessingService implements IEntityProcessingService {
    public static void TryBuildTurret(GameData gameData, World world) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 TilePos = gameData.mouseOperator.GetTilePositionUnderMousePosition(gameData);
            boolean availableTile = gameData.TileMapHelper.IsTileAtPositionAValidBuildableTile(TilePos);
            boolean hasEnoughMoney = gameData.Coins - gameData.TurretPriceInCoins >= 0;
            boolean hasEnoughSpace = !world.turretPositions.containsKey(TilePos);
            if (availableTile && hasEnoughMoney && hasEnoughSpace) {
                gameData.Coins -= gameData.TurretPriceInCoins;
                world.addTower((int) TilePos.x, (int) TilePos.y);
            } else return;
        }
    }

    @Override
    public void process(GameData gameData, World world) {
        world.ProcessTowers(gameData);
        TryBuildTurret(gameData, world);

        if (gameData.enemyQuadTree != null) {
            world.getEntities(Tower.class).forEach(tower -> {
                ColliderPart colliderPart = tower.getPart(ColliderPart.class);
                if (colliderPart != null) {
                    Array<Entity> hitEntities = gameData.enemyQuadTree.Query(colliderPart.getShape("hitRect").getRect());
                    for (Entity enemy : hitEntities) {
                        HealthPart healthPart = enemy.getPart(HealthPart.class);
                        if (healthPart != null) {
                            if (!healthPart.IsDead()) {
                                tower.OnCollision(gameData, world, enemy);
                            }
                        }
                    }
                }

            });

        }

/*		Turret.TryBuildTurret();
		// update turrets
		for (
				Turret turret : Turret.Turrets)

		{
			//use quad tree for better performance.
			Array<EnemyEntity> hitEntities = gameData.enemyQuadTree.Query(turret.hitRect);
			for (EnemyEntity enemy : hitEntities)
			{
				if (enemy.IsDead())
				{
					continue;
				}
				turret.EnemyIsClose(enemy);
			}
		}


		Tower.UpdateAllTowers(gameData, world);
		Tower.RenderAllTowers(gameData, world);*/
    }
}
