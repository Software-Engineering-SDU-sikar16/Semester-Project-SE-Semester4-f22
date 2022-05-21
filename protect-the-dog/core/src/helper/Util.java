package helper;

public class Util
{
	
	public static float Clamp(float value, float min, float max)
	{
		return  Math.max(min, Math.min(max, value));
	}
	
	public static int Clamp(int value, int min, int max)
	{
		return Math.max(min, Math.min(max, value));
	}
}
