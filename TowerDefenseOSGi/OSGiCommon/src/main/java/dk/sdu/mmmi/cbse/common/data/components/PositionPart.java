/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.components;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public class PositionPart implements EntityPart
{
	
	private float x;
	private float y;
	private int width;
	private int height;
	
	public PositionPart(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}
	
	public PositionPart(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.width =0;
		this.height = 0;
		this.width = 0;
	}
	
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	
	public void setX(float newX)
	{
		this.x = newX;
	}
	
	public void setY(float newY)
	{
		this.y = newY;
	}
	
	public void setPosition(float newX, float newY)
	{
		this.x = newX;
		this.y = newY;
	}
	
	public void setPosition(Vector2 pos)
	{
		setPosition(pos.x, pos.y);
	}
	
	
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	public Vector2 getPosition()
	{
		return new Vector2(getX(), getY());
	}
	
	
	public float getCenterY()
	{
		return getY() + (getHeight() / 2);
	}
	
	public float getCenterX()
	{
		return getX() + (getWidth() / 2);
	}
	
	public Vector2 getCenteredPosition()
	{
		return new Vector2(getCenterX(), getCenterY());
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	
	@Override
	public void OnCreate(GameData gameData, World world, Entity entity)
	{
	}
	
	@Override
	public void OnUpdate(GameData gameData, World world, Entity entity)
	{
	
	}
	
	@Override
	public void OnRender(GameData gameData, World world, Entity entity)
	{
	
	}
	
	
}
