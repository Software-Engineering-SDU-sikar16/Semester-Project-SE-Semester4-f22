package dk.sdu.mmmi.cbse.common.data;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.services.EntityComparator;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;



public abstract class Entity extends Sprite implements IEntity, Serializable {
    private final UUID ID = UUID.randomUUID();
    
    
    public static dk.sdu.mmmi.cbse.common.services.EntityComparator EntityComparator = new EntityComparator(); // for z sorting

    private Map<Class, EntityPart> parts;
    
    public Entity() {
        parts = new ConcurrentHashMap<>();
    }
    
    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }
    
    public void remove(Class partClass) {
        parts.remove(partClass);
    }
    
    public <E extends EntityPart> E getPart(Class partClass) {
        return (E) parts.get(partClass);
    }
    

    public String getID() {
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
    
    
    
    
    
    
    
    
    
    
    

    
    public abstract void OnCreate(GameData gameData, World world);
    public abstract void OnRender(GameData gameData, World world);
    public abstract void OnUpdate(GameData gameData, World world);
    
}
