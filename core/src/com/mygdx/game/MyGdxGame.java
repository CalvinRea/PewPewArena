package com.mygdx.game;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {

    //TODO: read the documentation FIRST!!!!!!!!!!
    //can add variables that can be accessed by all screens game.variable
    @Override
    public void create() {
        //setScreen(); to set screen
        //screen.show();use for input
        //screen.render(1/30f);rendering
        //screen.hide();before change
        //screen.dispose();after change



        //if switch to different screen screen.hide automatically called
          setScreen(new MainMenu(this));
        //setScreen(new LevelScreen((this), new Player()));
        //setScreen(new LoginSignupScreen(this));

    }

}
