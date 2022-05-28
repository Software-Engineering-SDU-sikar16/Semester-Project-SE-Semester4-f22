package dk.sdu.mmmi.cbse.common.data;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.DEFAULT_CHARS;

public class Resources
{
	public static ExecutorService ThreadPool = Executors.newCachedThreadPool();
	
	public static ConcurrentHashMap<String, Texture> Textures = new ConcurrentHashMap<String, Texture>();
	
	public static ConcurrentHashMap<String, FontResource> Fonts = new ConcurrentHashMap<String, FontResource>();
	public static ConcurrentHashMap<String, Music> Musics = new ConcurrentHashMap<String, Music>();
	
	public static Texture LoadTexture(String FilePath)
	{
		if (Textures.containsKey(FilePath))
		{
			return Textures.get(FilePath);
		}
		else
		{
			Texture texture = new Texture(Gdx.files.internal(FilePath), true);
			texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
			if (texture != null)
			{
				Textures.put(FilePath, texture);
				return texture;
			}
			return null;
		}
	}
	
	
	public static void LoadTextures()
	{
		ThreadPool.submit(new Runnable()
		{
			@Override
			public void run()
			{
				LoadTexture("./assets/images/star_tiny.png");
				LoadTexture("./assets/ui/start_button.png");
				LoadTexture("./assets/ui/UI_BUTTON_BKG.png");
				LoadTexture("./assets/ui/UI_LEFT_SELECTOR.png");
				LoadTexture("./assets/ui/UI_TOP_SCORE.png");
			}
		});
		
		
	}
	
	public static BitmapFont LoadFont(String FilePath, int Size)
	{
		return LoadFont(FilePath, Size, DEFAULT_CHARS);
	}
	
	public static BitmapFont LoadFont(String FilePath, int Size, String characters)
	{
		if (Fonts.containsKey(FilePath + Size))
		{
			return Fonts.get(FilePath + Size).GetFont();
		}
		else
		{
			FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(FilePath));
			FreeTypeFontGenerator.FreeTypeFontParameter freeTypeFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
			freeTypeFontParameter.size = Size;
			freeTypeFontParameter.characters = characters;
			freeTypeFontParameter.magFilter = Texture.TextureFilter.Linear;
			fontGenerator.generateData(freeTypeFontParameter);
			BitmapFont font = fontGenerator.generateFont(freeTypeFontParameter);
			
			fontGenerator.dispose();
			
			//"fake" smooth the font
			font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
			
			
			Fonts.put(FilePath + Size, new FontResource(font, Size));
			return font;
		}
	}
	
	public static void LoadFonts(GameData gameData)
	{
		LoadFont("../assets/fonts/GoogleIconsRounded.ttf", 24);
		
		gameData.BigPauseScreenFont = LoadFont("../assets/fonts/editundo.ttf", 35);
		//		Constants.BigPauseScreenFont = LoadFont("fonts/OpenSans-Bold.ttf", 42 * (Constants.GlobalWidth / Constants.GlobalHeight)); // 42 for best luck
		gameData.GlobalSpriteBatch = new SpriteBatch();
		
		gameData.ScoreUIFont =  LoadFont("../assets/fonts/editundo.ttf", 24);
		gameData.ScoreUIFontIcons = LoadFont("../assets/fonts/GoogleIconsRounded.ttf", 22, FontResource.GetCharacterRangeFromTo(0, 500));
		
		gameData.PixelFont = LoadFont("../assets/fonts/editundo.ttf", 14);
	}
	
	public static Music LoadSound(String FilePath)
	{
		if (Musics.containsKey(FilePath))
		{
			return Musics.get(FilePath);
		}
		else
		{
			Music music = Gdx.audio.newMusic(Gdx.files.internal(FilePath));
			Musics.put(FilePath, music);
			return music;
		}
	}
	
	public static void LoadSounds()
	{
		
		
		ThreadPool.submit(new Runnable()
		{
			@Override
			public void run()
			{
				//Music backgroundMusic = LoadSound("sound/title_music.mp3");
				//backgroundMusic.play();
			}
		});
		
	}
	
	public static void LoadAllAssets(GameData gameData) {
		LoadFonts(gameData);
		LoadTextures();
		LoadSounds();
	}
	
	public static void Dispose(GameData gameData)
	{
		gameData.GlobalSpriteBatch.dispose();
		for (Texture tex : Textures.values())
		{
			tex.dispose();
		}
		
		for (FontResource font : Fonts.values())
		{
			font.Dispose();
		}
	}
	
	
}
