package com.mygdx.game;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {
    //can add variables here that can be accessed by all screens using game.variable which is similar to global variables
    @Override
    public void create() {
//this is the point the game starts from, like the main method in java
        setScreen(new MainMenuScreen(this));

    }

}
