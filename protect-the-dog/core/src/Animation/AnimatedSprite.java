package Animation;

import Entities.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.esotericsoftware.spine.*;
import helper.Constants;


public class AnimatedSprite extends Entity // extends Skeleton
{
	SpriteBatch batch;
	SkeletonRenderer skeletonRenderer;
	
	Skeleton skeleton;
	AnimationState animationState;
	
	AnimationState.TrackEntry CurrentTrack;
	
	
	TextureRegion[][] TextureRegions = null;
	TextureRegion[] AnimationFrames = null;
	
	com.badlogic.gdx.graphics.g2d.Animation animation;
	
	public boolean LoopAllFrames = false;
	public int[][] AnimatedFrameSubset;
	
	
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
		
		Constants.Entities.add(this);
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
	public AnimatedSprite(Texture atlas, int NumberOfRows, int NumberOfColumns)
	{
		int width = atlas.getWidth() / NumberOfColumns;
		int height = atlas.getHeight() / NumberOfRows;
		// assume the frames are aligned correctly.
		TextureRegions = TextureRegion.split(atlas, width, height);
		
		setSize(width, height);
		setX(0);
		setY(0);
		
		
		AnimationFrames = new TextureRegion[NumberOfColumns * NumberOfRows];
		int animationFramesIndex = 0;
		for (int i = 0; i < TextureRegions.length; i++)
		{
			for (int j = 0; j < TextureRegions[i].length; j++)
			{
				AnimationFrames[animationFramesIndex++] = TextureRegions[i][j];
			}
		}
		
		animation = new com.badlogic.gdx.graphics.g2d.Animation(1.0f / 60.0f, AnimationFrames);
		animation.setPlayMode(Animation.PlayMode.LOOP);
		
		batch = new SpriteBatch();
	}
	
	// this uses a custom lookup into a texture atlas
	public AnimatedSprite(Texture[] textures, int width, int height)
	{
		TextureRegions = new TextureRegion[0][textures.length];
		
		int counter = 1;
		for (int i = 0; i < textures.length; i++)
		{
			TextureRegions[0][i] = new TextureRegion(textures[i], counter * width, counter * height, width, height);
			counter++;
		}
		
		setSize(width, height);
		setX(0);
		setY(0);
		
		batch = new SpriteBatch();
	}
	
	@Override
	public void OnCreate()
	{
	
	}
	
	@Override
	public void OnRender()
	{
		batch.begin();
		if (skeleton != null)
		{
			skeletonRenderer.draw(batch, skeleton);
		}
		
		if (TextureRegions != null)
		{
			//draw texture regions
			DrawTextureRegions();
		}
		
		if (AnimationFrames != null)
		{
			batch.draw(AnimationFrames[4], 128, 130, 10, 10);
			batch.draw(AnimationFrames[1], getX(), getY());
			batch.draw(AnimationFrames[2], getX(), getY());
			batch.draw(AnimationFrames[3], getX(), getY());
		}

		batch.end();
	}
	
	int currentRegionX = 0;
	int currentRegionY = 0;
	float elapsedTime = 0;
	
	private void DrawTextureRegions()
	{
		elapsedTime += Gdx.graphics.getDeltaTime();
		
		if (LoopAllFrames)
		{
			if (currentRegionX >= TextureRegions.length)
			{
				currentRegionX = 0;
				currentRegionY = 0;
			} else
			{
				if (currentRegionY >= TextureRegions[currentRegionX].length)
				{
					currentRegionY = 0;
					currentRegionX++;
				} else
				{
					currentRegionY++;
				}
			}
			
			
		}
		
		batch.draw(AnimationFrames[4], getX(), getY(), 50, 50);
		batch.draw(AnimationFrames[1], getX(), getY(), 50, 50);
		batch.draw(AnimationFrames[2], getX(), getY(), 50, 50);
		batch.draw(AnimationFrames[3], getX(), getY(), 50, 50);
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
