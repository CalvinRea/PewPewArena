package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class AP9Bullet extends Projectile {
    private final Animation<Texture> animation;

    public AP9Bullet() {//a constructor uses to initialise the AP9Bullet animation attribute
        animation = new Animation(0.25F,populateAnimationTextures(new Texture[4], "Temp assets folder/Sprites/bullets/yellow"));
        animation.setPlayMode(Animation.PlayMode.LOOP);
    }
    public void initialisePositioning(Sprite gunSprite, int playerX, int playerY) {//used to initialise the positioning of the bullet in relation to the gunSprites position and players position(playerX,playerY)

        float cos = MathUtils.cosDeg(gunSprite.getRotation() + 30);
        float sin = MathUtils.sinDeg(gunSprite.getRotation() + 30);

        x = (int) (playerX + 40 + gunSprite.getWidth() / 3 + cos * gunSprite.getWidth() / 4);
        y = (int) (playerY + 60 + gunSprite.getHeight() / 3 + sin * gunSprite.getWidth() / 4);

        speedX = MathUtils.cosDeg(gunSprite.getRotation()) * 25;
        speedY = MathUtils.sinDeg(gunSprite.getRotation()) * 25;

    }

    private Texture[] populateAnimationTextures(Texture[] temp, String filePath) {//used to populate the bullets animation textures array using a temporary array and the filepath of the textures stored as a string
        for (int i = 0; i < temp.length; i++) {
            temp[i] = new Texture(Gdx.files.internal(filePath + "/frame (" + (i + 1) + ").gif"));
        }
        return temp;
    }

    public void move() {//the AP9Bullet and makes sure the bullet is only alive if on screen

        translate(speedX, speedY);
        if (x > 1920 || x < 0 || y < 0 || y > 1080) {
            alive = false;
        }
    }

    public Animation<Texture> getAnimation(){
        return animation;
    }//returns the animation of the Animation field


}
