package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.esotericsoftware.spine.*;

import java.util.HashMap;


public class AnimatedSprite extends Entity
{
	
	public static SpriteBatch batch;
	
	// Spine specific
	public static SkeletonRenderer skeletonRenderer;
	Skeleton skeleton;
	AnimationState animationState;
	AnimationState.TrackEntry CurrentTrack;
	
	
	// Non Spine custom animated spritesheets
	TextureRegion[][] TextureRegions = null;
	TextureRegion[] AnimationFrames = null;
	
	String currentAnimationName = "";
	HashMap<String, Animation<TextureRegion>> Animations = new HashMap<String, Animation<TextureRegion>>();
	Animation<TextureRegion> currentAnimation = null;
	float elapsedTime = 0;
	
	
	// This uses the spine animation system.
	public AnimatedSprite(String TextureAtlas, String SkeletonDataJSON, float x, float y)
	{
		batch = new SpriteBatch();
		skeletonRenderer = new SkeletonRenderer();
		
		// YOU MUSE USE Spine 3.7 exporter for this version of libgdx
		// per entity
		TextureAtlas playerAtlas = new TextureAtlas(Gdx.files.internal(TextureAtlas));
		SkeletonJson json = new SkeletonJson(playerAtlas);
		SkeletonData playerSkeletonData = json.readSkeletonData(Gdx.files.internal(SkeletonDataJSON));
		AnimationStateData playerAnimationData = new AnimationStateData(playerSkeletonData);
		
		
		skeleton = new Skeleton(playerSkeletonData);
		
		skeleton.setPosition(x, y);
		skeleton.updateWorldTransform();
		
		animationState = new AnimationState(playerAnimationData);
		
		
		CurrentTrack = animationState.setAnimation(0, "animation", false); // trackIndex, name, loop
		
		skeleton.updateWorldTransform();
		
	}
	
	public AnimatedSprite(String TextureAtlas, String SkeletonDataJSON)
	{
		this(TextureAtlas, SkeletonDataJSON, 0, 0);
	}
	
	
	public void SetPosition(float x, float y)
	{
		if (skeleton != null)
		{
			skeleton.updateWorldTransform();
			skeleton.setPosition(x, y);
		}
		
	}
	
	// this uses a custom lookup into a texture atlas
	public AnimatedSprite()
	{
	}
	
	// this uses a custom lookup into a texture atlas
	public AnimatedSprite(int x, int y, int width, int height)
	{
		setSize(width, height);
		setX(x);
		setY(y);
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
		if (currentAnimation == null)
		{
			return;
		}
		currentAnimation.setFrameDuration(1 / AnimationSpeed);
	}
	
	
	@Override
	public void OnCreate()
	{
	
	}
	
	@Override
	public void OnRender()
	{
		if (skeleton == null && currentAnimation == null) // end early
		{
			return;
		}
		
		batch.begin();
		if (skeleton != null)
		{
			skeletonRenderer.draw(batch, skeleton);
		}
		
		if (currentAnimation != null)
		{
			TickAndDrawAnimations();
		}
		
		batch.end();
	}
	
	
	private void TickAndDrawAnimations()
	{
		elapsedTime += Gdx.graphics.getDeltaTime();
		batch.draw(currentAnimation.getKeyFrame(elapsedTime, true), getX(), getY(), getWidth(), getHeight());
	}
	
	@Override
	public void OnUpdate(float DeltaTime)
	{
		if (skeleton != null)
		{
			skeleton.updateWorldTransform();
			animationState.update(DeltaTime);
			animationState.apply(skeleton);
		}
	}
	
	public void PlayAnimation()
	{
		if (skeleton != null)
		{
			CurrentTrack.setTrackTime(0);
		}
	}
	
	
}
