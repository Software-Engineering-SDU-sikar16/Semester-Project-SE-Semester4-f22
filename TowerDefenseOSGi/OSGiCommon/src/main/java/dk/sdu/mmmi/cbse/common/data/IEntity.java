package dk.sdu.mmmi.cbse.common.data;

public interface IEntity {
    void OnCreate(GameData gameData, World world);

    void OnRender(GameData gameData, World world);

    void OnUpdate(GameData gameData, World world);

    void OnDispose(GameData gameData, World world);
}
