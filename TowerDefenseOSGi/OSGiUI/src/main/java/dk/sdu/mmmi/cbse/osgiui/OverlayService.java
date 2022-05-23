package dk.sdu.mmmi.cbse.osgiui;


import dk.sdu.mmmi.cbse.common.services.IOverlayService;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;


public class OverlayService implements IOverlayService {

    public static Array<IOverlayService> Overlays = new Array<IOverlayService>();

    public OverlayService()
    {
        Overlays.add(this);
    }


    @Override
    public void OnCreate() {
        for (IOverlayService overlay : Overlays)
        {
            overlay.OnCreate();
        }

    }

    @Override
    public void OnRender() {
        for (IOverlayService overlay : Overlays) {
            overlay.OnRender();
        }

    }

    @Override
    public void OnUpdate(float DeltaTime) {
        for (IOverlayService overlay : Overlays)
        {
            overlay.OnUpdate(Gdx.graphics.getDeltaTime());
        }
    }
}