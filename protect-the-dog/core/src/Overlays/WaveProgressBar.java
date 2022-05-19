package Overlays;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import helper.DrawUtil;

public class WaveProgressBar extends ProgressBar
{
	
	public WaveProgressBar(Texture backgroundTexture, Texture knob, Texture knobBefore, float width, float height)
	{
		super(0f, 1f, 0.01f, false, new ProgressBarStyle());
		
		getStyle().background = DrawUtil.GetDrawableByTexture(backgroundTexture);
		getStyle().knob = DrawUtil.GetDrawableByTexture(knob);
		getStyle().knobBefore = DrawUtil.GetDrawableByTexture(knobBefore);
		
		getStyle().knob.setLeftWidth(20);
		getStyle().knob.setRightWidth(20);
		
		
		setWidth(width);
		setHeight(height);
		
		setAnimateDuration(0.0f);
		setValue(1f);
		
		setAnimateDuration(0.25f);
	}
}
