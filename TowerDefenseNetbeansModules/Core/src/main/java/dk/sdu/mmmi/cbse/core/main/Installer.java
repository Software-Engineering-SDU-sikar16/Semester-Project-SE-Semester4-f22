package dk.sdu.mmmi.cbse.core.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    private static Game g;

    @Override
    public void restored() {

        g = new Game();

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Gaem";
        cfg.width = 960;
        cfg.height = 640;
        cfg.useGL30 = false;
        cfg.resizable = false;
        cfg.backgroundFPS = 60;
        cfg.foregroundFPS = 60;

        new LwjglApplication(g, cfg);
    }
}
