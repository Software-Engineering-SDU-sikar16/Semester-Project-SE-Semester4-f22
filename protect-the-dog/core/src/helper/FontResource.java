package helper;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class FontResource
{
	int Size;
	BitmapFont Font;
	
	public FontResource(BitmapFont font, int size)
	{
		Font = font;
		Size = size;
	}
	
	public static String GetCharacterRangeFromTo(int from, int to)
	{
		StringBuilder builder = new StringBuilder();
		
		
		for (int i = from; i < to; i++)
		{
			String s = String.valueOf((char)i);
			builder.append(s);
		}
		
		return builder.toString();
	}
	
	int GetSize()
	{
		return Size;
	}
	
	BitmapFont GetFont()
	{
		return Font;
	}
}
