package com.mygdx.game;

import com.badlogic.gdx.Game;

public class MyGame extends Game {

    MainMenuScreen mainMenuScreen;
    public static MyGame instance;

    public static MyGame getInstance() {
        if (instance == null) {
            return new MyGame();
        }
        return instance;
    }

    @Override
    public void create() {
        mainMenuScreen = new MainMenuScreen(this);
        setScreen(mainMenuScreen);
    }
}
