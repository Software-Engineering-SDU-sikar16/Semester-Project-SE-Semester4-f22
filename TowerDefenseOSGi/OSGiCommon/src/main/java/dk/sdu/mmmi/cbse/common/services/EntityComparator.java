package dk.sdu.mmmi.cbse.common.services;

import java.util.Comparator;

public class EntityComparator implements Comparator<IEntityProcessingService>
{
	@Override
	public int compare(IEntityProcessingService o1, IEntityProcessingService o2)
	{
		int a = ((EntityProcessingService) o1).GetZIndex();
		int b = ((EntityProcessingService) o2).GetZIndex();
		
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
