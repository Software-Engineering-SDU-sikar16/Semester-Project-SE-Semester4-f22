package helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;

import static com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.DEFAULT_CHARS;


public class Resources
{
	
	
	public static HashMap<String, Texture> Textures = new HashMap<String, Texture>();
	
	public static HashMap<String, FontResource> Fonts = new HashMap<String, FontResource>();
	public static HashMap<String, Music> Musics = new HashMap<String, Music>();
	
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
		LoadTexture("images/star_tiny.png");
		
		
		LoadTexture("ui/UI_BUTTON_BKG.png");
		LoadTexture("ui/UI_LEFT_SELECTOR.png");
		LoadTexture("ui/UI_TOP_SCORE.png");
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
	
	public static void LoadFonts()
	{
		
		LoadFont("fonts/GoogleIconsRounded.ttf", 24);
		
		Constants.BigPauseScreenFont = LoadFont("fonts/editundo.ttf", 35); // 42 for best luck
//		Constants.BigPauseScreenFont = LoadFont("fonts/OpenSans-Bold.ttf", 42 * (Constants.GlobalWidth / Constants.GlobalHeight)); // 42 for best luck
		Constants.BigPauseScreenSpriteBatch = new SpriteBatch();
		
		Constants.ScoreUIFont = LoadFont("fonts/OpenSans-Bold.ttf", 13);
		Constants.ScoreUIFontIcons = LoadFont("fonts/GoogleIconsRounded.ttf", 16, FontResource.GetCharacterRangeFromTo(0, 500));
		
		
		Constants.PixelFont = LoadFont("fonts/editundo.ttf", 14);
		
		Constants.ScoreUIFont = Constants.PixelFont;
		
	/*	FileHandle fontFile = Gdx.files.internal("data/Roboto-Bold.ttf");
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		textFont = generator.generateFont(parameter);
		parameter.size = 24;
		titleFont = generator.generateFont(parameter);
		generator.dispose();
		*/
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
		Music backgroundMusic = LoadSound("sound/title_music.mp3");
		backgroundMusic.play();
		
	}
	
	public static void Dispose()
	{
		Constants.BigPauseScreenSpriteBatch.dispose();
		
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
