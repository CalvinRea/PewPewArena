package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.ArrayList;

public class Enemy extends Entity {
    protected int hitBoxX;
    protected int hitBoxY;
    protected int hitBoxXOffSet;
    protected int hitBoxYOffSet;
    protected int state;
    protected double damage;
    protected int attackDistance;
    protected int moveDistance;
    protected Texture[][] animationTextures;
    protected Animation<Texture> enemyAnimation;
    protected boolean flipped;


    public Enemy() {
    }


    protected void move(Player player) {
        if (player.getX() - (x + xOffSet) > 0) {
            moveRight(speed);
        } else {
            moveLeft(speed);
        }
    }

    protected void attack(PlayerHealthbar healthBar, Player player) {
        healthBar.setHealth(healthBar.getHealth() - damage);
    }

    protected boolean isFlipped() {

        return flipped;
    }

    protected void setFlipped(boolean bool) {
        flipped = bool;
    }

    public Texture[][] populateAnimationTextures(Texture[][] temp, String filePath) {
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                temp[i][j] = new Texture(Gdx.files.internal(filePath + i + "/frame (" + (j + 1) + ").png"));
            }

        }
        return temp;
    }

    public void flipper(int playerX) {
        setFlipped(0 > playerX - (x + xOffSet));
    }

    public void checkBulletAndPain(ArrayList<AP9Bullet> bullets,int enX,int enY,double damageDone){
        for (int i = 0; i < bullets.size(); i++) {

            if (Math.abs(enX+hitBoxXOffSet - bullets.get(i).x ) < hitBoxX
                    && Math.abs(enY+hitBoxYOffSet - bullets.get(i).y ) < hitBoxY
            ) {
                health-=damageDone;
                bullets.remove(i);
            }
        }
        if(!alive){
            dispose();
        }
    }

    public void dispose(){
        for (int i = 0; i < animationTextures.length; i++) {
            for (int j = 0; j < animationTextures[i].length; j++) {
                animationTextures[i][j].dispose();
            }
        }
        x=10000;
        y=10000;
    }


}