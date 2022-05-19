package Overlays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
	
	TextButton[] Buttons = new TextButton[1];
	ImageButton[] MenuButtons = new ImageButton[6];
	
	
	Vector2 CenterPoint;
	
	
	Texture ButtonBKGTexture;
	Texture UIMenuBKGTexture;
	Texture ScoreBKGTexture;
	
	Sprite ScoreUI;
	Sprite PlayPauseButtonBKG;
	Sprite UIMenu;
	
	
	@Override
	public void OnCreate()
	{
		
		
		ButtonBKGTexture = Resources.LoadTexture("ui/UI_BUTTON_BKG.png");
		UIMenuBKGTexture = Resources.LoadTexture("ui/UI_MENU.png");
		ScoreBKGTexture = Resources.LoadTexture("ui/UI_TOP_SCORE.png");
		
		
		CenterPoint = new Vector2(Constants.GlobalWidth / 2, Constants.GlobalHeight / 2);
		Vector2 Pos = GetTextCenterPosition("\u0083", CenterPoint.x, CenterPoint.y);
		
		ScoreUI = new Sprite(ScoreBKGTexture);
		ScoreUI.setSize(ScoreBKGTexture.getWidth() / 7, ScoreBKGTexture.getHeight() / 7);
		ScoreUI.setPosition(Constants.GlobalWidth - ScoreUI.getWidth() - 20, Constants.GlobalHeight - ScoreUI.getHeight() - 20);
		
		UIMenu = new Sprite(UIMenuBKGTexture);
		UIMenu.setSize(UIMenuBKGTexture.getWidth() / 5, UIMenuBKGTexture.getHeight() / 5);
		UIMenu.setPosition(Constants.GlobalWidth / 2 - ScoreUI.getWidth() / 2 - 40, 20);
		
		
		PlayPauseButtonBKG = new Sprite(ButtonBKGTexture);
		PlayPauseButtonBKG.setSize(ButtonBKGTexture.getWidth() / 10, ButtonBKGTexture.getHeight() / 10);
		PlayPauseButtonBKG.setPosition(Constants.GlobalWidth - PlayPauseButtonBKG.getWidth() - 23, Constants.GlobalHeight - PlayPauseButtonBKG.getHeight() - 23);
		
		
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = Resources.LoadFont("fonts/GoogleIconsRounded.ttf", 32, FontResource.GetCharacterRangeFromTo(0, 150));
		textButtonStyle.fontColor = Color.WHITE;
		
		
		Buttons[0] = new TextButton("\u0083", textButtonStyle);
		Buttons[0].setVisible(true);
		Buttons[0].setWidth(PlayPauseButtonBKG.getWidth());
		Buttons[0].setHeight(PlayPauseButtonBKG.getHeight());
		Buttons[0].setPosition(PlayPauseButtonBKG.getX(), PlayPauseButtonBKG.getY());
		Buttons[0].getLabelCell().padTop(32.0f);
		Buttons[0].getLabelCell().padLeft(0.5f);
		//Buttons[0].debugAll();
		Buttons[0].addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				Constants.IsGamePaused = !Constants.IsGamePaused;
				if (Constants.IsGamePaused)
				{
					Buttons[0].setText("\u0086");
				} else
				{
					Buttons[0].setText("\u0083");
				}
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
		
		
		MenuButtons[0] = new ImageButton(DrawUtil.GetDrawableByTexture(ButtonBKGTexture));
		MenuButtons[0].setVisible(true);
		MenuButtons[0].setWidth(45);
		MenuButtons[0].setHeight(45);
		MenuButtons[0].setPosition(UIMenu.getX() + 2, UIMenu.getY() + 2);
		//Buttons[0].debugAll();
		MenuButtons[0].addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
			
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
		
		
		
		MenuButtons[1] = new ImageButton(DrawUtil.GetDrawableByTexture(ButtonBKGTexture));
		MenuButtons[1].setVisible(true);
		MenuButtons[1].setWidth(45);
		MenuButtons[1].setHeight(45);
		MenuButtons[1].setPosition(UIMenu.getX() + 65, UIMenu.getY() + 2);
		//Buttons[0].debugAll();
		MenuButtons[1].addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
			
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
		
		
		
		MenuButtons[2] = new ImageButton(DrawUtil.GetDrawableByTexture(ButtonBKGTexture));
		MenuButtons[2].setVisible(true);
		MenuButtons[2].setWidth(45);
		MenuButtons[2].setHeight(45);
		MenuButtons[2].setPosition(UIMenu.getX() + 128, UIMenu.getY() + 2);
		//Buttons[0]2debugAll();
		MenuButtons[2].addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
			
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
		
		
		
		
		MenuButtons[3] = new ImageButton(DrawUtil.GetDrawableByTexture(ButtonBKGTexture));
		MenuButtons[3].setVisible(true);
		MenuButtons[3].setWidth(45);
		MenuButtons[3].setHeight(45);
		MenuButtons[3].setPosition(UIMenu.getX() + 191, UIMenu.getY() + 2);
		//Buttons[0]3debugAll();
		MenuButtons[3].addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
			
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
		
		MenuButtons[4] = new ImageButton(DrawUtil.GetDrawableByTexture(ButtonBKGTexture));
		MenuButtons[4].setVisible(true);
		MenuButtons[4].setWidth(45);
		MenuButtons[4].setHeight(45);
		MenuButtons[4].setPosition(UIMenu.getX() + 254, UIMenu.getY() + 2);
		//Buttons[0]4debugAll();
		MenuButtons[4].addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
			
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
		
		
		MenuButtons[5] = new ImageButton(DrawUtil.GetDrawableByTexture(ButtonBKGTexture));
		MenuButtons[5].setVisible(true);
		MenuButtons[5].setWidth(45);MenuButtons[5].setHeight(45);
		MenuButtons[5].setPosition(UIMenu.getX() + 315, UIMenu.getY() + 2);
		//Buttons[0]5debugAll();
		MenuButtons[5].addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
			
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
		
		
		
		
		for (TextButton Button : Buttons)
		{
			if (Button != null)
			{
				Constants.Stage.addActor(Button);
			}
		}
		
		
		for (ImageButton Button : MenuButtons)
		{
			if (Button != null)
			{
				Constants.Stage.addActor(Button);
			}
		}
		
		
	}
	
	
	public void OnRender()
	{
		Constants.batch.begin();
		ScoreUI.draw(Constants.batch);
		UIMenu.draw(Constants.batch);
		PlayPauseButtonBKG.draw(Constants.batch);
		
		
		DrawUtil.DrawText(Constants.batch, Constants.ScoreUIFontIcons, "\u0116", PlayPauseButtonBKG.getX() - 200, PlayPauseButtonBKG.getY() + 11, Color.WHITE);
		DrawUtil.DrawText(Constants.batch, Constants.ScoreUIFont, "Wave " + Constants.CurrentWave + " / " + Constants.TotalWavesInLevel, PlayPauseButtonBKG.getX() - 184, PlayPauseButtonBKG.getY() + 23, Color.WHITE);
		
		DrawUtil.DrawText(Constants.batch, Constants.ScoreUIFontIcons, "\u0183", PlayPauseButtonBKG.getX() - 100, PlayPauseButtonBKG.getY() + 11, Color.WHITE);
		DrawUtil.DrawText(Constants.batch, Constants.ScoreUIFont, "" + Constants.Coins, PlayPauseButtonBKG.getX() - 88, PlayPauseButtonBKG.getY() + 23, Color.WHITE);
		
		
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
