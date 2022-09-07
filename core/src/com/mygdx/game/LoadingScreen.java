package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;

public class LoadingScreen extends ScreenAdapter {

    //TODO: add functionality with asset manager

    String username;
    MyGdxGame game;
    boolean touch;

    public LoadingScreen(MyGdxGame game) {
        this.game = game;
    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        touch = Gdx.input.isTouched();
        ScreenUtils.clear(0, 0, 0, 0);
        if (touch) {
            game.setScreen(new LoginSignupScreen(game));
        }
    }

    @Override
    public void hide() {
        dispose();
    }//call after done with screen

    @Override
    public void dispose() {
    }//call to dispose


}
