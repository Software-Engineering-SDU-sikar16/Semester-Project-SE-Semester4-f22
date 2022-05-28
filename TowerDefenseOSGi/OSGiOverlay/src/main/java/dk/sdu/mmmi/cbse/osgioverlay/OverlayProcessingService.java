package dk.sdu.mmmi.cbse.osgioverlay;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class OverlayProcessingService implements IEntityProcessingService
{
	@Override
	public void process(GameData gameData, World world)
	{
		System.out.println("habib");
		Overlay.UpdateAllOverlays(gameData, world);
		Overlay.RenderAllOverlays(gameData, world);
		
	}
}
