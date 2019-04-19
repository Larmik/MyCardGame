package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class MainMenuScreen implements Screen {

    Texture background, logogame, button;
    Image backgroundImg, logoImg;
    boolean isTouched = false;
    int logoX, logoY, buttonX, buttonY, screenHeight, screenWidth;
    Button playButton;
    Skin skin;
    Stage stage;

    MyGame game;

    MainMenuScreen(MyGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        initViews();
    }

    @Override
    public void render(float delta) {

        Gdx.input.setInputProcessor(stage);
        stage.act();
        stage.draw();
        if (isTouched) {
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void initViews() {
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        skin = new Skin(Gdx.files.internal("skins/neon-ui.json"));
        playButton = new TextButton("Nouvelle partie", skin);

        stage = new Stage(new ScreenViewport());

        background = new Texture(Gdx.files.local("assets/background.jpg"));
        logogame = new Texture(Gdx.files.local("assets/logogame.png"));
        button = new Texture(Gdx.files.local("assets/button.png"));

        backgroundImg = new Image(background);
        logoImg = new Image(logogame);

        logoX = screenWidth / 2 - logogame.getWidth() * 2;
        logoY = screenHeight - (logogame.getHeight() * 2);
        buttonX = screenWidth - screenWidth / 3 - screenWidth / 20;
        buttonY = Math.round(screenHeight - button.getHeight());

        backgroundImg.setSize(screenWidth, screenHeight);
        logoImg.setSize(logogame.getWidth() * 2, logogame.getHeight() * 2);
        playButton.setSize(screenWidth/6, screenHeight/6);

        backgroundImg.setPosition(0, 0);
        logoImg.setPosition(logoX, logoY);
        playButton.setPosition(buttonX, buttonY);
        ((TextButton)playButton).getLabel().setFontScale(2.5f, 2.5f);
        playButton.addListener(new InputListener() {
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isTouched = true;
                return false;
            }
        });
        stage.addActor(backgroundImg);
        stage.addActor(logoImg);
        stage.addActor(playButton);
    }
}
