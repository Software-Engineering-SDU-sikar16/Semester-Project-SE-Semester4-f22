package dk.sdu.mmmi.cbse.common.data;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.common.data.*;
import dk.sdu.mmmi.cbse.common.data.GamePlay.Health;
import dk.sdu.mmmi.cbse.common.data.components.AnimatedSpritePart;
import dk.sdu.mmmi.cbse.common.data.components.HealthPart;
import dk.sdu.mmmi.cbse.common.data.components.TileIndexPart;


public class Enemy extends Entity
{
	HealthPart healthPart;
	TileIndexPart tileIndexPart;
	
	private EnemyType Type = EnemyType.normal;
	
	
	public boolean IsDead()
	{
		return healthPart.GetHealth().IsDead();
	}
	
	public Enemy(int x, int y, int width, int height, EnemyType Type)
	{
		setPosition(x, y);
		setSize(width, height);
		
		//	int randomSheetIndex = EnemyRandomNumberGenerator.nextInt(23 - 1);
		int randomSheetIndex = MathUtils.random(23 - 1);
		
		AnimatedSpritePart spritePart = new AnimatedSpritePart(x, y, width, height);
		spritePart.AddAnimation("default", Resources.LoadTexture("../assets/entities/enemies/sheet_" + randomSheetIndex + ".png"), 1, 4, 12, Animation.PlayMode.NORMAL);
		
		setTexture(Resources.LoadTexture("../assets/entities/enemies/sheet_" + randomSheetIndex + ".png"));
		
		this.Type = Type;
		if (Type == EnemyType.normal)
		{
			healthPart = new HealthPart(x + 6, y + height + 18, width / 2, height / 2, 0, 1);
		}
		else if (Type == EnemyType.boss)
		{
			healthPart = new HealthPart(x + 4, y + height + 18, width / 2, height / 2, 5, 2);
		}
		else if (Type == EnemyType.superboss)
		{
			healthPart = new HealthPart(x + 2, y + height + 18, width / 2, height / 2, 5, 3);
		}
		else if (Type == EnemyType.elite)
		{
			healthPart = new HealthPart(x, y + height + 18, width / 2, height / 2, 5, 4);
		}
		else if (Type == EnemyType.kingkong)
		{
			healthPart = new HealthPart(x - 3, y + height + 18, width / 2, height / 2, 5, 5);
		}
		
		add(spritePart);
		add(healthPart);
		
		
		tileIndexPart = new TileIndexPart();
		add(tileIndexPart);
		
	}
	
	
	@Override
	public void OnCreate(GameData gameData, World world)
	{
	
	}
	
	@Override
	public void OnRender(GameData gameData, World world)
	{
	
	}
	
	@Override
	public void OnUpdate(GameData gameData, World world)
	{
	
	}
	
	@Override
	public void OnDispose(GameData gameData, World world)
	{
	
	}
}
