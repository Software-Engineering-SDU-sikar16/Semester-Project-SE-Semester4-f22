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
	
	ImageButton StartButton;
	
	ImageButton[] MenuButtons = new ImageButton[6];
	
	
	Vector2 CenterPoint;
	
	
	Texture StartButtonTexture;
	Texture UIMenuBKGTexture;
	
	Sprite UIMenu;
	
	
	@Override
	public void OnCreate()
	{
		StartButtonTexture = Resources.LoadTexture("ui/start_button.png");
		UIMenuBKGTexture = Resources.LoadTexture("ui/UI_MENU.png");
		
		
		CenterPoint = new Vector2(Constants.GlobalWidth / 2, Constants.GlobalHeight / 2);
		Vector2 Pos = GetTextCenterPosition("\u0083", CenterPoint.x, CenterPoint.y);
		
		UIMenu = new Sprite(UIMenuBKGTexture);
		UIMenu.setSize(UIMenuBKGTexture.getWidth() / 1, UIMenuBKGTexture.getHeight() /1.5f);
		UIMenu.setPosition(0, 0);
	
		
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = Resources.LoadFont("fonts/GoogleIconsRounded.ttf", 32, FontResource.GetCharacterRangeFromTo(0, 150));
		textButtonStyle.fontColor = Color.WHITE;
		
		
		StartButton = new ImageButton(DrawUtil.GetDrawableByTexture(StartButtonTexture));
		StartButton.setVisible(true);
		StartButton.setWidth(StartButtonTexture.getWidth() * 1.5f);
		StartButton.setHeight(StartButtonTexture.getHeight()* 1.5f);
		StartButton.getImage().setFillParent(true);
		StartButton.setPosition(180, Constants.GlobalHeight - 48);
		StartButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
			
			}
			
			
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{
				System.out.println("entered");
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				System.out.println("exit");
			}
			
		});

		
		MenuButtons[0] = new ImageButton(DrawUtil.GetDrawableByTexture(StartButtonTexture));
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
		
		
		
		MenuButtons[1] = new ImageButton(DrawUtil.GetDrawableByTexture(StartButtonTexture));
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
		
		
		
		MenuButtons[2] = new ImageButton(DrawUtil.GetDrawableByTexture(StartButtonTexture));
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
		
		
		
		
		MenuButtons[3] = new ImageButton(DrawUtil.GetDrawableByTexture(StartButtonTexture));
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
		
		MenuButtons[4] = new ImageButton(DrawUtil.GetDrawableByTexture(StartButtonTexture));
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
		
		
		MenuButtons[5] = new ImageButton(DrawUtil.GetDrawableByTexture(StartButtonTexture));
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
		
	
		
		for (ImageButton Button : MenuButtons)
		{
			if (Button != null)
			{
				Constants.Stage.addActor(Button);
			}
		}
		
		Constants.Stage.addActor(StartButton);
		
		new Health();
		
		
		
	}
	
	
	public void OnRender()
	{
		Constants.batch.begin();
		
		UIMenu.draw(Constants.batch);
		DrawUtil.DrawText(Constants.batch, Constants.ScoreUIFont, "Wave " + Constants.CurrentWave + " / " + Constants.TotalWavesInLevel,  25,  Constants.GlobalHeight- 20, Color.WHITE);
		
		DrawUtil.DrawText(Constants.batch, Constants.ScoreUIFontIcons, "\u0183", 25, Constants.GlobalHeight - 118, Color.WHITE);
		DrawUtil.DrawText(Constants.batch, Constants.ScoreUIFont, "" + Constants.Coins, 41, Constants.GlobalHeight- 99, Color.WHITE);
		
		
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
