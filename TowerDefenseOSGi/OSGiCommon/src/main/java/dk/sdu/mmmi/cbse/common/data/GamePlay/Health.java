package dk.sdu.mmmi.cbse.common.data.GamePlay;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.IEntity;
import dk.sdu.mmmi.cbse.common.data.IRenderable;
import dk.sdu.mmmi.cbse.common.data.World;

public class Health implements IRenderable
{
	Sprite[] hearts;
	int healthCounter = 0;
	private boolean Enabled = true;
	
	public int GetHealth()
	{
		return MathUtils.clamp(healthCounter, 0, hearts.length - 1);
	}
	
	public boolean IsDead()
	{
		return healthCounter <= 0;
	}
	
	private Vector2 position = new Vector2(0, 0);
	private Vector2 size = null;
	private int paddingX = 0;
	
	private Texture HeartTexture = null;
	private Texture HeartTextureNull = null;
	
	
	private void LoadHeartTextures(GameData gameData)
	{
		HeartTexture = gameData.assets.LoadTexture("../assets/ui/hp.png");
		HeartTextureNull = gameData.assets.LoadTexture("../assets/ui/hp_null.png");
	}
	
	
	public Health(int x, int y, int inputPaddingX, int health)
	{
		position = new Vector2(x, y);
		size = new Vector2(0, 0);
		hearts = new Sprite[health];
		
		paddingX = inputPaddingX;
		healthCounter = health;
	}
	
	public Health(int x, int y, int width, int height, int inputPaddingX, int health)
	{
		position = new Vector2(x, y);
		size = new Vector2(width, height);
		
		hearts = new Sprite[health];
		
		paddingX = inputPaddingX;
		healthCounter = health;
	}
	
	public void setSize(float width, float height)
	{
		size.set(width, height);
		
		if (hearts == null)
		{
			return;
		}
		for (int i = 0; i < hearts.length; i++)
		{
			hearts[i].setSize(width, height);
		}
	}
	
	public void setPosition(Vector2 pos)
	{
		position.set(pos);
		if (hearts == null)
		{
			return;
		}
		for (int i = 0; i < hearts.length; i++)
		{
			hearts[i].setPosition(pos.x, pos.y);
		}
	}
	
	public void setPosition(float x, float y)
	{
		position.set(x, y);
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
		healthCounter = MathUtils.clamp(healthCounter, 0, hearts.length - 1);
		
		for (int i = healthCounter; i < hearts.length; i++)
		{
			hearts[i].setTexture(HeartTextureNull);
			//hearts[i].setSize(HeartTextureNull.getWidth() * 1.7f, HeartTextureNull.getHeight() * 1.7f);
		}
	}
	
	public void AddHealth()
	{
		healthCounter = MathUtils.clamp(healthCounter, 0, hearts.length - 1);
		healthCounter++;
		
		for (int i = 0; i < healthCounter; i++)
		{
			hearts[i].setTexture(HeartTexture);
			hearts[i].setTexture(HeartTexture);
			//hearts[i].setSize(HeartTexture.getWidth() * 1.6f, HeartTexture.getHeight() * 1.6f);
		}
	}
	
	public void SetEnabled(boolean value)
	{
		this.Enabled = value;
	}
	
	@Override
	public void OnCreate(GameData gameData, World world)
	{
		LoadHeartTextures(gameData);
		if (size == null || size == Vector2.Zero)
		{
			size = new Vector2(HeartTexture.getWidth() * 1.6f, HeartTexture.getHeight() * 1.6f);
		}
		
		for (int i = 0; i < hearts.length; i++)
		{
			hearts[i] = new Sprite(HeartTexture, (paddingX * i) + (int) position.x, (int) position.y - 15, (int) size.x, (int) size.y);
		}
	}
	
	@Override
	public void OnRender(GameData gameData, World world)
	{
		if (Enabled)
		{
			for (int i = 0; i < hearts.length; i++)
			{
				gameData.GlobalSpriteBatch.draw(hearts[i].getTexture(), position.x, position.y, size.x, size.y);
//				hearts[i].draw(gameData.GlobalSpriteBatch);
			}
		}
	}
	
	@Override
	public void OnUpdate(GameData gameData, World world)
	{
	}
	
	@Override
	public void OnDispose(GameData gameData, World world)
	{
	/*	HeartTexture = null;
		HeartTextureNull = null;*/
	}
}
