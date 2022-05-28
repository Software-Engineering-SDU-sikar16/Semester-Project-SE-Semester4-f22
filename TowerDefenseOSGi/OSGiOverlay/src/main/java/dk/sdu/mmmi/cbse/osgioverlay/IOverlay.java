package dk.sdu.mmmi.cbse.osgioverlay;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IOverlay
{
	void OnCreate(GameData gameData, World world);
	void OnRender(GameData gameData, World world);
	void OnUpdate(GameData gameData, World world);
	
	void OnDispose(GameData gameData, World world);
}
