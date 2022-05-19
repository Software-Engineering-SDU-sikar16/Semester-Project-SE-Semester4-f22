package Overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.MyGdxGame;
import helper.Constants;
import helper.Resources;

public class PauseScreen extends Overlay
{
	
	
	GlyphLayout GlyphLayouter = new GlyphLayout();
	
	TextButton[] Buttons = new TextButton[5];
	
	Vector2 SelectorPosition = new Vector2(0, 0);
	Sprite SelectorSprite;
	Sprite SelectorSpriteTwo;
	Vector2 CenterPoint;
	
	private void SetSelectorSpriteLocation(TextButton button)
	{
		String text = button.getText().toString();
		SelectorPosition = GetTextCenterPosition(text, button.getX(), button.getY());
		GlyphLayouter.setText(Constants.BigPauseScreenFont, text);
		SelectorSprite.setPosition(button.getX() - 60, button.getY() - 15);
		SelectorSpriteTwo.setPosition(button.getX() + GlyphLayouter.width - 17, button.getY() - 15);
	}
	
	
	@Override
	public void OnCreate()
	{
		
		
		SelectorSprite = new Sprite(Resources.LoadTexture("images/star_tiny.png"));
		SelectorSpriteTwo = new Sprite(Resources.LoadTexture("images/star_tiny.png"));
		
		CenterPoint = new Vector2(Constants.GlobalWidth / 2, Constants.GlobalHeight / 2);
		
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = Constants.BigPauseScreenFont;
		textButtonStyle.fontColor = Color.WHITE;
		
		
		Vector2 Pos = GetTextCenterPosition("Resume", CenterPoint.x, CenterPoint.y + 100);
		Buttons[0] = new TextButton("Resume ", textButtonStyle);
		Buttons[0].setPosition(Pos.x, Pos.y);
		Buttons[0].setVisible(false);
		Buttons[0].addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				TogglePaused();
			}
			
			
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{
				SetSelectorSpriteLocation(Buttons[0]);
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				SelectorPosition = Vector2.Zero;
			}
			
		});
		
		Pos = GetTextCenterPosition("Game Settings", CenterPoint.x, CenterPoint.y + 50);
		Buttons[1] = new TextButton("Game Settings ", textButtonStyle);
		Buttons[1].setPosition(Pos.x, Pos.y);
		Buttons[1].setVisible(false);
		Buttons[1].addListener(new ClickListener()
		{
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{
				SetSelectorSpriteLocation(Buttons[1]);
			}
			
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				SelectorPosition = Vector2.Zero;
			}
		});
		
		
		Pos = GetTextCenterPosition("Video Settings", CenterPoint.x, CenterPoint.y);
		Buttons[2] = new TextButton("Video Settings", textButtonStyle);
		Buttons[2].setPosition(Pos.x, Pos.y);
		Buttons[2].setVisible(false);
		Buttons[2].addListener(new ClickListener()
		{
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{
				SetSelectorSpriteLocation(Buttons[2]);
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				SelectorPosition = Vector2.Zero;
			}
			
			
		});
		
		
		Pos = GetTextCenterPosition("Audio Settings", CenterPoint.x, CenterPoint.y - 50);
		Buttons[3] = new TextButton("Audio Settings", textButtonStyle);
		Buttons[3].setPosition(Pos.x, Pos.y);
		Buttons[3].setVisible(false);
		Buttons[3].addListener(new ClickListener()
		{
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{
				SetSelectorSpriteLocation(Buttons[3]);
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				SelectorPosition = Vector2.Zero;
			}
		});
		
		
		Pos = GetTextCenterPosition("Exit", CenterPoint.x, CenterPoint.y - 100);
		Buttons[4] = new TextButton("Exit", textButtonStyle);
		Buttons[4].setPosition(Pos.x, Pos.y);
		Buttons[4].setVisible(false);
		Buttons[4].addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				Gdx.app.exit();
			}
			
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{
				SetSelectorSpriteLocation(Buttons[4]);
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				SelectorPosition = Vector2.Zero;
			}
		});
		
		for (TextButton button : Buttons)
		{
			Constants.Stage.addActor(button);
		}
	}
	
	@Override
	public void OnRender()
	{
		if (!Constants.IsPauseScreenVisible)
		{
			return;
		}
		
		
		if (SelectorPosition.x > 0 && SelectorPosition.y > 0)
		{
			Constants.batch.begin();
			SelectorSprite.draw(Constants.batch);
			SelectorSpriteTwo.draw(Constants.batch);
			Constants.batch.end();
		}
		
	}
	
	
	private Vector2 GetTextCenterPosition(String text, float x, float y)
	{
		GlyphLayouter.setText(Constants.BigPauseScreenFont, text);
		return new Vector2(x - GlyphLayouter.width / 2, y);
	}
	
	@Override
	public void OnUpdate(float DeltaTime)
	{
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
		{
			TogglePaused();
		}
		
		// this is just for testing
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
		{
			MyGdxGame.sprite.PlayAnimation();
		}
		
	}
	
	public void TogglePaused()
	{
		SetPaused(!Constants.IsPauseScreenVisible);
		MyGdxGame.sprite.PlayAnimation();
	}
	
	public void UnpauseGame()
	{
		SetPaused(false);
	}
	
	public void PauseGame()
	{
		SetPaused(true);
	}
	
	public void SetPaused(boolean value)
	{
		Constants.IsPauseScreenVisible = value;
		
		for (TextButton button : Buttons)
		{
			button.setVisible(value);
		}
	}
	
}
