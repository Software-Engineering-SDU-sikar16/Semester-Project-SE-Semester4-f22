package dk.sdu.mmmi.cbse.osgienemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Sprite {

    private Vector2 velocity = new Vector2();
    private float speed = 60*2, gravity = 60*1.8f;

    public Enemy(Sprite sprite) {
        super(sprite);
    }

//    @Override
    public void draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public void update(float delta) {

    }

}
