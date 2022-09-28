package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Gun {
    protected float angle;
    protected double damage;
    protected Sprite sprite;
    protected Texture shotTexture;
    protected Animation<Texture> gunShot;
    protected Texture gunTexture;
    protected boolean shooting;

    protected Texture[] populateAnimationTextures(Texture[] temp, String filePath) {
        for (int i = 0; i < temp.length; i++) {

            temp[i] = new Texture(Gdx.files.internal(filePath + "/frame (" + (i + 1) + ").gif"));
        }
        return temp;
    }

    public void shoot() {

        shooting = true;
    }

}
