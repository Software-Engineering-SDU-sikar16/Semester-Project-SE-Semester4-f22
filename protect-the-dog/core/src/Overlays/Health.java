package Overlays;

import Entities.AnimatedSprite;
import com.badlogic.gdx.graphics.Texture;
import helper.Constants;
import helper.Resources;
import helper.Util;

public class Health extends AnimatedSprite
{
	AnimatedSprite[] hearts;
	Texture HeartTexture;
	Texture HeartTextureNull;
	
	int healthCounter = 7;
	
	public int GetHealth()
	{
		return  Util.Clamp( healthCounter,0, hearts.length - 1);
	}
	
	public Health()
	{
		super(20, Constants.GlobalHeight - 70, 0, 0);
		
		HeartTexture = Resources.LoadTexture("ui/hp.png");
		HeartTextureNull = Resources.LoadTexture("ui/hp_null.png");
		
		hearts = new AnimatedSprite[8];
		
		for (int i = 0; i < hearts.length; i++)
		{
			hearts[i] = new AnimatedSprite(HeartTexture, (30 * i) + 20, Constants.GlobalHeight - 85, (int) (HeartTexture.getWidth() * 1.6f), (int) (HeartTexture.getHeight() * 1.6f));
		}
		
	}
	
	
	public void SubstractHealth()
	{
		healthCounter--;
		healthCounter = Util.Clamp( healthCounter,0, hearts.length - 1);
		
		for (int i = healthCounter; i < hearts.length; i++)
		{
			hearts[i].setTexture(HeartTextureNull);
			hearts[i].setSize(HeartTextureNull.getWidth()  * 1.7f, HeartTextureNull.getHeight()  * 1.7f);
		}
	}
	
	public void AddHealth()
	{
		healthCounter = Util.Clamp( healthCounter,0, hearts.length - 1);
		healthCounter++;
		
		for (int i = 0; i < healthCounter; i++)
		{
			hearts[i].setTexture(HeartTexture);
			hearts[i].setTexture(HeartTexture);
			hearts[i].setSize(HeartTexture.getWidth()  * 1.6f , HeartTexture.getHeight()  * 1.6f);
		}
	}
}
