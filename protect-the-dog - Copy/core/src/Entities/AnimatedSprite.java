package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.spine.AnimationState;
import helper.Constants;

import java.util.HashMap;


public class AnimatedSprite extends Entity
{
	AnimationState animationState;
	AnimationState.TrackEntry CurrentTrack;
	
	// Non Spine custom animated spritesheets
	TextureRegion[][] TextureRegions = null;
	TextureRegion[] AnimationFrames = null;
	
	String currentAnimationName = "";
	HashMap<String, Animation<TextureRegion>> Animations = new HashMap<String, Animation<TextureRegion>>();
	Animation<TextureRegion> currentAnimation = null;
	float elapsedTime = 0;
	
	
	// this uses a custom lookup into a texture atlas
	public AnimatedSprite()
	{
	}
	
	// this uses a custom lookup into a texture atlas
	public AnimatedSprite(float x, float y, int width, int height)
	{
		setSize(width, height);
		setPosition(x, y);
	}
	
	
	public AnimatedSprite(Texture texture, float x, float y, int width, int height)
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
		TextureRegions = TextureRegion.split(AnimationSpriteSheet, width, height);
		
		setSize(width, height);
		
		AnimationFrames = new TextureRegion[NumberOfColumns * NumberOfRows];
		int animationFramesIndex = 0;
		for (int i = 0; i < TextureRegions.length; i++)
		{
			for (int j = 0; j < TextureRegions[i].length; j++)
			{
				AnimationFrames[animationFramesIndex++] = TextureRegions[i][j];
			}
		}
		
		Animation<TextureRegion> animation = new Animation(1.0f / AnimationSpeed, AnimationFrames);
		animation.setPlayMode(playMode);
		Animations.put(AnimationName, animation);
		
		if (currentAnimation == null)
		{
			currentAnimation = animation;
		}
	}
	
	public void SetAnimation(String AnimationName)
	{
		if (AnimationName == null || currentAnimation == null)
		{
			return;
		}
		
		if (Animations.containsKey(AnimationName))
		{
			currentAnimation = Animations.get(AnimationName);
			currentAnimationName = AnimationName;
		}
	}
	
	public void SetAnimationSpeed(float AnimationSpeed)
	{
		if (currentAnimation == null && currentAnimation.getFrameDuration() == 1 / AnimationSpeed)
		{
			return;
		}
		currentAnimation.setFrameDuration(1 / AnimationSpeed);
	}
	
	
	public float getEntityY()
	{
		return getY() + (getHeight() / 2);
	}

	public float getEntityX()
	{
		return getX() + (getWidth() / 2);
	}
	
	public Vector2 getCenteredPosition()
	{
		return new Vector2(getEntityX(), getEntityY());
	}
	
	
	@Override
	public void OnCreate()
	{
	
	}
	
	@Override
	public void OnRender()
	{
		if (!this.IsEnabled()) return;
		
		if (currentAnimation == null && getTexture() == null) // end early
		{
			return;
		}
		
		Constants.batch.begin();
		
		
		if (currentAnimation != null)
		{
			TickAndDrawAnimations();
		}
		
		if (getTexture() != null)
		{
			Constants.batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
		}
		
		Constants.batch.end();
	}
	
	@Override
	public void OnUpdate(float DeltaTime)
	{
		if (!this.IsEnabled()) return;
	}
	
	
	private void TickAndDrawAnimations()
	{
		elapsedTime += Gdx.graphics.getDeltaTime();
		Constants.batch.draw(currentAnimation.getKeyFrame(elapsedTime, true), getX(), getY(), getWidth(), getHeight());
	}
	
	
}
