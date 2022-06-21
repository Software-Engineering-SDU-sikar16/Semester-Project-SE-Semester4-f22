package dk.sdu.mmmi.cbse.common.data.helpers;

import com.badlogic.gdx.math.Vector2;

public class Util {

    public static float Clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public static int Clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public static double distance(Vector2 object1, Vector2 object2) {
        return Math.sqrt(Math.pow((object2.x - object1.x), 2) + Math.pow((object2.y - object1.y), 2));
    }
}
