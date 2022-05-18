package dk.sdu.mmmi.cbse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class Enemy extends GameEntity {
    public static final float PPM = 32.0f;
    private int jumpCounter;

    public Enemy(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 4f;
        this.jumpCounter = 0;
    }

    @Override
    public void update() {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;
        this.getSprite().setPosition(x, y);

        checkUserInput();
        moveEnemy();

    }

    @Override
    public void render(SpriteBatch batch) {

    }

    private void checkUserInput() {
        velX = 0;
        velY = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velX = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velX = -1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velY = -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velY = 1;
        }




//        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && jumpCounter < 2) {
//            float force = body.getMass() * 18;
//            body.applyLinearImpulse(new Vector2(0, force), body.getPosition(), true);
//        }
        body.setLinearVelocity(velX * speed, velY* speed);

    }

    private void moveEnemy(){
        if(body.getPosition().x < 0){
            body.setLinearVelocity(-speed, 0);
        }
        if(body.getPosition().x > 0){
            body.setLinearVelocity(speed, 0);
        }
    }
}