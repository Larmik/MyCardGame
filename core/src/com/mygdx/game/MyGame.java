package com.mygdx.game;

import com.badlogic.gdx.Game;

public class MyGame extends Game {

    MainMenuScreen mainMenuScreen;
    GameScreen gameScreen;
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
        gameScreen = new GameScreen(this);
        setScreen(mainMenuScreen);
    }
}
