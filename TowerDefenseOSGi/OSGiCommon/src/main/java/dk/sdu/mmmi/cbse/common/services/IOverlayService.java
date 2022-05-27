package dk.sdu.mmmi.cbse.common.services;

public interface IOverlayService {
    void onCreate();
    void onRender();
    void onUpdate(float DeltaTime);
}
