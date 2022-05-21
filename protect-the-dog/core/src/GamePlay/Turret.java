package GamePlay;

import Entities.AnimatedSprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import helper.MouseOperator;
import helper.Resources;

public class Turret extends AnimatedSprite
{
	
	public static Array<Turret> Turrets = new Array<Turret>();
	
	public Turret(int x, int y)
	{
		super(x, y, 50, 50);
		
		Texture turret1 = Resources.LoadTexture("turrets/4shot.png");
		setTexture(turret1);
		Turrets.add(this);
	}
	
	
	@Override
	public void OnUpdate(float DeltaTime)
	{
		super.OnUpdate(DeltaTime);
	}
}
