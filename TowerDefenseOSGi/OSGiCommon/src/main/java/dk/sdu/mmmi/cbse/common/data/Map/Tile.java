package dk.sdu.mmmi.cbse.common.data.Map;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;


public class Tile {
    public int x;
    public int y;
    public String name;
    public int index = 0;

    public Tile(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
