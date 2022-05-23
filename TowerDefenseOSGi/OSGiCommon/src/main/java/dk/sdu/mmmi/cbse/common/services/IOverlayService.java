package dk.sdu.mmmi.cbse.common.services;


public interface IOverlayService {
    void OnCreate();
    void OnRender();
    void OnUpdate(float DeltaTime);
}
