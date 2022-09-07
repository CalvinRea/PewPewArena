package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BossHealthbar extends Healthbar{
    boolean bossAlive;


    public BossHealthbar(double originalHealth){
        this.originalHealth=originalHealth;
        bossAlive =true;
        x=800;
        y=800;
        textures=new Texture[39];
        populate();
    }
    public void populate() {
        for (int i = 0; i < this.textures.length; i++) {
            this.textures[i] = new Texture(Gdx.files.internal("Temp assets folder/Sprites/boss healthbar/frame (" + (i+1) + ").gif"));
        }
    }
    public void setCurrentTexture(double currentHealth) {
            if(currentHealth<=0){
                bossAlive=false;
                dispose();
            }else{
                this.currentTexture = textures[(int)Math.round(39-currentHealth*(39/originalHealth))];//math to convert health to frame value between 0 and 43
            }}

    private void dispose(){
        for (Texture t:textures) {
            t.dispose();
        }
        }
}
