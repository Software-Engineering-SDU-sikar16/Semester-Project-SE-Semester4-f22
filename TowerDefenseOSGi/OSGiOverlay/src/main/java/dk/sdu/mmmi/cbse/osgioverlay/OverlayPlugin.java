package dk.sdu.mmmi.cbse.osgioverlay;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Resources;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class OverlayPlugin implements IGamePluginService
{
	@Override
	public void start(GameData gameData, World world)
	{
		//call functionality to add the map to the game, so it is visible
		
		Resources.LoadAllAssets(gameData);
		System.out.println("loaded all assets");
		new PauseScreen();
		System.out.println("new pause screeen");
		Overlay.CreateAllOverlays(gameData, world);
		System.out.println("create all overlayas");
	}
	
	@Override
	public void stop(GameData gameData, World world)
	{
		//call functionality to remove the map from the game, so it is no longer visible
		
		Overlay.DisposeAllOverlays(gameData, world);
		
	}
}
