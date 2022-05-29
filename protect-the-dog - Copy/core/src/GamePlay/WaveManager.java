package GamePlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import helper.Constants;

public class WaveManager
{
	
	private int SpawnedEnemies = 0;
	public float WaveSpawnSpeed = 1.5f; // lower numbers are faster
	float timeSinceLastSpawned = 0;
	int currentWaveIndex = 0;
	
	private WaveSettings GetWave()
	{
		currentWaveIndex = MathUtils.clamp(currentWaveIndex, 0, AllWaves.length);
		return AllWaves[currentWaveIndex];
	}
	
	private boolean isWaveStarted = false;
	
	public void StartWave()
	{
		isWaveStarted = true;
	}
	
	public int GetWaveNumber()
	{
		return currentWaveIndex;
	}
	
	public int GetTotalWaves()
	{
		return AllWaves.length;
	}
	
	private static class WaveSettings
	{
		EnemyEntity.EnemyType[] Types;
		
		public int MaxEnemies()
		{
			return Types.length;
		}
		
		public EnemyEntity.EnemyType GetEnemyTypeAtIndex(int index)
		{
			return Types[index];
		}
		
		public WaveSettings(EnemyEntity.EnemyType[] Types)
		{
			this.Types = Types;
		}
	}
	
	public static WaveSettings[] AllWaves = new WaveSettings[]
			{
					new WaveSettings(new EnemyEntity.EnemyType[]{
							EnemyEntity.EnemyType.kingkong,
							EnemyEntity.EnemyType.kingkong,
							EnemyEntity.EnemyType.kingkong,
							EnemyEntity.EnemyType.kingkong,
							EnemyEntity.EnemyType.kingkong,
							EnemyEntity.EnemyType.kingkong,
							EnemyEntity.EnemyType.kingkong,
							EnemyEntity.EnemyType.kingkong,
							EnemyEntity.EnemyType.kingkong,
							EnemyEntity.EnemyType.boss,
					}),
					
					new WaveSettings(new EnemyEntity.EnemyType[]{
							EnemyEntity.EnemyType.normal,
							EnemyEntity.EnemyType.normal,
							EnemyEntity.EnemyType.normal,
							EnemyEntity.EnemyType.normal,
							EnemyEntity.EnemyType.normal,
							EnemyEntity.EnemyType.normal,
							EnemyEntity.EnemyType.normal,
							EnemyEntity.EnemyType.boss,
							EnemyEntity.EnemyType.boss,
							EnemyEntity.EnemyType.boss,
					}),
			};
	
	
	public WaveManager()
	{
	
	}
	
	public void OnUpdate()
	{
		
		if (isWaveStarted)
		{
			
			if (SpawnedEnemies < GetWave().MaxEnemies())
			{
				timeSinceLastSpawned += Gdx.graphics.getDeltaTime();
				if (timeSinceLastSpawned > WaveSpawnSpeed)
				{
					Constants.EnemyManager.SpawnEnemy(GetWave().GetEnemyTypeAtIndex(SpawnedEnemies));
					timeSinceLastSpawned = 0;
					SpawnedEnemies++;
				}
			}
			else
			{
				//Wave Ended
				currentWaveIndex++;
				isWaveStarted = false;
				SpawnedEnemies = 0;
			}
		}
		
		
	}
}
