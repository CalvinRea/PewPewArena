package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class EnemyProjectile extends Projectile {

    protected boolean tracking;

    protected void move(Player player) {
        if (tracking) {
            if (player.getHitBoxX() - (x - xOffSet) > 0) {
                moveRight(speedX);
            } else {
                moveLeft(speedX);
            }

            if (player.getHitBoxY() - (y - yOffSet) > 0) {
                moveUp(speedY);
            } else if (player.getHitBoxY() + 70 - (y - yOffSet) < 0) {//+80 for player height
                moveDown(speedY);
            }
        }
    }

    protected void attack(PlayerHealthbar playerHealthbar, Player player) {
        playerHealthbar.setHealth(playerHealthbar.getHealth() - damage);
        alive = false;
    }

    public Texture[][] populateAnimationTextures(Texture[][] temp, String filePath) {
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                temp[i][j] = new Texture(Gdx.files.internal(filePath + i + "/frame (" + (j + 1) + ").png"));
            }

        }
        return temp;
    }
}


