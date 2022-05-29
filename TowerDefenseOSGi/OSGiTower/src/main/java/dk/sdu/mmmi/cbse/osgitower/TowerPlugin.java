package dk.sdu.mmmi.cbse.osgitower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.components.AnimatedSpritePart;
import dk.sdu.mmmi.cbse.common.data.components.LifePart;
import dk.sdu.mmmi.cbse.common.data.components.PositionPart;
import dk.sdu.mmmi.cbse.common.data.components.SpriteLoaderPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.osgimap.MapService;
import dk.sdu.mmmi.cbse.osgimap.helper.MouseOperator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TowerPlugin implements IGamePluginService
{
	@Override
	public void start(GameData gameData, World world)
	{
		world.CreateAllTowers(gameData);
	}
	
	@Override
	public void stop(GameData gameData, World world)
	{
		world.DisposeAllTowers(gameData);
	}
}
