package dk.sdu.mmmi.cbse;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import dk.sdu.mmmi.cbse.GameScreen;
import dk.sdu.mmmi.cbse.Enemy;

public class TileMapHelper {

    private TiledMap tiledMap;
    private static GameScreen gameScreen;
    public static final float PPM = 32.0f;

    public TileMapHelper(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public OrthogonalTiledMapRenderer setupMap() {
        tiledMap = new TmxMapLoader().load("/Users/sk/Documents/Uni/SoftwareEngineering/4Semester/Semester-Project-SE-Semester4-f22/TowerDefenseOSGi/OSGiCore/src/main/assets/maps/map1.tmx");
        parseMapObjects(tiledMap.getLayers().get("objects").getObjects());

//        TiledMapTileLayer tileid = (TiledMapTileLayer)tiledMap.getLayers().get(0);
//        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
//        System.out.print(tileid.getCell(0,0).getTile().getObjects().toString());

        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    public void parseMapObjects(MapObjects mapObjects) {
        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) mapObject);
            }


            if (mapObject instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
                String rectangleName = mapObject.getName();

                boolean findEnemy = rectangleName.equals("enemy");

                if (findEnemy) {
                    Body body = BodyHelperService.createBody(
                            rectangle.getX() + rectangle.getWidth() / 2,
                            rectangle.getY() + rectangle.getHeight() / 2,
                            rectangle.getWidth(),
                            rectangle.getHeight(),
                            false,
                            gameScreen.getWorld());
                    gameScreen.setEnemy(new Enemy(rectangle.getWidth(), rectangle.getHeight(), body));
                }

            }

        }

    }

    private void createStaticBody(PolygonMapObject polygonMapObject) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000f);
        System.out.println(body.getPosition());
        shape.dispose();
    }

    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];
        for (int i = 0; i < vertices.length / 2; i++) {
            Vector2 current = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
            worldVertices[i] = current;
        }
        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }
}
