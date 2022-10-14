package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Torch {

    private static final Animation<Texture> torchAnimation=populate();

    public Torch() {}//used instead of the default constructor for code readability

    private static Animation<Texture> populate() {
        Texture[]temp = new Texture[12];
        for (int i = 0; i < 12; i++) {
            temp[i] = new Texture(Gdx.files.internal("Temp assets folder/Sprites/Torch/frame (" + (i + 1) + ").gif"));
        }
        return new Animation<>(0.15f, temp);
    }//called to initialize torchAnimation which is the same for all objects

    public static Animation<Texture> getTorchAnimation() {
        return torchAnimation;
    }//returns the torch animation which is the same for all objects
}
