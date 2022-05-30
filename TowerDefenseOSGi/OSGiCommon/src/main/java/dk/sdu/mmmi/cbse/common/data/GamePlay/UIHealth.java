package dk.sdu.mmmi.cbse.common.data.GamePlay;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.cbse.common.data.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public class UIHealth implements IRenderable
{
	Texture[] hearts;
	int healthCounter = 0;

	private Vector2 position = new Vector2(0, 0);
	private Vector2 size = null;
	private int paddingX = 0;
	
	public static Texture HeartTexture = Resources.LoadTexture("../assets/ui/hp.png");
	public static Texture HeartTextureNull = Resources.LoadTexture("../assets/ui/hp_null.png");
	
	
	public UIHealth(int x, int y, int inputPaddingX, int health)
	{
		
		position = new Vector2(x, y);
		size = new Vector2(HeartTexture.getWidth(), HeartTexture.getHeight());
		hearts = new Texture[health];
		
		paddingX = inputPaddingX;
		healthCounter = health;
	}
	
	

	public void SubstractHealth()
	{
		healthCounter--;
		healthCounter = MathUtils.clamp(healthCounter, 0, hearts.length - 1);
		
		for (int i = healthCounter; i < hearts.length; i++)
		{
			hearts[i] = HeartTextureNull;
		}
	}
	
	
	
	@Override
	public void OnCreate(GameData gameData, World world)
	{
		if (size == null || size == Vector2.Zero)
		{
			size = new Vector2(HeartTexture.getWidth() * 1.6f, HeartTexture.getHeight() * 1.6f);
		}
		
		for (int i = 0; i < hearts.length; i++)
		{
			hearts[i] = HeartTexture;
		}
	}
	
	@Override
	public void OnRender(GameData gameData, World world)
	{
		gameData.GlobalSpriteBatch.begin();
		for (int i = 0; i < hearts.length; i++)
		{
			gameData.GlobalSpriteBatch.draw(hearts[i], (paddingX * i) + (int) position.x, position.y - 15, size.x, size.y);
		}
		gameData.GlobalSpriteBatch.end();
	}
	
	@Override
	public void OnUpdate(GameData gameData, World world)
	{
	}
	
	@Override
	public void OnDispose(GameData gameData, World world)
	{
	}
	
	public int GetHealth()
	{
		return MathUtils.clamp(healthCounter, 0, hearts.length - 1);
	}
}
