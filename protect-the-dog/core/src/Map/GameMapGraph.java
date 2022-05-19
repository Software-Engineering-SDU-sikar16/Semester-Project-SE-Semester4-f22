package Map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class GameMapGraph implements IndexedGraph<Tile>
{
	
	TileHeuristic GameMapNodeHeuristic = new TileHeuristic();
	public Array<Tile> Tiles = new Array<>();
	public Array<TilePath> Paths = new Array<>();
	
	public ObjectMap<Tile, Array<Connection<Tile>>> PathsMap = new ObjectMap<>();
	
	private int lastNodeIndex = 0;
	
	public void AddTile(Tile tile)
	{
		tile.index = lastNodeIndex;
		lastNodeIndex++;
		
		Tiles.add(tile);
	}
	
	public void ConnectTiles(Tile from, Tile to)
	{
		TilePath Path = new TilePath(from, to);
		if (!PathsMap.containsKey(from))
		{
			PathsMap.put(from, new Array<Connection<Tile>>());
		}
		PathsMap.get(from).add(Path);
		Paths.add(Path);
	}
	
	public GraphPath<Tile> FindPath(Tile start, Tile goal)
	{
		GraphPath<Tile> cityPath = new DefaultGraphPath<>();
		new IndexedAStarPathFinder<>(this).searchNodePath(start, goal, GameMapNodeHeuristic, cityPath);
		return cityPath;
	}
	
	@Override
	public int getIndex(Tile node)
	{
		return node.index;
	}
	
	@Override
	public int getNodeCount()
	{
		return lastNodeIndex;
	}
	
	@Override
	public Array<Connection<Tile>> getConnections(Tile fromNode)
	{
		if (PathsMap.containsKey(fromNode))
		{
			return PathsMap.get(fromNode);
		}
		
		return new Array<>(0);
	}
}
