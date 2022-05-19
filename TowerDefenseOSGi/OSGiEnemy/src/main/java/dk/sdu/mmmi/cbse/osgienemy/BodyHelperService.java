package dk.sdu.mmmi.cbse.osgienemy;

import com.badlogic.gdx.physics.box2d.*;

public class BodyHelperService {
    public static final float PPM = 32.0f;

    public static Body createBody(float x, float y, float width, float height, boolean isStatic, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / PPM, y / PPM);
        Body body = world.createBody(bodyDef);

        //add color to body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.5f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / PPM / 2, height / PPM / 2);


        shape.setAsBox(width / PPM / 2, height / PPM / 2);

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }
}
