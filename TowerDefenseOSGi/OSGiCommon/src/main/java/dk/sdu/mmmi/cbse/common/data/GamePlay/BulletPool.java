package dk.sdu.mmmi.cbse.common.data.GamePlay;

import com.badlogic.gdx.utils.Pool;
import dk.sdu.mmmi.cbse.common.data.Bullets.Bullet;

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
