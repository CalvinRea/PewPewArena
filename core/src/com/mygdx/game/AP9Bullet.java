package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class AP9Bullet extends Bullet {

    public AP9Bullet(){

animation=new Animation(0.25F,populateAnimationTextures(new Texture[4],"Temp assets folder/Sprites/bullets/yellow"));
animation.setPlayMode(Animation.PlayMode.LOOP);
    }


}
