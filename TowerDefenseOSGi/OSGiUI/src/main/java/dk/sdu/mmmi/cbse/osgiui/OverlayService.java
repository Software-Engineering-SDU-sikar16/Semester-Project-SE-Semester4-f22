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

    public static void OnCreateAll() {
        for (IOverlayService overlay : Overlays)
        {
            overlay.onCreate();
        }

    }


    public static void OnRenderAll() {
        for (IOverlayService overlay : Overlays) {
            overlay.onRender();
        }

    }


    public static void OnUpdateALl(float DeltaTime) {
        for (IOverlayService overlay : Overlays)
        {
            overlay.onUpdate(Gdx.graphics.getDeltaTime());
        }
    }
}