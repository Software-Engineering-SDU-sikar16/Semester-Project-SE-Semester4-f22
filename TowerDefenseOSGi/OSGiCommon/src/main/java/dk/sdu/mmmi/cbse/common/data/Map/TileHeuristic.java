package dk.sdu.mmmi.cbse.common.data.Map;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;


public class TileHeuristic implements Heuristic<Tile> { // This class is used to calculate the heuristic value for a tile.
    @Override
    public float estimate(Tile current, Tile goal) { // estimate the distance between the current tile and the goal tile
        return Vector2.dst(current.x, current.y, goal.x, goal.y); // calculate the distance between the two tiles
    } // estimate the distance between the current tile and the goal tile
}
