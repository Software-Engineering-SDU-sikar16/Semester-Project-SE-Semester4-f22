package dk.sdu.mmmi.cbse.osgimap;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import dk.sdu.mmmi.cbse.common.services.IMapService;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MapService implements IMapService {

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    @Override
    public void createMap() {
        // Finds the location of where the function looks for files
//        Path currentRelativePath = Paths.get("..");
//        String s = currentRelativePath.toAbsolutePath().toString();
//        System.out.println("Current absolute path is: " + s);
        tiledMap = new TmxMapLoader().load("../assets/maps/map1.tmx");
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

    }

    @Override
    public void updateMap(OrthographicCamera camera) {
        if (orthogonalTiledMapRenderer == null) {
            createMap();
        }
        orthogonalTiledMapRenderer.setView(camera);
        orthogonalTiledMapRenderer.render();
    }

}