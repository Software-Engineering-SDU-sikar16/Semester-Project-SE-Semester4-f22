package dk.sdu.mmmi.cbse;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class TowerEntity {
    protected float x, y;
    protected float width, height;
    protected Body body;
    private Sprite sprite;
    private Texture texture;
    private TextureRegion textureRegion;

    public Sprite getSprite() {
        return sprite;
    }

    public TowerEntity(float width, float height, Body body) {
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.width = width;
        this.height = height;
        this.body = body;

        this.texture = new Texture("../OSGiCore/assets/images/v3.png");
        this.textureRegion = new TextureRegion(texture, 1, 192, 16,16);
        this.sprite = new Sprite(textureRegion);
    }

    public abstract void update();

    public abstract void render(SpriteBatch batch);

    public Body getBody() {
        return body;
    }
}
