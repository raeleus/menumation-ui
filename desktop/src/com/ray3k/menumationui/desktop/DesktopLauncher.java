package com.ray3k.menumationui.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.ray3k.menumationui.Core;

public class DesktopLauncher {

    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(800, 800);
        config.setBackBufferConfig(8, 8, 8, 8, 16, 0, 4);
        Core core = new Core();
        core.setSplashWorker(new DesktopSplashWorker());
        new Lwjgl3Application(core, config);
    }
}
