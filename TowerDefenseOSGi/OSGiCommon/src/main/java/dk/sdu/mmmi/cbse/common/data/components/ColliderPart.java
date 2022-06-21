package dk.sdu.mmmi.cbse.common.data.components;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.ArrayList;


public class ColliderPart implements EntityPart {
    private ArrayList<ColliderShape> shapes;

    public ColliderPart() {
        shapes = new ArrayList<>();
    }

    public ColliderPart(String name, Rectangle rect) {
        this();
        Add(name, rect);
    }


    public ColliderPart(String name, Circle circle) {
        this();
        Add(name, circle);
    }

    public void Add(String name, Circle circle) {
        shapes.add(new ColliderShape(name, circle));
    }

    public void Add(String name, Rectangle rect) {
        shapes.add(new ColliderShape(name, rect));
    }

    public ColliderShape getShape(String Name) {
        for (ColliderShape shape : shapes) {
            if (shape.name.equals(Name)) {
                return shape;
            }
        }
        return null;
//		return shapes.stream().findFirst().filter(shape -> shape.name.equals(Name)).orElse(null);
    }

    @Override
    public void OnCreate(GameData gameData, World world, Entity entity) {

    }

    @Override
    public void OnUpdate(GameData gameData, World world, Entity entity) {

    }

    @Override
    public void OnRender(GameData gameData, World world, Entity entity) {

    }
}
