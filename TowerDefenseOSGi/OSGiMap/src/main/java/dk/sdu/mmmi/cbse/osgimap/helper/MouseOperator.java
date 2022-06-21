package dk.sdu.mmmi.cbse.osgimap.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.osgimap.MapService;

public class MouseOperator //extends MapService
{
    public static Vector3 UnprojectVector = new Vector3();
    public static Vector2 WorldMousePosition = new Vector2();
    public static Vector2 WorldPosition = new Vector2();
    public static Vector2 TileUnderMouseWorldPosition = new Vector2();
    public static Vector2 MousePosition = new Vector2();

    public static Vector2 ScreenToWorldPoint(float x, float y) {
        GameData.camera.unproject(UnprojectVector.set(x, y, 0.0f));
        WorldMousePosition.set(UnprojectVector.x, UnprojectVector.y);
        return new Vector2(UnprojectVector.x, UnprojectVector.y);
    }

    public static Vector2 ScreenToWorldPoint(Vector2 vec) {
        return ScreenToWorldPoint(vec.x, vec.y);
    }

    public static Vector3 MultiplyPoint(Matrix4 m, Vector3 point) {
        Vector3 result = new Vector3(0, 0, 0);
        result.x = m.val[Matrix4.M00] * point.x + m.val[Matrix4.M01] * point.y + m.val[Matrix4.M02] * point.z + m.val[Matrix4.M03];
        result.y = m.val[Matrix4.M10] * point.x + m.val[Matrix4.M11] * point.y + m.val[Matrix4.M12] * point.z + m.val[Matrix4.M13];
        result.z = m.val[Matrix4.M20] * point.x + m.val[Matrix4.M21] * point.y + m.val[Matrix4.M22] * point.z + m.val[Matrix4.M23];
        float num = m.val[Matrix4.M30] * point.x + m.val[Matrix4.M31] * point.y + m.val[Matrix4.M32] * point.z + m.val[Matrix4.M33];
        num = 1f / num;
        result.x *= num;
        result.y *= num;
        result.z *= num;
        return result;
    }

    public static Vector2 WorldToScreenPoint(float x, float y) {
        Matrix4 V = GameData.camera.view;
        Matrix4 P = GameData.camera.projection;
        Matrix4 MVP = P.mul(V); // Skipping M, point in world coordinates
        Vector3 screenPos = MultiplyPoint(MVP, new Vector3(x, y, 0));
        Vector3 screenPoint = new Vector3(screenPos.x + 1f, screenPos.y + 1f, screenPos.z + 1f).scl(0.5f); // returns x, y in [0, 1] internal.

        return new Vector2(screenPoint.x * GameData.GlobalWidth, screenPoint.y * GameData.GlobalHeight); // multiply by viewport width and height to get the actual screen coordinates.
    }

    public static Vector2 WorldToScreenPoint(Vector2 ScreenPoint) {
        GameData.camera.unproject(UnprojectVector.set(ScreenPoint.x, GameData.camera.viewportHeight - ScreenPoint.y, 0.0f));
        WorldMousePosition.set(UnprojectVector.x, UnprojectVector.y);
        return new Vector2(UnprojectVector.x, UnprojectVector.y);
    }


    public static Vector2 GetMouseScreenPosition() {
        int mx = Gdx.input.getX();
        int my = Gdx.input.getY();
        return MousePosition.set(mx, GameData.camera.viewportHeight - my);
    }

    public static Vector2 GetMouseWorldPosition() {
        int mx = Gdx.input.getX();
        int my = Gdx.input.getY();
        GameData.camera.unproject(UnprojectVector.set(mx, my, 0.0f));
        WorldMousePosition.set(UnprojectVector.x, UnprojectVector.y);

        return WorldMousePosition;
    }

    public static Vector2 GetTilePositionUnderMousePosition() {
        int mx = Gdx.input.getX();
        int my = Gdx.input.getY();

        int TILE_HEIGHT = GameData.TileMapHelper.TilePixelHeight;
        int TILE_WIDTH = GameData.TileMapHelper.TilePixelWidth;

        int tileIndexX = (int) Math.floor(mx / TILE_WIDTH + 0.5f);
        int tileIndexY = (int) Math.floor(my / TILE_HEIGHT + 0.5f);

        TiledMapTileLayer tileid = (TiledMapTileLayer) MapService.tiledMap.getLayers().get(0);

        Vector2 WorldPoint = new Vector2(tileIndexX * TILE_WIDTH, ((tileid.getHeight() - 1) - tileIndexY) * TILE_HEIGHT);

        return TileUnderMouseWorldPosition.set(WorldPoint.x, WorldPoint.y);
    }

    public static Vector2 GetTilePositionAt(float x, float y) {
        int TILE_HEIGHT = GameData.TileMapHelper.TilePixelHeight;
        int TILE_WIDTH = GameData.TileMapHelper.TilePixelWidth;

        int tileIndexX = (int) Math.floor(x / TILE_WIDTH + 0.5f);
        int tileIndexY = (int) Math.floor(y / TILE_HEIGHT + 0.5f);

        Vector2 WorldPoint = new Vector2(tileIndexX * TILE_WIDTH, (tileIndexY) * TILE_HEIGHT);

        return TileUnderMouseWorldPosition.set(WorldPoint.x, WorldPoint.y);
    }


    public static Vector2 GetTilePositionUnderMousePosition2() {
        int mx = Gdx.input.getX();
        int my = Gdx.input.getY();

        int TILE_HEIGHT = GameData.TileMapHelper.TilePixelHeight;
        int TILE_WIDTH = GameData.TileMapHelper.TilePixelHeight;

        GameData.camera.unproject(UnprojectVector.set(mx, my, 0.0f));
        WorldMousePosition.set(UnprojectVector.x, UnprojectVector.y);

        float mapx = WorldMousePosition.x / TILE_WIDTH + 0.5f;
        float mapy = WorldMousePosition.y / TILE_HEIGHT + 0.5f;

        WorldPosition = new Vector2(mapx, mapy);

        int tileX = (int) WorldPosition.x;
        int tileY = (int) WorldPosition.y;

        TileUnderMouseWorldPosition.set(tileX, tileY);
        return TileUnderMouseWorldPosition;

    }
}