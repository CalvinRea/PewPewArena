package com.mygdx.game;

import com.badlogic.gdx.ScreenAdapter;

public class ScreenTemplate extends ScreenAdapter {
    MyGdxGame game;

    public ScreenTemplate(MyGdxGame game) {
        this.game = game;
    }

    @Override
    public void show() {
    }//like create method

    @Override
    public void render(float delta) {
    }

    @Override
    public void hide() {
        dispose();
    }//call after done with screen

    @Override
    public void dispose() {
    }//call to dispose
}
