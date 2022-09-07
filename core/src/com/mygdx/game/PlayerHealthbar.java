package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class PlayerHealthbar extends Healthbar{


    public PlayerHealthbar() {

        originalHealth= 10;
        textures = new Texture[11];
        populate("Temp assets folder/Sprites/MyHealthbar/");
        currentTexture = getTextures()[10];
        x = 50;
        y = Gdx.graphics.getHeight() - 100;
    }

    public void setCurrentTexture(double i) {
        if (i >= 0) {
            this.currentTexture = getTextures()[(int) i];
        } else {//TODO: implement game over screen
        }
    }


}
