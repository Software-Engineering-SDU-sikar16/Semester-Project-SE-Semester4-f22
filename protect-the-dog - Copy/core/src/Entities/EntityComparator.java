package Entities;

import java.util.Comparator;

public class EntityComparator implements Comparator<IEntity>
{
	@Override
	public int compare(IEntity o1, IEntity o2)
	{
		int a = ((Entity)o1).GetZIndex();
		int b = ((Entity)o2).GetZIndex();
		
		if (a < b)
		{
			return -1;
		}
		else if (a > b)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
}
