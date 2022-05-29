package dk.sdu.mmmi.cbse.osgioverlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Resources;
import dk.sdu.mmmi.cbse.common.data.World;

public class PauseScreen extends Overlay
{
	GlyphLayout GlyphLayouter = new GlyphLayout();
	
	TextButton[] Buttons = new TextButton[2];
	
	Vector2 SelectorPosition = new Vector2(0, 0);
	Sprite SelectorSprite;
	Sprite SelectorSpriteTwo;
	Vector2 CenterPoint;
	
	private void SetSelectorSpriteLocation(GameData gameData, TextButton button)
	{
		String text = button.getText().toString();
		SelectorPosition = GetTextCenterPosition(gameData.BigPauseScreenFont, text, button.getX(), button.getY());
		GlyphLayouter.setText(gameData.BigPauseScreenFont, text);
		SelectorSprite.setPosition(button.getX() - 60, button.getY() - 15);
		SelectorSpriteTwo.setPosition(button.getX() + GlyphLayouter.width - 17, button.getY() - 15);
	}
	
	
	@Override
	public void OnCreate(GameData gameData, World world)
	{
		SelectorSprite = new Sprite(Resources.LoadTexture("../assets/images/star_tiny.png"));
		SelectorSpriteTwo = new Sprite(Resources.LoadTexture("../assets/images/star_tiny.png"));
		System.out.println("pause screen created 2");
		CenterPoint = new Vector2(gameData.getGlobalWidth() / 2, gameData.getGlobalHeight() / 2);
		
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = gameData.BigPauseScreenFont;
		textButtonStyle.fontColor = Color.WHITE;
		
		
		Vector2 Pos = GetTextCenterPosition(gameData.BigPauseScreenFont, "Resume", CenterPoint.x, CenterPoint.y + 100);
		Buttons[0] = new TextButton("Resume ", textButtonStyle);
		Buttons[0].setPosition(Pos.x, Pos.y);
		Buttons[0].setVisible(false);
		Buttons[0].addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				TogglePaused(gameData);
			}
			
			
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{
				
				SetSelectorSpriteLocation(gameData, Buttons[0]);
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				SelectorPosition = Vector2.Zero;
			}
			
		});
		
		Pos = GetTextCenterPosition(gameData.BigPauseScreenFont,"Exit", CenterPoint.x, CenterPoint.y + 50);
		Buttons[1] = new TextButton("Exit", textButtonStyle);
		Buttons[1].setPosition(Pos.x, Pos.y);
		Buttons[1].setVisible(false);
		Buttons[1].addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				Gdx.app.exit();
			}
			
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{
				SetSelectorSpriteLocation(gameData, Buttons[1]);
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				SelectorPosition = Vector2.Zero;
			}
		});
		
		for (TextButton button : Buttons)
		{
			gameData.UIStage.addActor(button);
		}
	}
	

	@Override
	public void OnRender(GameData gameData, World world)
	{
		if (!gameData.IsPauseScreenVisible)
		{
			return;
		}
		
		
		if (SelectorPosition.x > 0 && SelectorPosition.y > 0)
		{
			gameData.GlobalSpriteBatch.begin();
			SelectorSprite.draw(gameData.GlobalSpriteBatch);
			SelectorSpriteTwo.draw(gameData.GlobalSpriteBatch);
			gameData.GlobalSpriteBatch.end();
		}
		
	}
	
	
	private Vector2 GetTextCenterPosition(BitmapFont font, String text, float x, float y)
	{
		GlyphLayouter.setText(font, text);
		return new Vector2(x - GlyphLayouter.width / 2, y);
	}
	
	@Override
	public void OnUpdate(GameData gameData, World world)
	{
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
		{
			TogglePaused(gameData);
		}
		
		
	}
	
	@Override
	public void OnDispose(GameData gameData, World world)
	{
	
	}
	
	public void TogglePaused(GameData gameData)
	{
		SetPaused(gameData, !gameData.IsPauseScreenVisible);
	}
	
	public void SetPaused(GameData gameData, boolean value)
	{
		gameData.IsPauseScreenVisible = value;
		
		for (TextButton button : Buttons)
		{
			button.setVisible(value);
		}
	}
	
}
