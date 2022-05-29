package dk.sdu.mmmi.cbse.common.data.Map;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;


public class Tile {
	public int x;
	public int y;
	public String name;
	public int index = 0;
	public MapObject mapObject;
	
	public Tile(int x, int y, String name)
	{
		this.x = x;
		this.y = y;
		this.name = name;
	}
	
	public Tile(int x, int y, String name, MapObject mapObject) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.mapObject = mapObject;
	}
	
	public void render(ShapeRenderer shapeRenderer, SpriteBatch batch, BitmapFont font, boolean inPath){
		
		if (false)
		{
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			if(inPath) {
				// green
				shapeRenderer.setColor(.57f, .76f, .48f, 1);
			}
			else{
				// blue
				shapeRenderer.setColor(.8f, .88f, .95f, 1);
			}
			shapeRenderer.circle(x, y, 20);
			shapeRenderer.end();
			
			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			shapeRenderer.setColor(0, 0, 0, 1);
			shapeRenderer.circle(x, y, 20);
			shapeRenderer.end();
			batch.begin();
			font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			font.draw(batch, "" + index, x, y);
			//font.draw(batch, name, x, y);
			batch.end();
		}

	}

	@Override
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
	
}
