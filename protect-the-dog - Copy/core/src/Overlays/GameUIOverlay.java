package Overlays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import helper.Constants;
import helper.DrawUtil;
import helper.FontResource;
import helper.Resources;

public class GameUIOverlay extends Overlay
{
	GlyphLayout GlyphLayouter = new GlyphLayout();
	
	ImageButton StartButton;
	
	
	Vector2 CenterPoint;
	
	
	Texture StartButtonTexture;
	
	
	
	@Override
	public void OnCreate()
	{
		StartButtonTexture = Resources.LoadTexture("ui/start_button.png");
		
		
		CenterPoint = new Vector2(Constants.GlobalWidth / 2, Constants.GlobalHeight / 2);
		Vector2 Pos = GetTextCenterPosition("\u0083", CenterPoint.x, CenterPoint.y);
		
		
		
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = Resources.LoadFont("fonts/GoogleIconsRounded.ttf", 32, FontResource.GetCharacterRangeFromTo(0, 150));
		textButtonStyle.fontColor = Color.WHITE;
		
		
		StartButton = new ImageButton(DrawUtil.GetDrawableByTexture(StartButtonTexture));
		StartButton.setVisible(true);
		StartButton.setWidth(StartButtonTexture.getWidth() * 1.5f);
		StartButton.setHeight(StartButtonTexture.getHeight() * 1.5f);
		StartButton.getImage().setFillParent(true);
		StartButton.setPosition(180, Constants.GlobalHeight - 48);
		StartButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				Constants.WaveManager.StartWave();
			}
			
			
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{
			
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
			
			}
			
		});
		
	
		
		Constants.Stage.addActor(StartButton);
		
		Constants.Health = new Health(20, Constants.GlobalHeight - 70);
		
		
	}
	
	
	public void OnRender()
	{
		Constants.batch.begin();
		
		DrawUtil.DrawText(Constants.batch, Constants.ScoreUIFont, "Wave " + Constants.WaveManager.GetWaveNumber() + " / " + Constants.WaveManager.GetTotalWaves(), 25, Constants.GlobalHeight - 20, Color.WHITE);
		
		DrawUtil.DrawText(Constants.batch, Constants.ScoreUIFontIcons, "\u0183", 25, Constants.GlobalHeight - 118, Color.WHITE);
		DrawUtil.DrawText(Constants.batch, Constants.ScoreUIFont, "" + Constants.Coins, 41, Constants.GlobalHeight - 99, Color.WHITE);
		
		
		Constants.batch.end();
	}
	
	//todo draw and make tooltip work on entities, and ui stuff.
	private void DrawToolTip()
	{
	
	}
	
	
	private Vector2 GetTextCenterPosition(String text, float x, float y)
	{
		GlyphLayouter.setText(Constants.BigPauseScreenFont, text);
		return new Vector2(x - GlyphLayouter.width / 2, y);
	}
	
	@Override
	public void OnUpdate(float DeltaTime)
	{
	
	}
	
	
}
