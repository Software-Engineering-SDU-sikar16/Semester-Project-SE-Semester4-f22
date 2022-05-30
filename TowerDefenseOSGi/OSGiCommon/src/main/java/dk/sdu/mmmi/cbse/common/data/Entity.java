package dk.sdu.mmmi.cbse.common.data;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.cbse.common.data.components.EntityPart;
import dk.sdu.mmmi.cbse.common.data.components.PositionPart;
import dk.sdu.mmmi.cbse.common.services.EntityComparator;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public abstract class Entity implements IRenderable, ICollidable,  Serializable
{
	private final UUID ID = UUID.randomUUID(); // Unique ID for this entity
	
	public static dk.sdu.mmmi.cbse.common.services.EntityComparator EntityComparator = new EntityComparator(); // for z sorting
	
	private Map<Class, EntityPart> parts; // Components
	
	public Entity()
	{
		parts = new ConcurrentHashMap<>();
	}
	
	public Entity(int x, int y)
	{
		parts = new ConcurrentHashMap<>();
		PositionPart transform = new PositionPart(x, y); // Create a transform component for this entity
		add(transform);
	}
	
	public Entity(int x, int y, int width, int height)
	{
		parts = new ConcurrentHashMap<>();
		PositionPart transform = new PositionPart(x, y, width, height); // Create a transform component for this entity
		add(transform);
	}
	
	public void add(EntityPart part)
	{
		parts.put(part.getClass(), part);
	} // Add a component to this entity
	
	public void remove(Class partClass)
	{
		parts.remove(partClass);
	} // Remove a component from this entity
	
	public <E extends EntityPart> E getPart(Class partClass)
	{
		return (E) parts.get(partClass);
	} // Get a component from this entity
	
	
	public PositionPart getTransform()
	{
		return getPart(PositionPart.class);
	} // Get the transform component
	
	
	public String getID()
	{
		return ID.toString();
	}
	
	
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
	
	

	
	public void InternalOnCreate(GameData gameData, World world)
	{
		OnCreate(gameData, world);
		for (EntityPart part : parts.values())
		{
			part.OnCreate(gameData, world, this);
		}
	}
	
	public void InternalOnUpdate(GameData gameData, World world)
	{
		OnUpdate(gameData, world);
		for (EntityPart part : parts.values())
		{
			part.OnUpdate(gameData, world, this);
		}
	}
	
	public void InternalOnRender(GameData gameData, World world)
	{
		OnRender(gameData, world);
		for (EntityPart part : parts.values())
		{
			part.OnRender(gameData, world, this);
		}
	}
	
	public abstract void OnCreate(GameData gameData, World world);
	
	public abstract void OnRender(GameData gameData, World world);
	
	public abstract void OnUpdate(GameData gameData, World world);
	
}
