package Overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;


public abstract class Overlay implements IOverlay
{
	
	public static Array<IOverlay> Overlays = new Array<IOverlay>();
	
	public Overlay()
	{
		Overlays.add(this);
	}
	
	public static void RenderAllOverlays()
	{
		for (IOverlay overlay : Overlays)
		{
			overlay.OnRender();
		}
	}
	
	public static void CreateAllOverlays()
	{
		for (IOverlay overlay : Overlays)
		{
			overlay.OnCreate();
		}
		
	}
	
	public static void UpdateAllOverlays()
	{
		for (IOverlay overlay : Overlays)
		{
			overlay.OnUpdate(Gdx.graphics.getDeltaTime());
		}
	}
}
