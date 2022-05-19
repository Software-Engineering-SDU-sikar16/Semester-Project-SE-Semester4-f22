package objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

import static helper.Constants.PPM;

public class Enemy extends GameEntity{

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

//        checkUserInput();
        moveEnemy();

    }

    @Override
    public void render(SpriteBatch batch) {

    }

//    private void checkUserInput() {
//        velX = 0;
//        velY = 0;
//        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//            velX = 1;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//            velX = -1;
//        }
//
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            velY = -1;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//            velY = 1;
//        }
//
//        body.setLinearVelocity(velX * speed, velY* speed);
//
//    }

    private void moveEnemy(){
        System.out.println("move enemy");
        //System.out.println(body.getPosition());
        System.out.println(body.getWorldCenter());

        //check if enemy is not moving
        if(body.getLinearVelocity().x == 0 && body.getLinearVelocity().y == 0){
            System.out.println("enemy is not moving");
        }

        // check if enemy is not moving position
        if(body.getPosition().x == body.getWorldCenter().x && body.getPosition().y == body.getWorldCenter().y){
            System.out.println("enemy is not moving position");
            body.setLinearVelocity(1, 0);
        } else {
            body.setLinearVelocity(0, 1); // move enemy down
        }

        // if enemy is not moving position move the enemy down or right
        if(body.getPosition().x == body.getWorldCenter().x && body.getPosition().y == body.getWorldCenter().y){
            body.applyLinearImpulse(0, -0.1f, body.getWorldCenter().x, body.getWorldCenter().y, true);
        }


        //create a checker which checks if body.getPosition is the same for more than 2 seconds



       // move body 2 pixels to the right
        //body.setTransform(20, body.getPosition().y, 0);
       // body.setLinearVelocity(2, -1);
        // body.setAngularVelocity(-8);
        // body.setTransform(body.getWorldCenter().x + 0.1f, body.getWorldCenter().y, 0);

       // body.setLinearVelocity(0, -1); move down

//        if (body.getPosition().y < 0.5f){
//            body.setLinearVelocity(0, 1);   //move up
//        }





//        if(body.getPosition().x < 0){
//            body.setLinearVelocity(-speed, 0);
//        } else if(body.getPosition().x > 0){
//            body.setLinearVelocity(speed, 0);
//        } else if(body.getPosition().y < 0){
//            body.setLinearVelocity(0, -speed);
//        } else if(body.getPosition().y > 0){
//            body.setLinearVelocity(0, speed);
//        }

        // check if enemy is not moving


        // if enemy can not go right go dow
    }


}
