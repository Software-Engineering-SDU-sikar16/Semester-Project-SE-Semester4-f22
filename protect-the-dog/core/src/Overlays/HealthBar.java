package Overlays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import helper.DrawUtil;

public class HealthBar extends ProgressBar
{
	
	public HealthBar(int width, int height)
	{
		super(0f, 1f, 0.01f, false, new ProgressBarStyle());
		getStyle().background = DrawUtil.GetColoredDrawable(width, height, Color.RED);
		getStyle().knob = DrawUtil.GetColoredDrawable(0, height, Color.GREEN);
		getStyle().knobBefore = DrawUtil.GetColoredDrawable(width, height, Color.GREEN);
		
		setWidth(width);
		setHeight(height);
		
		setAnimateDuration(0.0f);
		setValue(1f);
		
		setAnimateDuration(0.25f);
	}
}
