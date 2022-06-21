package dk.sdu.mmmi.cbse.common.data.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class MouseOperator {
    public static Vector3 UnprojectVector = new Vector3();
    public static Vector2 WorldMousePosition = new Vector2();
    public static Vector2 WorldPosition = new Vector2();
    public static Vector2 TileUnderMouseWorldPosition = new Vector2();
    public static Vector2 MousePosition = new Vector2();


    public static Vector2 ScreenToWorldPoint(GameData gameData, float x, float y) {
        gameData.camera.unproject(UnprojectVector.set(x, y, 0.0f));
        WorldMousePosition.set(UnprojectVector.x, UnprojectVector.y);
        return new Vector2(UnprojectVector.x, UnprojectVector.y);
    }

    public static Vector2 ScreenToWorldPoint(GameData gameData, Vector2 vec) {
        return ScreenToWorldPoint(gameData, vec.x, vec.y);
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

    public static Vector2 WorldToScreenPoint(GameData gameData, float x, float y) {
        Matrix4 V = gameData.camera.view;
        Matrix4 P = gameData.camera.projection;

        Matrix4 MVP = P.mul(V); // Skipping M, point in world coordinates
        Vector3 screenPos = MultiplyPoint(MVP, new Vector3(x, y, 0));

        Vector3 screenPoint = new Vector3(screenPos.x + 1f, screenPos.y + 1f, screenPos.z + 1f).scl(0.5f); // returns x, y in [0, 1] internal.

        return new Vector2(screenPoint.x * gameData.GlobalWidth, screenPoint.y * gameData.GlobalHeight); // multiply by viewport width and height to get the actual screen coordinates.
    }

    public static Vector2 WorldToScreenPoint(GameData gameData, Vector2 ScreenPoint) {
        gameData.camera.unproject(UnprojectVector.set(ScreenPoint.x, gameData.camera.viewportHeight - ScreenPoint.y, 0.0f));
        WorldMousePosition.set(UnprojectVector.x, UnprojectVector.y);
        return new Vector2(UnprojectVector.x, UnprojectVector.y);
    }


    public static Vector2 GetMouseScreenPosition(GameData gameData) {
        int mx = Gdx.input.getX();
        int my = Gdx.input.getY();
        return MousePosition.set(mx, gameData.camera.viewportHeight - my);
    }

    public static Vector2 GetMouseWorldPosition(GameData gameData) {
        int mx = Gdx.input.getX();
        int my = Gdx.input.getY();
        gameData.camera.unproject(UnprojectVector.set(mx, my, 0.0f));
        WorldMousePosition.set(UnprojectVector.x, UnprojectVector.y);

        return WorldMousePosition;
    }

    public static Vector2 GetTilePositionUnderMousePosition(GameData gameData) {
        int mx = Gdx.input.getX();
        int my = Gdx.input.getY();

        int TILE_HEIGHT = gameData.TileMapHelper.TilePixelHeight;
        int TILE_WIDTH = gameData.TileMapHelper.TilePixelWidth;

        if (TILE_HEIGHT == 0 || TILE_WIDTH == 0) {
            TILE_HEIGHT = 32;
            TILE_WIDTH = 32;
        }

        int tileIndexX = (int) Math.floor(mx / TILE_WIDTH + 0.5f);
        int tileIndexY = (int) Math.floor(my / TILE_HEIGHT + 0.5f);


        if (gameData.TileMapHelper == null || gameData.TileMapHelper.tiledMap == null) {
            return new Vector2(tileIndexX, tileIndexY);
        }
        TiledMapTileLayer tileid = (TiledMapTileLayer) gameData.TileMapHelper.tiledMap.getLayers().get(0);
        Vector2 WorldPoint = new Vector2(tileIndexX * TILE_WIDTH, ((tileid.getHeight() - 1) - tileIndexY) * TILE_HEIGHT);

        return TileUnderMouseWorldPosition.set(WorldPoint.x, WorldPoint.y);
    }

    public static Vector2 GetTilePositionAt(GameData gameData, float x, float y) {
        int TILE_HEIGHT = gameData.TileMapHelper.TilePixelHeight;
        int TILE_WIDTH = gameData.TileMapHelper.TilePixelWidth;

        if (TILE_HEIGHT == 0 || TILE_WIDTH == 0) {
            TILE_HEIGHT = 32;
            TILE_WIDTH = 32;
        }

        int tileIndexX = (int) Math.floor(x / TILE_WIDTH + 0.5f);
        int tileIndexY = (int) Math.floor(y / TILE_HEIGHT + 0.5f);

        Vector2 WorldPoint = new Vector2(tileIndexX * TILE_WIDTH, (tileIndexY) * TILE_HEIGHT);

        return TileUnderMouseWorldPosition.set(WorldPoint.x, WorldPoint.y);
    }


    public static Vector2 GetTilePositionUnderMousePosition2(GameData gameData) {
        int mx = Gdx.input.getX();
        int my = Gdx.input.getY();

        int TILE_HEIGHT = gameData.TileMapHelper.TilePixelHeight;
        int TILE_WIDTH = gameData.TileMapHelper.TilePixelWidth;

        if (TILE_HEIGHT == 0 || TILE_WIDTH == 0) {
            TILE_HEIGHT = 32;
            TILE_WIDTH = 32;
        }

        gameData.camera.unproject(UnprojectVector.set(mx, my, 0.0f));
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