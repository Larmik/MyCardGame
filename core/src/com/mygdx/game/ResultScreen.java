package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ResultScreen implements Screen {

    MyGame game;
    BitmapFont fontTitle, fontMessage;
    Image background;
    Stage stage;
    TextButton newGameBtn;
    boolean win, draw;

    ResultScreen(MyGame game, boolean win, boolean draw) {
        this.game = game;
        this.win = win;
        this.draw = draw;
    }

    @Override
    public void show() {
        fontTitle = new BitmapFont(Gdx.files.local("assets/fonts/junegull.fnt"));
        fontMessage = new BitmapFont(Gdx.files.local("assets/fonts/junegull.fnt"));
        stage = new Stage(new ScreenViewport());
        Skin skin = new Skin(Gdx.files.internal("skins/neon-ui.json"));
        background = new Image(new Texture(Gdx.files.local("assets/background.jpg")));
        newGameBtn = new TextButton("Recommencer", skin);

        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        newGameBtn.setSize(Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/6);

        newGameBtn.getLabel().setFontScale(2.5f);

        background.setPosition(0,0);
        newGameBtn.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/12, Gdx.graphics.getHeight()/2 - Gdx.graphics.getHeight()/3);

        newGameBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                return false;
            }
        });

        stage.addActor(background);
        stage.addActor(newGameBtn);

    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);

        stage.act();
        stage.draw();
        stage.getBatch().begin();
        fontTitle.getData().setScale(3f);
        fontMessage.getData().setScale(2f);
        fontTitle.draw(stage.getBatch(), "Fin de la manche", Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/4);

        if (win)
            fontMessage.draw(stage.getBatch(), "Bravo, une victoire pour toi !", Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/2);
        else if (draw)
            fontMessage.draw(stage.getBatch(), "C'est un ex aequo !", Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/2);
        else
            fontMessage.draw(stage.getBatch(), "Dommage, c'est perdu...", Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/2);


        stage.getBatch().end();

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
        fontTitle.dispose();
        fontMessage.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        fontTitle.dispose();
        fontMessage.dispose();
    }
}
