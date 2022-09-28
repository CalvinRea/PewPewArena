package com.mygdx.game;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {
    //can add variables that can be accessed by all screens game.variable which is similar to global variables
    @Override
    public void create() {
        //setScreen(); to set screen
        //screen.show();use for input
        //screen.render(1/30f);rendering
        //screen.hide();before change
        //screen.dispose();after change
        //if switch to different screen screen.hide automatically called

        setScreen(new LevelScreen(this,new Player()));

    }

}
