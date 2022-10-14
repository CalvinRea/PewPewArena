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
       //constructor used to initialise attributes with parameter values, filePathStart and filePathEnd used to populate textures
        x=inX;
        y=inY;
        originalHealth=inOriginalHealth;
        textures=new Texture[length];
        populate(filePathStart,filePathEnd);
        currentTexture=textures[0];
        currentHealth=originalHealth;
    }

    public void populate(String filePathStart,String filePathEnd) {
        //populates textures using the filepath of the textures to access them individually and populate the array
        for (int i = 0; i < textures.length; i++) {
            textures[i] = new Texture(Gdx.files.internal(filePathStart + (i+1) + filePathEnd));
        }
    }

    public double getOriginalHealth() {
        return originalHealth;
    }//provides access to the ORIGINALHEALTH field

    public double getCurrentHealth(){return currentHealth;}//provides access to the currentHealth field

    public void setCurrentHealth(double inCurrentHealth){
        currentHealth=inCurrentHealth;
    }//used to manipulate the currentHealth field to be the value of inCurrentHealth

    public int getX() {
        return x;
    }//provides access to the x attribute

    public int getY() {
        return y;
    }//provides access to the y attribute

    public Texture getCurrentTexture() {
        return currentTexture;
    }//provides access to the currentTexture

    public void dispose() {//used to free up memory by disposing of the healthbar’s textures
        for (Texture t : textures) {
            t.dispose();
        }
    }

    public void setCurrentTexture(double currentHealth) {
        //used to change the healthbar texture to match the currentHealth
        if (currentHealth >= 0) {
            currentTexture = textures[(int)((textures.length-1)-Math.ceil(currentHealth* (textures.length-1)/originalHealth))];
        }else{dispose();}
    }

}
