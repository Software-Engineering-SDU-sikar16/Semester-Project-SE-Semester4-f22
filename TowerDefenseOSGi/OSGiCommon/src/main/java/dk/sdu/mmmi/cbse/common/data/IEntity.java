package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IEntity
{
	void OnCreate(GameData gameData, World world);
	
	void OnRender(GameData gameData, World world);
	
	void OnUpdate(GameData gameData, World world);
}
