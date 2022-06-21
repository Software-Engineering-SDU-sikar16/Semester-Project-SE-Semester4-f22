package dk.sdu.mmmi.cbse.common.data.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.HashMap;

public class AnimatedSpritePart implements EntityPart {
    // Non Spine custom animated spritesheets
    TextureRegion[][] TextureRegions = null;
    TextureRegion[] AnimationFrames = null;

    String currentAnimationName = "";
    HashMap<String, Animation<TextureRegion>> Animations = new HashMap<String, Animation<TextureRegion>>();
    Animation<TextureRegion> currentAnimation = null;
    float elapsedTime = 0;

    Texture texture;

    public AnimatedSpritePart(Texture texture) {
        this.texture = texture;
    }


    public AnimatedSpritePart(Entity entity, String AnimationName, Texture AnimationSpriteSheet, int NumberOfRows, int NumberOfColumns, float AnimationSpeed, Animation.PlayMode playMode) {
        AddAnimation(entity, AnimationName, AnimationSpriteSheet, NumberOfRows, NumberOfColumns, AnimationSpeed, playMode);
    }

    public void AddAnimation(Entity entity, String AnimationName, Texture AnimationSpriteSheet, int NumberOfRows, int NumberOfColumns, float AnimationSpeed, Animation.PlayMode playMode) {
        int width = AnimationSpriteSheet.getWidth() / NumberOfColumns;
        int height = AnimationSpriteSheet.getHeight() / NumberOfRows;
        // assume the individual frames are aligned correctly. in terms of width/height
        TextureRegions = TextureRegion.split(AnimationSpriteSheet, width, height);

        PositionPart transform = entity.getTransform();
        if (transform != null) {
            transform.setSize(width, height);
        }

        AnimationFrames = new TextureRegion[NumberOfColumns * NumberOfRows];
        int animationFramesIndex = 0;
        for (int i = 0; i < TextureRegions.length; i++) {
            for (int j = 0; j < TextureRegions[i].length; j++) {
                AnimationFrames[animationFramesIndex++] = TextureRegions[i][j];
            }
        }

        Animation<TextureRegion> animation = new Animation(1.0f / AnimationSpeed, AnimationFrames);
        animation.setPlayMode(playMode);
        Animations.put(AnimationName, animation);

        if (currentAnimation == null) {
            currentAnimation = animation;
        }
    }

    public void SetAnimation(String AnimationName) {
        if (AnimationName == null || currentAnimation == null) {
            return;
        }

        if (Animations.containsKey(AnimationName)) {
            currentAnimation = Animations.get(AnimationName);
            currentAnimationName = AnimationName;
        }
    }

    public void SetAnimationSpeed(float AnimationSpeed) {
        if (currentAnimation == null && currentAnimation.getFrameDuration() == 1 / AnimationSpeed) {
            return;
        }
        currentAnimation.setFrameDuration(1 / AnimationSpeed);
    }


    public void OnRender(GameData gameData, Entity entity) {
        if (currentAnimation == null && texture == null) // end early
        {
            return;
        }
        PositionPart transform = entity.getTransform();
        if (transform == null) {
            return;
        }

        gameData.GlobalSpriteBatch.begin();
        if (currentAnimation != null) {
            elapsedTime += Gdx.graphics.getDeltaTime();
            gameData.GlobalSpriteBatch.draw(currentAnimation.getKeyFrame(elapsedTime, true), transform.getX(), transform.getY(), transform.getWidth(), transform.getHeight());
        }

        if (texture != null) {
            gameData.GlobalSpriteBatch.draw(texture, transform.getX(), transform.getY(), transform.getWidth(), transform.getHeight());
        }
        gameData.GlobalSpriteBatch.end();

    }

    @Override
    public void OnCreate(GameData gameData, World world, Entity entity) {

    }

    @Override
    public void OnUpdate(GameData gameData, World world, Entity entity) {

    }

    @Override
    public void OnRender(GameData gameData, World world, Entity entity) {
        OnRender(gameData, entity);
    }
}