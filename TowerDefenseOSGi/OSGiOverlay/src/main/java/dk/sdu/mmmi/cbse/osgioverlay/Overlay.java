package dk.sdu.mmmi.cbse.osgioverlay;

import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.IRenderable;
import dk.sdu.mmmi.cbse.common.data.World;

public abstract class Overlay implements IRenderable {
    public static Array<Overlay> Overlays = new Array<Overlay>();

    public Overlay() {
        Overlays.add(this);
    }

    public static void RenderAllOverlays(GameData gameData, World world) {
        for (Overlay overlay : Overlays) {
            overlay.OnRender(gameData, world);
        }
    }

    public static void CreateAllOverlays(GameData gameData, World world) {
        for (Overlay overlay : Overlays) {
            overlay.OnCreate(gameData, world);
        }
    }

    public static void UpdateAllOverlays(GameData gameData, World world) {
        for (Overlay overlay : Overlays) {
            overlay.OnUpdate(gameData, world);
        }
    }

    public static void DisposeAllOverlays(GameData gameData, World world) {
        for (Overlay overlay : Overlays) {
            overlay.OnDispose(gameData, world);
        }
        Overlays.clear();
    }
}
