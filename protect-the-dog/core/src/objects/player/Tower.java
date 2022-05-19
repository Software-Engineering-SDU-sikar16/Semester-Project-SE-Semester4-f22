package objects.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

import static helper.Constants.PPM;

public class Tower extends TowerEntity {

    public Tower(float width, float height, Body body) {
        super(width, height, body);
    }

    @Override
    public void update() {
        x = body.getPosition().x;
        y = body.getPosition().y;
        this.getSprite().setPosition(x, y);
    }

    @Override
    public void render(SpriteBatch batch) {

    }
}
