package dk.sdu.mmmi.cbse.common.data.Algorithms;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entities.Enemy;
import dk.sdu.mmmi.cbse.common.data.helpers.MouseOperator;

public class EnemyQuadTree extends QuadTree<Entity> {
    public EnemyQuadTree(GameData gameData) {
        super(new Rectangle(0, 0, gameData.getGlobalWidth(), gameData.getGlobalHeight()), 4); // create the quadtree with the global width and height
    }

    public void OnUpdate(GameData gameData, World world) { // update the quadtree
        Clear(); // clear the quadtree
        AddEnemies(gameData, world); // add the enemies to the quadtree
    }

    public void OnRender(GameData gameData) { // render the quadtree

        Vector2 scps = MouseOperator.GetMouseScreenPosition(gameData); // get the mouse screen position


        Rectangle dx = new Rectangle(scps.x, scps.y, 100, 100); // create a rectangle for the mouse position
        Array<Entity> entities = gameData.enemyQuadTree.Query(dx); // get the entities that are in the rectangle

        gameData.GlobalShapeRenderer.begin(ShapeRenderer.ShapeType.Line); // begin the shape renderer
        gameData.GlobalShapeRenderer.setColor(Color.BLACK); // set the color to black
        gameData.GlobalShapeRenderer.rect(dx.x, dx.y, dx.width, dx.height); // draw the rectangle of the rectangle as a rectangle with the width and height of the rectangle
        gameData.GlobalShapeRenderer.setColor(Color.GOLD);
        for (Entity entity : entities) { // for each entity in the array
            if (entity != null) { // if the entity is not null
                gameData.GlobalShapeRenderer.circle(entity.getTransform().getX(), entity.getTransform().getY(), 10); // draw a circle at the entity position with a radius of 10
            }
        }
        gameData.GlobalShapeRenderer.end(); // end the shape renderer

        super.Render(gameData); // render the quadtree
    }

    private void AddEnemies(GameData gameData, World world) { // add the enemies to the quadtree
        world.getEntities(Enemy.class).forEach(enemy -> Insert(enemy.getTransform().getCenteredPosition(), enemy)); // for each enemy in the world insert the enemy into the quadtree
    }


}