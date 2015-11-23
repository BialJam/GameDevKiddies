package pl.edu.pb.gamedev.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import pl.edu.pb.gamedev.Game;

public class DesktopLauncher {
    public static void main(final String[] arg) {
        final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "DropletsTM";
        config.width = 800;
        config.height = 480;
        new LwjglApplication(new Game(), config);
    }
}
