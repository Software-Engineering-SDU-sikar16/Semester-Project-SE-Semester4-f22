package dk.sdu.mmmi.cbse.common.services;

import com.badlogic.gdx.graphics.OrthographicCamera;

public interface IMapService {
    void createMap();
    void updateMap(OrthographicCamera camera);

}
