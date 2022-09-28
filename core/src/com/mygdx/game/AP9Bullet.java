package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class AP9Bullet extends Bullet {
    protected Animation<Texture> animation;
    protected static Texture[] animationTextures;
    public AP9Bullet(){
if(animationTextures==null){
    animationTextures=populateAnimationTextures(new Texture[4],"Temp assets folder/Sprites/bullets/yellow");
}
animation=new Animation(0.25F,animationTextures);
animation.setPlayMode(Animation.PlayMode.LOOP);
    }


}
