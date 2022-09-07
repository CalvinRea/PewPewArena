package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Healthbar {
    //TODO: make health bar super class that player and boss healthbar inherit from
    protected Texture[] textures;
    protected Texture currentTexture;
    protected double originalHealth;
    protected int x;
    protected int y;


    public Healthbar(){}

    public void populate(String filePath) {
        for (int i = 0; i < this.textures.length; i++) {
            this.textures[i] = new Texture(Gdx.files.internal(filePath + i + ".png"));
        }
    }
    public double getHealth() {
        return originalHealth;
    }

    public void setHealth(double originalHealth) {
        this.originalHealth = originalHealth;
    }

    public Texture[] getTextures() {
        return this.textures;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Texture getCurrentTexture() {
        return currentTexture;
    }
}
