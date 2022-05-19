package Overlays;

import helper.Constants;

public abstract class Overlay implements IOverlay
{
	public Overlay()
	{
		Constants.Overlays.add(this);
	}
}
