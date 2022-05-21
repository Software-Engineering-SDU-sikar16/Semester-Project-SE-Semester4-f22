package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends Game {

	public static MyGdxGame INSTANCE;
	private int widthScreen, heightScreen;
	private OrthographicCamera orthographicCamera;


	public MyGdxGame() {
		INSTANCE = this;
	}

	@Override
	public void create()
	{
		Constants.GlobalWidth = Gdx.graphics.getWidth();
		Constants.GlobalHeight = Gdx.graphics.getHeight();
		Resources.LoadFonts();
		Resources.LoadTextures();
		Resources.LoadSounds();
	
		
		Constants.Viewport = new FitViewport(Constants.GlobalWidth, Constants.GlobalHeight);
		Constants.Stage = new Stage(Constants.Viewport);
		Gdx.input.setInputProcessor(Constants.Stage);
		
		new CustomCursorDrawer();
		
		
		Constants.Camera = new OrthographicCamera();
		Constants.Camera.setToOrtho(false, Constants.GlobalWidth, Constants.GlobalHeight);
		setScreen(new GameScreen());
		
		
		// Create Overlays
		Constants.PauseScreen = new PauseScreen();
		Constants.GameUIOverlay = new GameUIOverlay();
		
		
		for (int i = 0; i < 23; i++)
		{
			AnimatedSprite sprite2 = new AnimatedSprite(50 + (i * 50), 158 + (i), 24, 24);
//			sprite2.AddAnimation("default", Resources.LoadTexture("entities/enemies/enemies/sheet_"+ i + ".png "), 1, 4, 12, Animation.PlayMode.NORMAL);
		}
		
		
		sprite = new AnimatedSprite("spine/FogExplosion/fog explosion.atlas", "spine/FogExplosion/skeleton.json", 128, 128);
		sprite.SetZIndex(2);
		
		
		
		// these must be the last things that happen when the application is created, after everything else as possible.
		Entity.CreateAllEntities();
		
		Overlay.CreateAllOverlays();
		
		Constants.shapeRenderer = new ShapeRenderer();
		Constants.batch = new SpriteBatch();
		Constants.font = new BitmapFont();
		
		
	}


/*	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
		img = new Texture("mapproject.png");
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}*/
}
