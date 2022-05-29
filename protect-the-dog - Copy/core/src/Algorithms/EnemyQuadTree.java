package Algorithms;

import GamePlay.EnemyEntity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import helper.Constants;
import helper.MouseOperator;


public class EnemyQuadTree extends QuadTree<EnemyEntity>
{
	public EnemyQuadTree()
	{
		super(new Rectangle(0, 0, Constants.GlobalWidth, Constants.GlobalHeight), 4);
	}
	
	public void OnUpdate()
	{
		Clear();
		AddEnemies();
	}
	
	public void OnRender()
	{
		
		Vector2 scps = MouseOperator.GetMouseScreenPosition();
		
		
		Rectangle dx = new Rectangle(scps.x, scps.y, 100, 100);
		Array<EnemyEntity> entities = Constants.EnemyQuadTree.Query(dx);
		
		Constants.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		Constants.shapeRenderer.setColor(Color.BLACK);
		Constants.shapeRenderer.rect(dx.x, dx.y, dx.width, dx.height);
		Constants.shapeRenderer.setColor(Color.GOLD);
		for (EnemyEntity entity : entities)
		{
			if (entity != null)
			{
				Constants.shapeRenderer.circle(entity.getX(), entity.getY(), 10);
			}
		}
		Constants.shapeRenderer.end();
		
		super.Render();
	}
	
	private void AddEnemies()
	{
		for (EnemyEntity enemy : Constants.EnemyManager.enemiesOnScreen)
		{
			Insert(enemy.getCenteredPosition(), enemy);
		}
	}
	
	
	
}
