package dk.sdu.mmmi.cbse.common.data;

public class WaveSettings
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
