package dk.sdu.mmmi.cbse.common.data.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GamePlay.Health;
import dk.sdu.mmmi.cbse.common.data.Resources;
import dk.sdu.mmmi.cbse.common.data.World;

public class HealthPart implements EntityPart
{
	Health health;
	
	public HealthPart(int x, int y, int paddingX, int startHealth)
	{
		health = new Health(x, y, paddingX, startHealth);
	}
	
	public HealthPart(int x, int y, int width, int height, int paddingX, int startHealth)
	{
		health = new Health(x, y,  width, height, paddingX, startHealth);
	}
	
	public Health GetHealth()
	{
		return health;
	}
	
	@Override
	public void OnCreate(GameData gameData, World world, Entity entity)
	{
		health = new Health(0, 0, 0, 0);
	}
	
	@Override
	public void OnUpdate(GameData gameData, World world, Entity entity)
	{
		health.setPosition(entity.getTransform().getX(), entity.getTransform().getY());
		health.OnUpdate(gameData, world);
	}
	
	@Override
	public void OnRender(GameData gameData, World world, Entity entity)
	{
		health.OnRender(gameData, world);
	}
	
	
}
