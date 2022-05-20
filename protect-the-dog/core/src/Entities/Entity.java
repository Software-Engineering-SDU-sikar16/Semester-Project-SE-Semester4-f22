package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public abstract class Entity extends Sprite implements IEntity
{
	public static Array<Entity> Entities = new Array<Entity>(); // Data Oriented Programming!
	public static EntityComparator EntityComparator = new EntityComparator(); // for z sorting
	
	private int ZIndex = 0; // Z Sorting of the sprite [-100, ..., 0, ..., +100]. default 0. => All entities are sorted each frame before being drawn.
	private boolean Enabled = false;
	
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
	
	public void setPosition(Vector2 pos)
	{
		setPosition(pos.x, pos.y);
	}
	
	
	public Entity()
	{
		Entities.add(this);
		Enabled = true;
	}
	
	
	public void OnRenderInternal()
	{
	}
	
	public void OnCreateInternal()
	{
		Enabled = true;
	}
	
	public void OnUpdateInternal()
	{
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
			entity.OnRenderInternal();
			entity.OnRender();
		}
	}
	
	public static void CreateAllEntities()
	{
		//Create Entities
		for (Entity entity : Entities)
		{
			entity.OnCreateInternal();
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
			entity.OnUpdateInternal();
			entity.OnUpdate(Gdx.graphics.getDeltaTime());
		}
	}
	
	
}
