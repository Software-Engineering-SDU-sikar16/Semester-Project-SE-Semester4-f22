package Overlays;

import Entities.AnimatedSprite;
import Entities.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import helper.Resources;
import helper.Util;

public class Health extends Entity
{
	AnimatedSprite[] hearts;
	public static Texture HeartTexture = Resources.LoadTexture("ui/hp.png");
	
	public static Texture HeartTextureNull = Resources.LoadTexture("ui/hp_null.png");
	
	
	int healthCounter = 0;
	
	public int GetHealth()
	{
		return Util.Clamp(healthCounter, 0, hearts.length - 1);
	}
	
	public boolean IsDead()
	{
		return healthCounter <= 0;
	}
	
	public Health(int x, int y)
	{
		super(x, y, 0, 0);
		
		hearts = new AnimatedSprite[8];
		
		for (int i = 0; i < hearts.length; i++)
		{
			hearts[i] = new AnimatedSprite(HeartTexture, (30 * i) + x, y - 15, (int) (HeartTexture.getWidth() * 1.6f), (int) (HeartTexture.getHeight() * 1.6f));
		}
		
		healthCounter = hearts.length - 1;
		
	}
	
	public Health(int x, int y, int width, int height, int health)
	{
		super(x, y, width, height);
		
		hearts = new AnimatedSprite[health];
		
		for (int i = 0; i < hearts.length; i++)
		{
			hearts[i] = new AnimatedSprite(HeartTexture, (30 * i) + x, y - 15, width, height);
		}
		
		healthCounter = health;
	}
	
	public Health(int x, int y, int width, int height, int paddingX, int health)
	{
		super(x, y, width, height);
		
		hearts = new AnimatedSprite[health];
		
		for (int i = 0; i < hearts.length; i++)
		{
			hearts[i] = new AnimatedSprite(HeartTexture, (paddingX * i) + x, y - 15, width, height);
		}
		
		healthCounter = health;
	}
	
	
	@Override
	public void setSize(float width, float height)
	{
		super.setSize(width, height);
		if (hearts == null)
		{
			return;
		}
		for (int i = 0; i < hearts.length; i++)
		{
			
			hearts[i].setSize(width, height);
		}
	}
	
	
	@Override
	public void setPosition(Vector2 pos)
	{
		super.setPosition(pos);
		if (hearts == null)
		{
			return;
		}
		for (int i = 0; i < hearts.length; i++)
		{
			hearts[i].setPosition(pos.x, pos.y);
		}
	}
	
	@Override
	public void setPosition(float x, float y)
	{
		super.setPosition(x, y);
		if (hearts == null)
		{
			return;
		}
		for (int i = 0; i < hearts.length; i++)
		{
			hearts[i].setPosition(x, y);
		}
	}
	
	public void SubstractHealth()
	{
		healthCounter--;
		healthCounter = Util.Clamp(healthCounter, 0, hearts.length - 1);
		
		for (int i = healthCounter; i < hearts.length; i++)
		{
			hearts[i].setTexture(HeartTextureNull);
			//hearts[i].setSize(HeartTextureNull.getWidth() * 1.7f, HeartTextureNull.getHeight() * 1.7f);
		}
	}
	
	public void AddHealth()
	{
		healthCounter = Util.Clamp(healthCounter, 0, hearts.length - 1);
		healthCounter++;
		
		for (int i = 0; i < healthCounter; i++)
		{
			hearts[i].setTexture(HeartTexture);
			hearts[i].setTexture(HeartTexture);
		//hearts[i].setSize(HeartTexture.getWidth() * 1.6f, HeartTexture.getHeight() * 1.6f);
		}
	}
	
	
	@Override
	public void SetEnabled(boolean value)
	{
		super.SetEnabled(value);
		
		for (int i = 0; i < healthCounter; i++)
		{
			hearts[i].SetEnabled(value);
		}
	}
	
	@Override
	public void OnCreate()
	{
	
	}
	
	@Override
	public void OnRender()
	{
	
	}
	
	@Override
	public void OnUpdate(float DeltaTime)
	{
	
	}
}
