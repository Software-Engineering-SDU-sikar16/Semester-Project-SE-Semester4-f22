package dk.sdu.mmmi.cbse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import dk.sdu.mmmi.cbse.TileMapHelper;
import dk.sdu.mmmi.cbse.Enemy;

public class GameScreen extends ScreenAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    private Enemy enemy;
    public static final float PPM = 32.0f;

    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, 0), false); //gravity is -9.81f in y direction
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();
    }

    private void update() {
        world.step(1 / 60f, 6, 2);
        cameraUpdate(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        enemy.update();

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void cameraUpdate(int width, int height) {
        Vector3 position = camera.position;
        position.x = Math.round(enemy.getBody().getPosition().x * PPM * 10) / 10f;
        position.y = Math.round(enemy.getBody().getPosition().y * PPM * 10) / 10f;

        camera.viewportHeight = height;
        camera.viewportWidth = width;
        // camera.position.set(position);

        camera.position.set(width/2f, height/2f, 0); //by default camera position on (0,0,0)
//        camera.position.set(new Vector3(0, 0, 0));
        camera.update();
    }

    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

        batch.begin();
        // render objects
        this.enemy.getSprite().draw(batch);;
        this.enemy.getSprite().translate(20, 20);


        batch.end();
        box2DDebugRenderer.render(world, camera.combined.scl(PPM));
    }

    public World getWorld() {
        return world;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }



}
