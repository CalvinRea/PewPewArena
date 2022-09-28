package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class Bullet extends Projectile {


    public void initialisePositioning(Sprite gunSprite, int playerX, int playerY) {

        float cos = MathUtils.cosDeg(gunSprite.getRotation() + 30);
        float sin = MathUtils.sinDeg(gunSprite.getRotation() + 30);

        x = (int) (playerX + 40 + gunSprite.getWidth() / 3 + cos * gunSprite.getWidth() / 4);
        y = (int) (playerY + 60 + gunSprite.getHeight() / 3 + sin * gunSprite.getWidth() / 4);

        speedX = MathUtils.cosDeg(gunSprite.getRotation()) * 25;
        speedY = MathUtils.sinDeg(gunSprite.getRotation()) * 25;

    }

    protected Texture[] populateAnimationTextures(Texture[] temp, String filePath) {
        for (int i = 0; i < temp.length; i++) {
            temp[i] = new Texture(Gdx.files.internal(filePath + "/frame (" + (i + 1) + ").gif"));
        }
        return temp;
    }

    protected void attack() {
    }


    protected void move() {

        translate(speedX, speedY);
        if (x > 1920 || x < 0 || y < 0 || y > 1080) {
            alive = false;
        }
    }


}
