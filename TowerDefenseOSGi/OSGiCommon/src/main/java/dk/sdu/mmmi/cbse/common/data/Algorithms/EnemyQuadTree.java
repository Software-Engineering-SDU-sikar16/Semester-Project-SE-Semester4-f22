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
        super(new Rectangle(0, 0, gameData.getGlobalWidth(), gameData.getGlobalHeight()), 4);
    }

    public void OnUpdate(GameData gameData, World world) {
        Clear();
        AddEnemies(gameData, world);
    }

    public void OnRender(GameData gameData) {

        Vector2 scps = MouseOperator.GetMouseScreenPosition(gameData);


        Rectangle dx = new Rectangle(scps.x, scps.y, 100, 100);
        Array<Entity> entities = gameData.enemyQuadTree.Query(dx);

        gameData.GlobalShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        gameData.GlobalShapeRenderer.setColor(Color.BLACK);
        gameData.GlobalShapeRenderer.rect(dx.x, dx.y, dx.width, dx.height);
        gameData.GlobalShapeRenderer.setColor(Color.GOLD);
        for (Entity entity : entities) {
            if (entity != null) {
                gameData.GlobalShapeRenderer.circle(entity.getTransform().getX(), entity.getTransform().getY(), 10);
            }
        }
        gameData.GlobalShapeRenderer.end();

        super.Render(gameData);
    }

    private void AddEnemies(GameData gameData, World world) {
        world.getEntities(Enemy.class).forEach(enemy -> Insert(enemy.getTransform().getCenteredPosition(), enemy));
    }


}