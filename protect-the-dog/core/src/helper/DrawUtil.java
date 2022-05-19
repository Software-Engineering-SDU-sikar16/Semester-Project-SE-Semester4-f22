package helper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class DrawUtil
{
	
	public static void DrawPerspectiveSquareDebug(ShapeRenderer shapeRenderer, float x, float y, float width, float height, Color color)
	{
		float centerX = width / 2;
		float centerY = height / 2;
		float x2 = x + width;
		float y2 = y + height;
		
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.triangle(x, y, centerX, centerY, x2, y, color, color, color);
		shapeRenderer.triangle(x2, y, centerX, centerY, x2, y2, color, color, color);
		shapeRenderer.triangle(x2, y2, centerX, centerY, x, y2, color, color, color);
		shapeRenderer.triangle(x, y2, centerX, centerY, x, y, color, color, color);
		shapeRenderer.end();
	}
	
	
	private void DrawTriangleAtPositon(ShapeRenderer shapeRenderer, Vector2 P, float Scale, Color color)
	{
		
		/**
		 
		 A
		 / \
		 /   \
		 /  P  \
		 /       \
		 C---------B
		 Solve for p:
		 A = (p.x, p.y + 1);
		 B = (p.x + 1, p.y -1)
		 C = (p.x - 1, p.y -1)
		 */
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.triangle(
				P.x, P.y + Scale, //A
				P.x + Scale, P.y - Scale, //B
				P.x - Scale, P.y - Scale, //C
				color, color, color
		);
		shapeRenderer.end();
		// Actually order of parameters doesn't matter it's a triangle either way.
		
	}
	
	public static void DrawText(SpriteBatch batch, BitmapFont font, String text, float x, float y, Color color)
	{
		font.setColor(color);
		font.draw(batch, text, x, y);
	}
	
	public static Drawable GetColoredDrawable(int width, int height, Color color)
	{
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fill();
		
		TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
		
		pixmap.dispose();
		
		return drawable;
	}
	
	public static Drawable GetDrawableByTexture(Texture texture)
	{
		return new TextureRegionDrawable(new TextureRegion(texture));
	}
}
