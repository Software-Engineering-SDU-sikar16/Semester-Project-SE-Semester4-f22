package dk.sdu.mmmi.cbse.osgienemy;


import com.badlogic.gdx.math.Rectangle;
import dk.sdu.mmmi.cbse.common.data.Algorithms.QuadTree;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class EnemyQuadTree extends QuadTree<Enemy>
{
	public EnemyQuadTree(GameData gameData)
	{
		super(new Rectangle(0, 0, gameData.getDisplayWidth(), gameData.getDisplayHeight()), 4);
	}
	
	public void OnUpdate(GameData gameData)
	{
		Clear();
		AddEnemies(gameData);
	}
	
	public void OnRender(GameData gameData)
	{
	
/*		Vector2 scps = MouseOperator.GetMouseScreenPosition();
		
		
		Rectangle dx = new Rectangle(scps.x, scps.y, 100, 100);
		Array<Enemy> entities = gameData.EnemyQuadTree.Query(dx);
		
		gameData.GlobalShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		gameData.GlobalShapeRenderer.setColor(Color.BLACK);
		gameData.GlobalShapeRenderer.rect(dx.x, dx.y, dx.width, dx.height);
		gameData.GlobalShapeRenderer.setColor(Color.GOLD);
		for (Enemy entity : entities)
		{
			if (entity != null)
			{
				gameData.GlobalShapeRenderer.circle(entity.getX(), entity.getY(), 10);
			}
		}
		gameData.GlobalShapeRenderer.end();*/
		
		super.Render(gameData);
	}
	
	private void AddEnemies(GameData gameData)
	{
	/*	for (Enemy enemy : gameData.EnemyManager.enemiesOnScreen)
		{
			Insert(enemy.getCenteredPosition(), enemy);
		}*/
	}
	
	
}
