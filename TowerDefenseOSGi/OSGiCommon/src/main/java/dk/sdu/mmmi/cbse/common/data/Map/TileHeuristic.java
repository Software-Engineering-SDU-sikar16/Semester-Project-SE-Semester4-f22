package dk.sdu.mmmi.cbse.common.data.Map;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;


public class TileHeuristic implements Heuristic<Tile> {
    @Override
    public float estimate(Tile current, Tile goal) {
        return Vector2.dst(current.x, current.y, goal.x, goal.y);
    }
}
