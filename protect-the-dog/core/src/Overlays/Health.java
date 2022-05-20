package Overlays;

import Entities.AnimatedSprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import helper.Constants;
import helper.Resources;

public class Health extends AnimatedSprite
{
	AnimatedSprite[] hearts;
	Texture HeartTexture;
	Texture HeartTextureNull;
	
	int health = 7;
	
	public Health()
	{
		super(20, Constants.GlobalHeight - 70, 0, 0);
		
		HeartTexture = Resources.LoadTexture("ui/hp.png");
		HeartTextureNull = Resources.LoadTexture("ui/hp_null.png");
		
		hearts = new AnimatedSprite[8];
		
		for (int i = 0; i < hearts.length; i++)
		{
			hearts[i] = new AnimatedSprite(HeartTexture, (30 * i) + 20, Constants.GlobalHeight - 85, (int) (HeartTexture.getWidth() * 1.7f), (int) (HeartTexture.getHeight() * 1.7f));
		}
		
	}
	
	@Override
	public void OnUpdate(float DeltaTime)
	{
		super.OnUpdate(DeltaTime);
		
		if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT))
		{
			SubstractHealth();
		}
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
		{
			AddHealth();
		}
		
	}
	
	public void SubstractHealth()
	{
		hearts[health].setTexture(HeartTextureNull);
		health--;
		
		if (health < 0)
		{
			health = 0;
		}
		else if (health > hearts.length - 1)
		{
			health = hearts.length - 1;
		}
		
		
	}
	
	public void AddHealth()
	{
		if (health < 0)
		{
			health = 0;
		}
		else if (health > hearts.length - 1)
		{
			health = hearts.length - 1;
		}
		health++;
		
		hearts[health].setTexture(HeartTexture);

		
		
		
	}
}
