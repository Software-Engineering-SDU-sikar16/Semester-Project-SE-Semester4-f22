package dk.sdu.mmmi.cbse.common.data.Map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class GameMapGraph implements IndexedGraph<Tile> {
    public Array<Tile> Tiles = new Array<>();
    public Array<TilePath> Paths = new Array<>();
    public ObjectMap<Tile, Array<Connection<Tile>>> PathsMap = new ObjectMap<>();
    TileHeuristic GameMapNodeHeuristic = new TileHeuristic();
    private int lastNodeIndex = 0;

    public void AddTile(Tile tile) {
        tile.index = lastNodeIndex;
        lastNodeIndex++;

        Tiles.add(tile);
    }


    public void ConnectTiles(Tile from, Tile to) { // This method connects two tiles.
        TilePath Path = new TilePath(from, to); // We create a new tile path.
        if (!PathsMap.containsKey(from)) { // If the from tile does not have any paths, we create a new array.
            PathsMap.put(from, new Array<Connection<Tile>>()); // We create a new array.
        }
        PathsMap.get(from).add(Path); // We add the path to the from tile.
        Paths.add(Path); // We add the path to the paths array.
    }
           
    /* Here is the explanation for the code above:
        1. We create a new IndexedAStarPathFinder<> object.
        2. We call the searchNodePath method on this object.
        3. We pass in the start and goal tiles.
        4. We pass in the GameMapNodeHeuristic as the heuristic.
        5. We pass in the path as the output path.
        6. We return the path.  */

    public GraphPath<Tile> FindPath(Tile start, Tile goal) {
        GraphPath<Tile> path = new DefaultGraphPath<>(); // We create a new path object.
        new IndexedAStarPathFinder<>(this).searchNodePath(start, goal, GameMapNodeHeuristic, path); // We call the searchNodePath method on the path finder.
        return path; // This is the path we want to return.
    }

    @Override
    public int getIndex(Tile node) {
        return node.index; // Returns the index of the node.
    }
    

    @Override
    public int getNodeCount() {
        return lastNodeIndex; // Returns the number of nodes in the graph.
    }

    @Override
    public Array<Connection<Tile>> getConnections(Tile fromNode) {
        if (PathsMap.containsKey(fromNode)) { // If the node has connections.
            return PathsMap.get(fromNode); // Return the connections.
        }

        return new Array<>(0); // If the node has no connections, return an empty array.
    }
}
