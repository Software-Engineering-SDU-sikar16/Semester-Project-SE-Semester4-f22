package dk.sdu.mmmi.cbse.osgitower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.AnimatedSpritePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SpriteLoaderPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.osgimap.MapService;
import dk.sdu.mmmi.cbse.osgimap.helper.MouseOperator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TowerPlugin implements IGamePluginService {

    private String towerID;
    public static boolean isBuildingTurret = false;

    // from gibson
    public static Array<TowerPlugin> towers = new Array<>();
    public static ArrayList<Entity> towerTest = new ArrayList<>();
    public static Array<AnimatedSpritePart> bullets = new Array<>(100); // global list of bullets //make a bullet service?
    public static HashMap<Vector2, TowerPlugin> turretPositions = new HashMap<>();
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
    // end from gibson

    public TowerPlugin(){}

    @Override
    public void start(GameData gameData, World world) {
        Entity tower = createTower(gameData, world, gameData.getDisplayWidth() / 2f, gameData.getDisplayHeight() / 2f);
//        Entity tower = towers.get(0);
//        Entity tower = TryBuildTurret(gameData, world);
        towerID = world.addEntity(tower);
//        TryBuildTurret(gameData, world);

//        createTower(gameData, world, gameData.getDisplayWidth() / 2f, gameData.getDisplayHeight() / 2f);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(towerID);
    }

    private Entity createTower(GameData gameData, World world, float x, float y) {
//        float x = gameData.getDisplayWidth() / 2f;
//        float y = gameData.getDisplayHeight() / 2f;
        float radians = 3.1415f / 2;

        Entity towerThing = new Tower();
        towerThing.add(new LifePart(100));
        towerThing.add(new PositionPart(x, y, radians));
        SpriteLoaderPart spriteLoaderPart = new SpriteLoaderPart("turrets/4shot.png", 0*16, 0*16, 41, 37, false, false);
        towerThing.add(spriteLoaderPart);
        towers.add(this);
        world.addEntity(towerThing);
        turretPositions.put(new Vector2(x, y), this);

//        Testing mouseoperator and mousetileselctor here and it works
//        Vector2 TilePos = MouseOperator.GetTilePositionUnderMousePosition();
//        gameData.MouseTileSelector.setPosition(TilePos.x, TilePos.y);
//        gameData.MouseTileSelector.setSize(32, 32);
//        System.out.println("TilePos: " + TilePos);
        return towerThing;
    }

//    public void Tower(int x, int y)
//    {
//        AnimatedSprite.super(x, y, 50, 50);
//
//        Texture turret1 = Resources.LoadTexture("turrets/4shot.png");
//
//        int width = turret1.getWidth();
//        int height = turret1.getHeight();
//
//        setSize(width, height);
//
//        float radius = 100;
//        circle = new Circle(getEntityX(), getEntityY(), radius);
//        rect = new Rectangle(x, y, width, height);
//
//        float radiusTimesTwoCircumfrence = 2 * radius;
//        float radiusSquared = radiusTimesTwoCircumfrence * radiusTimesTwoCircumfrence;
//        float rectCircleSize = (float) Math.sqrt(radiusSquared);
//        hitRect = new Rectangle(getEntityX() - rectCircleSize / 2, getEntityY() - rectCircleSize / 2, rectCircleSize, rectCircleSize);
//
//
//        setTexture(turret1);
//        Turrets.add(this);
//        turretPositions.put(new Vector2(x, y), this);
//    }
//
//    private void SpawnBullet()
//    {
//        if (IsShooting)
//        {
//            Bullet bullet = Constants.BulletPool.obtain();
//            Vector2 bulletPos = new Vector2(getX() + getWidth() / 4, getY() + getHeight() / 2 - 2);
//
//            bullet.fireBullet(bulletPos, EnemyPosition);
//            Constants.ActiveBullets.add(bullet);
//        }
//    }
//
//    @Override
//    public void OnUpdate(float DeltaTime)
//    {
//        super.OnUpdate(DeltaTime);
//
//        if (TargetEnemy == null || TargetEnemy.IsDead())
//        {
//            return;
//        }
//
//        if (IsShooting)
//        {
//            timeSinceLastBullet += Gdx.graphics.getDeltaTime();
//            if (timeSinceLastBullet > TurretShootSpeedInSeconds + (random.nextInt(500) * 0.001f))
//            {
//                SpawnBullet();
//                timeSinceLastBullet = 0;
//            }
//            else
//            {
//
//            }
//        }
//
//    }
//
//
//    @Override
//    public void OnRender()
//    {
//        super.OnRender();
//
//
///*		{
//			Constants.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//			if (IsShooting)
//			{
//				Constants.shapeRenderer.setColor(0.0f, 1.0f, 0.0f, 1);
//			}
//			else
//			{
//				Constants.shapeRenderer.setColor(1.0f, 0.0f, 0.0f, 1);
//			}
//			Constants.shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
//			Constants.shapeRenderer.setColor(1.0f, 0.0f, 1.0f, 1);
//			Constants.shapeRenderer.rect(hitRect.x, hitRect.y, hitRect.width, hitRect.height);
//			Constants.shapeRenderer.end();
//		}*/
//
//
//
//	/*	Constants.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//		Constants.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1);
//		Constants.shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
//		Constants.shapeRenderer.end();*/
//
//        Vector2 mousePosition = MouseOperator.GetMouseWorldPosition();
//        if (rect.contains(mousePosition))
//        {
//            Constants.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//            Constants.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1);
//            if (IsShooting)
//            {
//                Constants.shapeRenderer.setColor(0.0f, 1.0f, 0.0f, 1);
//            }
//            Constants.shapeRenderer.circle(circle.x, circle.y, circle.radius);
//            Constants.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1);
//            Constants.shapeRenderer.end();
//        }
//
//    }
//
    public Entity TryBuildTurret(GameData gameData, World world)
    {
        Vector2 TilePos = MouseOperator.GetTilePositionUnderMousePosition();
        gameData.MouseTileSelector.setPosition(TilePos.x, TilePos.y);
        gameData.MouseTileSelector.setSize(32, 32);
        System.out.println("TilePos: " + TilePos);
//        createTower(gameData, world, TilePos.x, TilePos.y);
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !isBuildingTurret)
        {
            System.out.println("Left Click");
//            createTower(gameData, world, TilePos.x, TilePos.y);
            if (MapService.IsTileAtPositionAValidBuildableTile(TilePos))
            {
                createTower(gameData, world, TilePos.x, TilePos.y);
//                new Tower();
//
//                if (Constants.Coins - TurretPriceInCoins < 0) // if the user has money to build this turret
//                {
//                    return;
//                }
//                Constants.Coins -= TurretPriceInCoins;
//
//                if (!turretPositions.containsKey(TilePos))
//                {
//                    new Tower((int) TilePos.x, (int) TilePos.y);
//                }
            }
        }
        return null;
    }

//    public void EnemyIsClose(Entity enemy)
//    {
//        this.TargetEnemy = enemy;
//        if (enemy == null)
//        {
//            IsShooting = false;
//        }
//        else
//        {
//            IsShooting = true;
//
//            // be smart and look ahead one tile and shoot at the one tile ahead of the enemy!
//            int tileIndex = MathUtils.clamp(enemy.TileIndex + 1, 0, MapService.gameMapPath.getCount() - 1);
//            Tile tile = MapService.gameMapPath.get(tileIndex);
//            // can do smarter ai, but this is fine for now.
//
//            EnemyPosition = new Vector2(tile.x, tile.y);
//        }
//    }
}
