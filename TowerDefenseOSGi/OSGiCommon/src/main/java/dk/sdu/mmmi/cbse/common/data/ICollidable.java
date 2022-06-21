package dk.sdu.mmmi.cbse.common.data;

public interface ICollidable {
    void OnCollision(GameData gameData, World world, Entity other);
}
