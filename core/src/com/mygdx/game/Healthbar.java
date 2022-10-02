package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Healthbar {

    private Texture[] textures;
    private Texture currentTexture;
    private final double originalHealth;
    private double currentHealth;
    private final int x;
    private final int y;


    public Healthbar(int inX,int inY,double inOriginalHealth,int length,String filePathStart,String filePathEnd) {
        x=inX;
        y=inY;
        originalHealth=inOriginalHealth;
        textures=new Texture[length];
        populate(filePathStart,filePathEnd);
        currentTexture=textures[0];
        currentHealth=originalHealth;
    }

    public void populate(String filePathStart,String filePathEnd) {
        for (int i = 0; i < textures.length; i++) {
            textures[i] = new Texture(Gdx.files.internal(filePathStart + (i+1) + filePathEnd));
        }
    }

    public double getOriginalHealth() {
        return originalHealth;
    }

    public double getCurrentHealth(){return currentHealth;}

    public void setCurrentHealth(double inCurrentHealth){
        currentHealth=inCurrentHealth;
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

    public void dispose() {
        for (Texture t : textures) {
            t.dispose();
        }
    }

    public void setCurrentTexture(double i) {
        if (i >= 0) {
            currentTexture = textures[(int)((textures.length-1)-Math.ceil(i* (textures.length-1)/originalHealth))];
        }else{dispose();}
    }

}
