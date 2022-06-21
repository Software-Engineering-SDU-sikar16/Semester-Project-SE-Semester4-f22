package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public abstract class EntityProcessingService implements IEntityProcessingService {
    public static EntityComparator EntityComparator = new EntityComparator(); // for z sorting
    private int ZIndex = 0; // Z Sorting of the sprite [-100, ..., 0, ..., +100]. default 0. => All entities are sorted each frame before being drawn.

    public abstract void process(GameData gameData, World world);

    public void SetZIndex(int NewZIndex) {
        ZIndex = NewZIndex;
    }

    public int GetZIndex() {
        return ZIndex;
    }
}
