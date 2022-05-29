package dk.sdu.mmmi.cbse.common.data;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.cbse.common.data.components.EntityPart;
import dk.sdu.mmmi.cbse.common.data.components.Tower;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class World
{
	
	private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();
	public static HashMap<Vector2, Entity> turretPositions = new HashMap<Vector2, Entity>();
	
	
	public String addEntity(Entity entity)
	{
		entityMap.put(entity.getID(), entity);
		return entity.getID();
	}
	
	public void removeEntity(String entityID)
	{
		entityMap.remove(entityID);
	}
	
	public void removeEntity(Entity entity)
	{
		entityMap.remove(entity.getID());
	}
	
	public Collection<Entity> getEntities()
	{
		return entityMap.values();
	}
	
	public <E extends EntityPart> List<Entity> getEntitiesOfWithComponents(Class partClass)
	{
		return entityMap.values().parallelStream().filter(x -> x.getPart(partClass) != null).collect(Collectors.toList());
	}
	
	
	public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes)
	{
		List<Entity> r = new ArrayList<>();
		for (Entity e : getEntities())
		{
			for (Class<E> entityType : entityTypes)
			{
				if (entityType.equals(e.getClass()))
				{
					r.add(e);
				}
			}
		}
		return r;
	}
	
	public Entity getEntity(String ID)
	{
		return entityMap.get(ID);
	}
	
	public void addTower(int x, int y)
	{
		Tower tower = new Tower(x, y);
		turretPositions.put(new Vector2(x, y), tower);
		addEntity(tower);
	}
	
	public void CreateAllTowers(GameData gameData)
	{
		getEntities(Tower.class).forEach(tower ->
		                                 {
			                                 Tower myTower = (Tower) tower;
			                                 myTower.OnCreate(gameData, this);
		                                 });
	}
	
	public void DisposeAllTowers(GameData gameData)
	{
		getEntities(Tower.class).forEach(tower ->
		                                 {
			                                 Tower myTower = (Tower) tower;
			                                 myTower.OnDispose(gameData, this);
		                                 });
		getEntities(Tower.class).forEach(tower -> removeEntity(tower));
	}
	
	public void ProcessTowers(GameData gameData)
	{
		getEntities(Tower.class).forEach(tower ->
		                                 {
			                                 Tower myTower = (Tower) tower;
			                                 myTower.OnUpdate(gameData, this);
			                                 myTower.OnRender(gameData, this);
		                                 });
	}
}
