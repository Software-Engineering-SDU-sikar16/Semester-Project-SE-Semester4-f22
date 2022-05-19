package dk.sdu.mmmi.cbse.osgienemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class EnemyEntity {
    protected float x, y, velX, velY, speed;
    protected float width, height;
    protected Body body;
    private Sprite sprite;
    private Texture texture;
    private TextureRegion textureRegion;

    public Sprite getSprite() {
        return sprite;
    }

    public EnemyEntity(float width, float height, Body body) {

        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.width = width;
        this.height = height;
        this.body = body;
        this.velX = 0;
        this.velY = 0;
        this.speed = 1;

        this.texture = new Texture("../OSGiCore/assets/images/v3.png");
        this.textureRegion = new TextureRegion(texture, 0, 192, 16,16);
        // flip the texture
        this.textureRegion.flip(true, false);
        this.sprite = new Sprite(textureRegion);
//        this.sprite.setCenter(width/2, height/2);
        //this.sprite.setOriginCenter();
//        this.sprite.setCenter(20, 20);
    }

    public abstract void update();

    public abstract void render(SpriteBatch batch);

    public Body getBody() {
        return body;
    }
}
