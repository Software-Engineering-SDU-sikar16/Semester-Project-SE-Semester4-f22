package dk.sdu.mmmi.cbse.osgienemy;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.*;
import dk.sdu.mmmi.cbse.common.data.entityparts.AnimatedSpritePart;

import java.util.Random;


public class Enemy extends Entity
{
	
	public Enemy(float x, float y, float width, float height, EnemyType Type)
	{
		setPosition(x, y);
		setSize(width, height);
		
		//	int randomSheetIndex = EnemyRandomNumberGenerator.nextInt(23 - 1);
		int randomSheetIndex = MathUtils.random(23 - 1);
		
		AnimatedSpritePart spritePart = new AnimatedSpritePart();
		spritePart.AddAnimation("default", Resources.LoadTexture("../assets/entities/enemies/sheet_" + randomSheetIndex + ".png"), 1, 4, 12, Animation.PlayMode.NORMAL);
		
		add(spritePart);
		
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
}
