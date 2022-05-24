package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public abstract class Entity extends Sprite implements IEntity
{
	public static Array<Entity> Entities = new Array<Entity>();
	public static EntityComparator EntityComparator = new EntityComparator(); // for z sorting
	
	private int ZIndex = 0; // Z Sorting of the sprite [-100, ..., 0, ..., +100]. default 0. => All entities are sorted each frame before being drawn.
	private boolean Enabled = true;
	
	public void SetEnabled(boolean value)
	{
		this.Enabled = value;
	}
	
	public boolean IsEnabled()
	{
		return Enabled;
	}
	
	
	public void SetZIndex(int NewZIndex)
	{
		ZIndex = NewZIndex;
	}
	
	public int GetZIndex()
	{
		return ZIndex;
	}
	
	public void SetZIndexBelow(Entity other)
	{
		ZIndex = other.GetZIndex() - 1;
	}
	
	public void SetZIndexAbove(Entity other)
	{
		ZIndex = other.GetZIndex() + 1;
	}
	
	
	@Override
	public void setPosition(float x, float y)
	{
		super.setPosition(x, y);
	}
	
	public void setPosition(Vector2 pos)
	{
		setPosition(pos.x, pos.y);
	}
	
	public Vector2 getPosition()
	{
		return new Vector2(getX(), getY());
	}
	
	public Entity()
	{
		Entities.add(this);
		Enabled = true;
	}
	
	public Entity(int x, int y, int width, int height)
	{
		super();
		setPosition(x, y);
		setSize(width, height);
	}
	
	
	public static void RenderAllEntities()
	{
		Entities.sort(EntityComparator);
		
		//render entities before overlays
		for (Entity entity : Entities)
		{
			if (!entity.Enabled)
			{
				continue;
			}
			entity.OnRender();
		}
	}
	
	public static void CreateAllEntities()
	{
		//Create Entities
		for (Entity entity : Entities)
		{
			entity.OnCreate();
		}
	}
	
	public static void UpdateAllEntities()
	{
		for (Entity entity : Entities)
		{
			if (!entity.Enabled)
			{
				continue;
			}
			entity.OnUpdate(Gdx.graphics.getDeltaTime());
		}
	}
	
	
}
