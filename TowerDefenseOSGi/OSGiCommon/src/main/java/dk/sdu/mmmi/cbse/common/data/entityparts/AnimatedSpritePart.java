package dk.sdu.mmmi.cbse.common.data.entityparts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.HashMap;

public class AnimatedSpritePart extends Sprite implements EntityPart{

    TextureRegion[][] textureRegions = null;
    TextureRegion[] animationFrames = null;

    String currentAnimationName = "";
    HashMap<String, Animation<TextureRegion>> animations = new HashMap<String, Animation<TextureRegion>>();
    Animation<TextureRegion> currentAnimation = null;
    float elapsedTime = 0;



    public AnimatedSpritePart()
    {
    }

    public AnimatedSpritePart(float x, float y, int width, int height)
    {
        setSize(width, height);
        setPosition(x, y);
    }


    public AnimatedSpritePart(Texture texture, float x, float y, int width, int height)
    {
        setSize(width, height);
        setPosition(x, y);
        setTexture(texture);
    }

    public void AddAnimation(String AnimationName, Texture AnimationSpriteSheet, int NumberOfRows, int NumberOfColumns, float AnimationSpeed, Animation.PlayMode playMode)
    {
        int width = AnimationSpriteSheet.getWidth() / NumberOfColumns;
        int height = AnimationSpriteSheet.getHeight() / NumberOfRows;
        // assume the individual frames are aligned correctly. in terms of width/height
        textureRegions = TextureRegion.split(AnimationSpriteSheet, width, height);

        setSize(width, height);

        animationFrames = new TextureRegion[NumberOfColumns * NumberOfRows];
        int animationFramesIndex = 0;
        for (int i = 0; i < textureRegions.length; i++)
        {
            for (int j = 0; j < textureRegions[i].length; j++)
            {
                animationFrames[animationFramesIndex++] = textureRegions[i][j];
            }
        }

        Animation<TextureRegion> animation = new Animation(1.0f / AnimationSpeed, animationFrames);
        animation.setPlayMode(playMode);
        animations.put(AnimationName, animation);

        if (currentAnimation == null)
        {
            currentAnimation = animation;
        }
    }
    
    @Override
    public void process(GameData gameData, World world, Entity entity)
    {
    
    }
}
