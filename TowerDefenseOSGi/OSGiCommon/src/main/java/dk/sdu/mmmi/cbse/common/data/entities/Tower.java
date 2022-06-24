package dk.sdu.mmmi.cbse.common.data.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.cbse.common.data.Bullets.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Map.Tile;
import dk.sdu.mmmi.cbse.common.data.Resources;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.components.AnimatedSpritePart;
import dk.sdu.mmmi.cbse.common.data.components.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.components.HealthPart;
import dk.sdu.mmmi.cbse.common.data.components.TileIndexPart;

import java.util.Random;


public class Tower extends Entity {
    public static Random random = new Random();
    public boolean IsShooting = false;
    public Vector2 EnemyPosition;
    public float TurretShootSpeedInSeconds = 3.0f; // lower numbers are faster
    float timeSinceLastBullet = 0;
    ColliderPart colliderPart;
    Circle circle;
    Rectangle rect;
    Rectangle hitRect;
    private Entity TargetEnemy;


    public Tower(int x, int y) {
        super(x, y, 50, 50); // Creates a tower at the given x and y coordinates with a width and height of 50

        Texture turret1 = Resources.LoadTexture("../assets/turrets/4shot.png");

        int width = turret1.getWidth(); // width of the turret
        int height = turret1.getHeight(); // height of the turret

        getTransform().setSize(width, height); // set the size of the turret

        float radius = 120; // radius of the turret
        circle = new Circle(getTransform().getCenterX(), getTransform().getCenterY(), radius); // create a circle for checking whether the enemy is getting close to the turret
        rect = new Rectangle(x, y, width, height);  // create a rectangle for checking whether the enemy is getting close to the turret

        float radiusTimesTwoCircumfrence = 2 * radius; // the circumference of the circle is 2 * radius
        float radiusSquared = radiusTimesTwoCircumfrence * radiusTimesTwoCircumfrence; // the radius squared is the circumference squared divided by 4
        float rectCircleSize = (float) Math.sqrt(radiusSquared); // the size of the rectangle is the square root of the radius squared
        hitRect = new Rectangle(getTransform().getCenterX() - rectCircleSize / 2, getTransform().getCenterY() - rectCircleSize / 2, rectCircleSize, rectCircleSize); // create a rectangle for checking whether the enemy is getting close to the turret

        AnimatedSpritePart animatedSprite = new AnimatedSpritePart(turret1); // create a new animated sprite part
        add(animatedSprite); // add the animated sprite part to the entity

        colliderPart = new ColliderPart(); // create a new collider part
        colliderPart.Add("circle", circle); // add the circle to the collider part
        colliderPart.Add("rect", rect); // add the rectangle to the collider part
        colliderPart.Add("hitRect", hitRect); // add the rectangle to the collider part

        add(colliderPart); // add the collider part to the entity
    }

    @Override
    public void OnCreate(GameData gameData, World world) {

    }

    private void SpawnBullet(GameData gameData) {
        if (IsShooting) { // if the turret is shooting
            Bullet bullet = gameData.BulletPool.obtain(); // get a bullet from the bullet pool
            Vector2 bulletPos = new Vector2(getTransform().getX() + getTransform().getWidth() / 4, getTransform().getY() + getTransform().getHeight() / 2 - 2); // get the position of the bullet (the center of the turret)

            bullet.fireBullet(bulletPos, EnemyPosition); // fire the bullet
            gameData.ActiveBullets.add(bullet); // add the bullet to the active bullets list
        }
    }

    @Override
    public void OnRender(GameData gameData, World world) { // render the turret
        Vector2 mousePosition = gameData.mouseOperator.GetMouseWorldPosition(gameData); // get the mouse position
        if (rect.contains(mousePosition)) { // if the mouse is inside the turret
            gameData.GlobalShapeRenderer.begin(ShapeRenderer.ShapeType.Line); // begin drawing lines
            gameData.GlobalShapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1); // set the color
            if (IsShooting) { // if the turret is shooting (the mouse is inside the turret and the mouse is pressed)
                gameData.GlobalShapeRenderer.setColor(0.0f, 1.0f, 0.0f, 1);
            }
            gameData.GlobalShapeRenderer.circle(circle.x, circle.y, circle.radius); // draw the circle
            gameData.GlobalShapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1); // set the color
            gameData.GlobalShapeRenderer.end(); // end drawing lines
        }

    }

    @Override
    public void OnUpdate(GameData gameData, World world) {
        if (TargetEnemy == null) { // if the turret has no target enemy (no enemy is close to the turret)
            return;
        }
        HealthPart healthPart = TargetEnemy.getPart(HealthPart.class); // get the health part of the target enemy
        if (healthPart == null || healthPart != null && healthPart.IsDead()) { // if the target enemy is dead or the target enemy has no health part
            return; // return (the turret has no target enemy)
        }


        if (IsShooting) { // if the turret is shooting (the mouse is inside the turret and the mouse is pressed)
            timeSinceLastBullet += Gdx.graphics.getDeltaTime(); // add the time since the last bullet was fired to the time since the last bullet was fired
            if (timeSinceLastBullet > TurretShootSpeedInSeconds + (random.nextInt(5000) * 0.01f)) { // if the time since the last bullet was fired is greater than the time it takes to shoot plus a random number between 0 and 5000 milliseconds
                SpawnBullet(gameData); // spawn a bullet (the turret is shooting)
                timeSinceLastBullet = 0; // reset the time since the last bullet was fired
                TargetEnemy = null; // reset the target enemy (the turret has no target enemy)
            } else {
            }
        }
    }

    @Override
    public void OnDispose(GameData gameData, World world) {

    }


    public void EnemyIsClose(GameData gameData, Entity enemy) { // if the enemy is close to the turret
        this.TargetEnemy = enemy; // set the target enemy to the enemy (the turret has a target enemy)
        if (enemy == null) { // if the enemy is null (the enemy is not close to the turret)
            IsShooting = false; // set the turret to not shooting (the turret is not shooting)
        } else { // if the enemy is not null (the enemy is close to the turret)
            IsShooting = true; // set the turret to shooting (the turret is shooting)


            TileIndexPart tileIndexPart = TargetEnemy.getPart(TileIndexPart.class); // get the tile index part of the target enemy
            if (tileIndexPart == null) { // if the target enemy has no tile index part
                return;  // return (the turret has no target enemy)
            }

            // be smart and look ahead one tile and shoot at the one tile ahead of the enemy!
            int tileIndex = MathUtils.clamp(tileIndexPart.TileIndex + 1, 0, gameData.GameMapPath.getCount() - 1); // get the tile index of the enemy plus one (the tile index of the enemy plus one)
            Tile tile = gameData.GameMapPath.get(tileIndex); // get the tile at the tile index of the enemy plus one (the tile at the tile index of the enemy plus one)
            // can do smarter ai, but this is fine for now.

            EnemyPosition = new Vector2(tile.x, tile.y); // set the enemy position to the tile position (the enemy is close to the turret)
        }
    }

    @Override
    public void OnCollision(GameData gameData, World world, Entity other) {
        EnemyIsClose(gameData, other);
    }
}
