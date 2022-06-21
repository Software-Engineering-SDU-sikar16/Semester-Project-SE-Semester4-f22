package dk.sdu.mmmi.cbse.osgienemy;

import com.badlogic.gdx.Gdx;
import dk.sdu.mmmi.cbse.common.data.EnemyType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entities.Enemy;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class EnemyProcessingService implements IEntityProcessingService {


    public float WaveSpawnSpeed = 1.5f; // lower numbers are faster
    float timeSinceLastSpawned = 0;
    private int SpawnedEnemies = 0;

    @Override
    public void process(GameData gameData, World world) {

        if (gameData.IsWaveStarted) {
            if (SpawnedEnemies < gameData.GetWave().MaxEnemies()) {
                timeSinceLastSpawned += Gdx.graphics.getDeltaTime();
                if (timeSinceLastSpawned > WaveSpawnSpeed) {
                    SpawnEnemy(gameData, world, gameData.GetWave().GetEnemyTypeAtIndex(SpawnedEnemies));
                    timeSinceLastSpawned = 0;
                    SpawnedEnemies++;
                }
            } else {
                //Wave Ended
                gameData.currentWaveIndex++;
                gameData.IsWaveStarted = false;
                SpawnedEnemies = 0;
            }
        }


    }

    private void SpawnEnemy(GameData gameData, World world, EnemyType type) {
        Enemy enemy = new Enemy((int) gameData.EnemiesSpawnPosition.x, (int) gameData.EnemiesSpawnPosition.y, 24, 24, type);

        enemy.getTransform().setPosition(gameData.EnemiesSpawnPosition);
        enemy.SetEnabled(true);
        world.addEntity(enemy);
    }
}
