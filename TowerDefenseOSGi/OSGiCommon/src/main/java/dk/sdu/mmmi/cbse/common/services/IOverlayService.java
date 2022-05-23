package dk.sdu.mmmi.cbse.common.services;


import com.badlogic.gdx.graphics.OrthographicCamera;

public interface IOverlayService {
    void onCreate();
    void onRender();
    void onUpdate(float DeltaTime);
}
