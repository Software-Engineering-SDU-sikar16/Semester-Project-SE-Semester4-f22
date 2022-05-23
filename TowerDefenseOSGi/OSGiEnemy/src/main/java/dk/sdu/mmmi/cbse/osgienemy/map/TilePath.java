package dk.sdu.mmmi.cbse.osgienemy.map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;


public class TilePath implements Connection<Tile>
{
	Tile From;
	Tile To;
	float cost;
	
	public TilePath(Tile from, Tile to)
	{
		this.From = from;
		this.To = to;
		cost = Vector2.dst(from.x, from.y, to.x, to.y);
	}
	
	public void render(ShapeRenderer shapeRenderer)
	{
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(0, 0, 0, 1);
		shapeRenderer.rectLine(From.x, From.y, To.x, To.y, 4);
		shapeRenderer.end();
	}
	
	@Override
	public float getCost()
	{
		return cost;
	}
	
	@Override
	public Tile getFromNode()
	{
		return From;
	}
	
	@Override
	public Tile getToNode()
	{
		return To;
	}
}
