package dk.sdu.mmmi.cbse.osgitower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SpriteLoaderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.AnimatedSpritePart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.HashMap;
import java.util.Random;

public class TowerPlugin implements IGamePluginService {

    private String towerID;

    @Override
    public void start(GameData gameData, World world) {
        Entity tower = createTower(gameData);
        towerID = world.addEntity(tower);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(towerID);
    }

    private Entity createTower(GameData gameData) {
        Entity towerThing = new Tower();
        towerThing.add(new LifePart(100));
//        tower.add(new AnimatedSpritePart(new Texture("tower.png"), 1, 1, 0.1f));
//        SpriteLoaderPart spriteLoaderPart = new SpriteLoaderPart("turrets/4shot.png", 2*16, 9*16, 16, 16, false, false);
        SpriteLoaderPart spriteLoaderPart = new SpriteLoaderPart("turrets/4shot.png");
        towerThing.add(spriteLoaderPart);
        return towerThing;
    }

    public static Array<Tower> Turrets = new Array<Tower>();
    public static Array<AnimatedSpritePart> bullets = new Array<AnimatedSpritePart>(100); // global list of bullets //make a bullet service?
    public static HashMap<Vector2, Tower> turretPositions = new HashMap<Vector2, Tower>();
    public static Random random = new Random();
    public boolean IsShooting = false;
    public Vector2 EnemyPosition;
    public float TurretShootSpeedInSeconds = 1.5f; // lower numbers are faster
    public Rectangle hitRect;
    float timeSinceLastBullet = 0;
    public static int TurretPriceInCoins = 500;
    public Circle circle; // this circle is for checking whether the enemy is getting close to the turret.
    public Rectangle rect; // this rectangle is for checking whether the mouse is hovering over the turret
    private Entity TargetEnemy;

    public void Tower(int x, int y)
    {
        AnimatedSpritePart.super(x, y, 50, 50);

        Texture turret1 = Resources.LoadTexture("turrets/4shot.png");

        int width = turret1.getWidth();
        int height = turret1.getHeight();

        setSize(width, height);

        float radius = 100;
        circle = new Circle(getEntityX(), getEntityY(), radius);
        rect = new Rectangle(x, y, width, height);

        float radiusTimesTwoCircumfrence = 2 * radius;
        float radiusSquared = radiusTimesTwoCircumfrence * radiusTimesTwoCircumfrence;
        float rectCircleSize = (float) Math.sqrt(radiusSquared);
        hitRect = new Rectangle(getEntityX() - rectCircleSize / 2, getEntityY() - rectCircleSize / 2, rectCircleSize, rectCircleSize);


        setTexture(turret1);
        Turrets.add(this);
        turretPositions.put(new Vector2(x, y), this);
    }

    private void SpawnBullet()
    {
        if (IsShooting)
        {
            Bullet bullet = Constants.BulletPool.obtain();
            Vector2 bulletPos = new Vector2(getX() + getWidth() / 4, getY() + getHeight() / 2 - 2);

            bullet.fireBullet(bulletPos, EnemyPosition);
            Constants.ActiveBullets.add(bullet);
        }
    }

    @Override
    public void OnUpdate(float DeltaTime)
    {
        super.OnUpdate(DeltaTime);

        if (TargetEnemy == null || TargetEnemy.IsDead())
        {
            return;
        }

        if (IsShooting)
        {
            timeSinceLastBullet += Gdx.graphics.getDeltaTime();
            if (timeSinceLastBullet > TurretShootSpeedInSeconds + (random.nextInt(500) * 0.001f))
            {
                SpawnBullet();
                timeSinceLastBullet = 0;
            }
            else
            {

            }
        }

    }


    @Override
    public void OnRender()
    {
        super.OnRender();


/*		{
			Constants.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			if (IsShooting)
			{
				Constants.shapeRenderer.setColor(0.0f, 1.0f, 0.0f, 1);
			}
			else
			{
				Constants.shapeRenderer.setColor(1.0f, 0.0f, 0.0f, 1);
			}
			Constants.shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
			Constants.shapeRenderer.setColor(1.0f, 0.0f, 1.0f, 1);
			Constants.shapeRenderer.rect(hitRect.x, hitRect.y, hitRect.width, hitRect.height);
			Constants.shapeRenderer.end();
		}*/



	/*	Constants.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		Constants.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1);
		Constants.shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
		Constants.shapeRenderer.end();*/

        Vector2 mousePosition = MouseOperator.GetMouseWorldPosition();
        if (rect.contains(mousePosition))
        {
            Constants.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            Constants.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1);
            if (IsShooting)
            {
                Constants.shapeRenderer.setColor(0.0f, 1.0f, 0.0f, 1);
            }
            Constants.shapeRenderer.circle(circle.x, circle.y, circle.radius);
            Constants.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1);
            Constants.shapeRenderer.end();
        }

    }

    public static void TryBuildTurret()
    {

        Vector2 TilePos = MouseOperator.GetTilePositionUnderMousePosition();

        Constants.MouseTileSelector.setPosition(TilePos.x, TilePos.y);
        Constants.MouseTileSelector.setSize(Constants.TileMapHelper.TilePixelWidth, Constants.TileMapHelper.TilePixelHeight);


        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !Constants.IsBuildingTurret)
        {
            if (Constants.TileMapHelper.IsTileAtPositionAValidBuildableTile(TilePos))
            {
                if (Constants.Coins - TurretPriceInCoins < 0) // if the user has money to build this turret
                {
                    return;
                }
                Constants.Coins -= TurretPriceInCoins;

                if (!turretPositions.containsKey(TilePos))
                {
                    new Turret((int) TilePos.x, (int) TilePos.y);
                }
            }
        }
    }

    public void EnemyIsClose(EnemyEntity enemy)
    {
        this.TargetEnemy = enemy;
        if (enemy == null)
        {
            IsShooting = false;
        }
        else
        {
            IsShooting = true;

            // be smart and look ahead one tile and shoot at the one tile ahead of the enemy!
            int tileIndex = MathUtils.clamp(enemy.TileIndex + 1, 0, Constants.GameMapPath.getCount() - 1);
            Tile tile = Constants.GameMapPath.get(tileIndex);
            // can do smarter ai, but this is fine for now.

            EnemyPosition = new Vector2(tile.x, tile.y);
        }
    }
}
