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
//
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.maps.MapLayer;
//import com.badlogic.gdx.maps.MapObject;
//import com.badlogic.gdx.maps.MapObjects;
//import com.badlogic.gdx.maps.objects.RectangleMapObject;
//import com.badlogic.gdx.maps.tiled.TiledMap;
//import com.badlogic.gdx.maps.tiled.TmxMapLoader;
//import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector2;
//import dk.sdu.mmmi.cbse.common.services.IMapService;
//
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//public class MapService implements IMapService {
//
//    private TiledMap tiledMap;
//    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
//
//    @Override
//    public void createMap() {
//        // Finds the location of where the function looks for files
////        Path currentRelativePath = Paths.get("..");
////        String s = currentRelativePath.toAbsolutePath().toString();
////        System.out.println("Current absolute path is: " + s);
//
//        tiledMap = new TmxMapLoader().load("../assets/maps/map1.tmx");
//        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
//    }
//
//    @Override
//    public void updateMap(OrthographicCamera camera) {
//        if (orthogonalTiledMapRenderer == null) {
//            createMap();
//        }
//        orthogonalTiledMapRenderer.setView(camera);
//        orthogonalTiledMapRenderer.render();
//    }
//}