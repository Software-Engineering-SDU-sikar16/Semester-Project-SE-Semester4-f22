package dk.sdu.mmmi.cbse.common.data.components;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class ColliderShape {
    String name;
    Rectangle rect;
    Circle circle;
    Shapes shape;

    public ColliderShape(String name, Rectangle rect) {
        this.name = name;
        this.rect = rect;
        this.shape = Shapes.RECTANGLE;
    }

    public ColliderShape(String name, Circle circle) {
        this.name = name;
        this.circle = circle;
        this.shape = Shapes.CIRCLE;
    }

    public Rectangle getRect() {
        return rect;
    }

    public Circle getCircle() {
        return circle;
    }

    private enum Shapes {RECTANGLE, CIRCLE}
}
