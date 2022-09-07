package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class EnemyProjectile extends Projectile {
//TODO: redo projectile damage system to include widths and heights as well as x and y values
    protected Texture[][] animationTextures;
    protected boolean tracking;

    protected void move(Player player) {
        if (tracking) {
            if (player.getX() - (x + xOffSet) > 0) {
                moveRight(speedX);
            } else {
                moveLeft(speedX);
            }

            if (player.getY()+20 - (y + yOffSet) > 0) {
                moveUp(speedY);
            } else {
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

