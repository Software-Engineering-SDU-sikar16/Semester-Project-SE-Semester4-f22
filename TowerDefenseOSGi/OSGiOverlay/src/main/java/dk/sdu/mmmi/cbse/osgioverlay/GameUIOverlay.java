package dk.sdu.mmmi.cbse.osgioverlay;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dk.sdu.mmmi.cbse.common.data.*;
import dk.sdu.mmmi.cbse.common.data.GamePlay.Health;
import dk.sdu.mmmi.cbse.common.data.helpers.DrawUtil;
import dk.sdu.mmmi.cbse.common.data.helpers.FontResource;

public class GameUIOverlay extends Overlay
{
	GlyphLayout GlyphLayouter = new GlyphLayout();
	
	ImageButton StartButton;
	
	
	Vector2 CenterPoint;
	
	
	Texture StartButtonTexture;
	
	
	
	private Vector2 GetTextCenterPosition(BitmapFont font, String text, float x, float y)
	{
		GlyphLayouter.setText(font, text);
		return new Vector2(x - GlyphLayouter.width / 2, y);
	}
	
	
	
	@Override
	public void OnCreate(GameData gameData, World world)
	{
		StartButtonTexture = Resources.LoadTexture("../assets/ui/start_button.png");
		
		
		CenterPoint = new Vector2(gameData.getGlobalWidth() / 2, gameData.getGlobalHeight() / 2);
		Vector2 Pos = GetTextCenterPosition(gameData.ScoreUIFontIcons, "\u0083", CenterPoint.x, CenterPoint.y);
		
		
		
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = Resources.LoadFont("../assets/fonts/GoogleIconsRounded.ttf", 32, FontResource.GetCharacterRangeFromTo(0, 150));
		textButtonStyle.fontColor = Color.WHITE;
		
		
		StartButton = new ImageButton(DrawUtil.GetDrawableByTexture(StartButtonTexture));
		StartButton.setVisible(true);
		StartButton.setWidth(StartButtonTexture.getWidth() * 1.5f);
		StartButton.setHeight(StartButtonTexture.getHeight() * 1.5f);
		StartButton.getImage().setFillParent(true);
		StartButton.setPosition(180, gameData.getGlobalHeight() - 48);
		StartButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				gameData.waveManager.StartWave();
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
		
		
		
		gameData.UIStage.addActor(StartButton);
		
		gameData.UIHealth = new Health(20, gameData.getGlobalHeight() - 70, 1, 7);
		gameData.UIHealth.OnCreate(gameData, world);
	}
	
	@Override
	public void OnRender(GameData gameData, World world)
	{
		gameData.GlobalSpriteBatch.begin();
		
		DrawUtil.DrawText(gameData.GlobalSpriteBatch, gameData.ScoreUIFont, "Wave " + gameData.waveManager.GetWaveNumber() + " / " + gameData.waveManager.GetTotalWaves(), 25, gameData.getGlobalHeight() - 20, Color.WHITE);
		
		DrawUtil.DrawText(gameData.GlobalSpriteBatch, gameData.ScoreUIFontIcons, "\u0183", 25, gameData.getGlobalHeight() - 118, Color.WHITE);
		DrawUtil.DrawText(gameData.GlobalSpriteBatch, gameData.ScoreUIFont, "" + gameData.Coins, 41, gameData.getGlobalHeight() - 99, Color.WHITE);
		
		gameData.UIHealth.OnRender(gameData, world);
		
		gameData.GlobalSpriteBatch.end();
	}
	
	@Override
	public void OnUpdate(GameData gameData, World world)
	{
		gameData.UIHealth.OnUpdate(gameData, world);
	}
	
	@Override
	public void OnDispose(GameData gameData, World world)
	{
		gameData.UIStage.getRoot().removeActor(StartButton);
	}
}