package dk.sdu.mmmi.cbse.osgiui;


import dk.sdu.mmmi.cbse.common.services.IOverlayService;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;


public abstract class OverlayService implements IOverlayService {

    public static Array<IOverlayService> Overlays = new Array<IOverlayService>();

    public OverlayService()
    {
        Overlays.add(this);
    }

    public static void OnCreate() {
        for (IOverlayService overlay : Overlays)
        {
            overlay.OnCreate();
        }

    }


    public static void OnRender() {
        for (IOverlayService overlay : Overlays) {
            overlay.OnRender();
        }

    }


    public static void OnUpdate(float DeltaTime) {
        for (IOverlayService overlay : Overlays)
        {
            overlay.OnUpdate(Gdx.graphics.getDeltaTime());
        }
    }
}