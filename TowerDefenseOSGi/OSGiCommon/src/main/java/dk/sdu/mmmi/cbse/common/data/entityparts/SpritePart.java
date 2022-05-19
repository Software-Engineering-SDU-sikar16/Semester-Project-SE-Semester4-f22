package dk.sdu.mmmi.cbse.common.data.entityparts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sun.prism.Texture;
import com.sun.xml.internal.ws.wsdl.writer.document.soap.Body;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class SpritePart implements EntityPart{
    protected float x, y, velX, velY, speed;
    protected float width, height;
    protected Body body;
    private Sprite sprite;
    private Texture texture;
    private TextureRegion textureRegion;

    public SpritePart(float x, float y, float width, float height, String fileName){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = new Sprite(new TextureRegion(new Texture(fileName)));
        this.sprite.setSize(width, height);
        this.sprite.setPosition(x, y);
    }

    public Sprite getSprite() {
        return sprite;
    }
    @Override
    public void process(GameData gameData, Entity entity) {
        //process the sprites here (run game loop)

    }



}
