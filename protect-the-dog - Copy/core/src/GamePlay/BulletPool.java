package GamePlay;

import com.badlogic.gdx.utils.Pool;

public class BulletPool extends Pool<Bullet>
{
	
	public BulletPool(int init, int max)
	{
		super(init, max);
	}
	
	
	@Override
	protected Bullet newObject()
	{
		return new Bullet();
	}
	
}
