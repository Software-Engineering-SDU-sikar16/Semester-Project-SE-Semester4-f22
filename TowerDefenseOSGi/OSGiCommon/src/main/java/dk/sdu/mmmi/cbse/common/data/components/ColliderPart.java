package dk.sdu.mmmi.cbse.common.data.components;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.ArrayList;


public class ColliderPart implements EntityPart {
    private ArrayList<ColliderShape> shapes; // list of shapes that make up the collider

    public ColliderPart() {
        shapes = new ArrayList<>();
    }


    public void Add(String name, Circle circle) {
        shapes.add(new ColliderShape(name, circle));
    }

    public void Add(String name, Rectangle rect) {
        shapes.add(new ColliderShape(name, rect));
    }

    public ColliderShape getShape(String Name) { // Returns the shape with the given name
        for (ColliderShape shape : shapes) { // Iterates through all shapes in the list of shapes and returns the shape with the given name
            if (shape.name.equals(Name)) { // If the name of the shape is equal to the given name, return the shape
                return shape; // Return the shape
            }
        }
        return null; // If no shape with the given name is found, return null
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
