package dk.sdu.mmmi.cbse.common.data;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

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
		EnemyType[] Types;
		
		public int MaxEnemies()
		{
			return Types.length;
		}
		
		public EnemyType GetEnemyTypeAtIndex(int index)
		{
			return Types[index];
		}
		
		public WaveSettings(EnemyType[] Types)
		{
			this.Types = Types;
		}
	}
	
	public static WaveSettings[] AllWaves = new WaveSettings[]
			{
					new WaveSettings(new EnemyType[]{
							EnemyType.normal,
							EnemyType.normal,
							EnemyType.normal,
							EnemyType.normal,
							EnemyType.normal,
							EnemyType.normal,
							EnemyType.normal,
							EnemyType.normal,
							EnemyType.normal,
							EnemyType.boss,
					}),
					
					new WaveSettings(new EnemyType[]{
							EnemyType.normal,
							EnemyType.normal,
							EnemyType.normal,
							EnemyType.normal,
							EnemyType.normal,
							EnemyType.normal,
							EnemyType.normal,
							EnemyType.boss,
							EnemyType.boss,
							EnemyType.boss,
					}),
			};
	
	
	public WaveManager()
	{
	
	}
	
	public void OnUpdate(GameData gameData)
	{
		
		if (isWaveStarted)
		{
			
			if (SpawnedEnemies < GetWave().MaxEnemies())
			{
				timeSinceLastSpawned += Gdx.graphics.getDeltaTime();
				if (timeSinceLastSpawned > WaveSpawnSpeed)
				{
				//	gameData.EnemyManager.SpawnEnemy(GetWave().GetEnemyTypeAtIndex(SpawnedEnemies));
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

