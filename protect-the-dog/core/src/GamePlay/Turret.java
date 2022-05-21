package GamePlay;

import Entities.AnimatedSprite;
import com.badlogic.gdx.utils.Array;
import helper.Resources;

public class Turret extends AnimatedSprite
{
	public static Array<Turret> turrets = new Array<>();
	
	public Turret()
	{
		super(0, 0, 0, 0);
		setTexture(Resources.LoadTexture(""));
	}
	
	public static boolean AddTurret(int x, int y)
	{
		// substract money!
		
		return true;
	}
}
