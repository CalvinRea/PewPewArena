package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Torch {

    private final Animation<Texture> torchAnimation;
    private final Texture[] animationTexture;

    public Torch() {
        this.animationTexture = new Texture[12];
        populate();
        this.torchAnimation = new Animation<Texture>(0.15f, animationTexture);
    }

    public void populate() {
        for (int i = 0; i < 12; i++) {
            this.animationTexture[i] = new Texture(Gdx.files.internal("Temp assets folder/Sprites/Torch/frame (" + (i + 1) + ").gif"));
        }
    }

    public Animation<Texture> getTorchAnimation() {
        return torchAnimation;
    }

    public Texture[] getAnimationTexture() {
        return animationTexture;
    }

}
