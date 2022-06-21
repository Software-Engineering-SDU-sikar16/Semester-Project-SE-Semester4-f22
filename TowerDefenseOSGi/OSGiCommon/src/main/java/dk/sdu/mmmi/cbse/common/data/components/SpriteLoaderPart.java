package dk.sdu.mmmi.cbse.common.data.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public class SpriteLoaderPart implements EntityPart {
    private SpriteBatch spriteBatch;
    private Sprite sprite;
    private Texture texture;
    private String initPath = "../assets/";
    private String path;
    private TextureRegion textureRegion;
    private boolean flipX = false;
    private boolean flipY = false;
    private int x;
    private int y;
    private int width;
    private int height;

    public SpriteLoaderPart(String path, int x, int y, int width, int height, boolean flipX, boolean flipY) {
        this.path = (initPath + path);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.flipX = flipX;
        this.flipY = flipY;
    }

    public SpriteLoaderPart(String path) {
        texture = new Texture(initPath + path);
        sprite = new Sprite(texture);
        spriteBatch = new SpriteBatch();

    }

    public void createSprite() {
        texture = new Texture(this.path);
        spriteBatch = new SpriteBatch();
        this.textureRegion = new TextureRegion(texture, this.x, this.y, this.width, this.height);
        this.textureRegion.flip(this.flipX, this.flipY);
        sprite = new Sprite(this.textureRegion);
    }

    public void drawSprite(float x, float y) {
        if (spriteBatch == null) {
            createSprite();
        }
        spriteBatch.begin();
        sprite.setPosition(x, y);
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void OnCreate(GameData gameData, World world, Entity entity) {

    }

    @Override
    public void OnUpdate(GameData gameData, World world, Entity entity) {

    }

    @Override
    public void OnRender(GameData gameData, World world, Entity entity) {
        PositionPart pp = entity.getPart(PositionPart.class);
        drawSprite(pp.getX(), pp.getY());
    }

    public boolean isFlipX() {
        return flipX;
    }

    public void setFlipX(boolean flipX) {
        this.textureRegion.flip(flipX, this.flipY);
        this.flipX = flipX;
    }

    public boolean isFlipY() {

        return flipY;
    }

    public void setFlipY(boolean flipY) {
        this.textureRegion.flip(this.flipX, flipY);
        this.flipY = flipY;
    }

}
