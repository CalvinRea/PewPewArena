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


    public Enemy() {}//constructor used instead of default constructor


    protected void move(int HitBoxX) {//moves the enemy based on the players hitbox position
        if (HitBoxX - (x + xOffSet) > 0) {
            moveRight(speed);
        } else {
            moveLeft(speed);
        }
    }

    protected void attack(Healthbar healthBar) {
        healthBar.setCurrentHealth(healthBar.getCurrentHealth() - damage);
    }// uses the players healthbar to reduce the health of the player by the enemy's damage

    protected boolean isFlipped() {//returns whether the enemy is currenty flipped or not so that the appropriate textures can be displayed

        return flipped;
    }

    protected void setFlipped(boolean bool) {
        flipped = bool;
    }//setFlipped: sets the flipped attribute to be true or false

    protected Texture[][] populateAnimationTextures(Texture[][] temp, String filePath) {/*populates and returns the animationTextures 2d array with the enemy’s animation
     textures by using the filepath of the stored textures and a temporary array*/
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                temp[i][j] = new Texture(Gdx.files.internal(filePath + i + "/frame (" + (j + 1) + ").png"));
            }

        }
        return temp;
    }

    public void flipper(int playerX) {//determines whether the enemy should be flipped or not based on the player’s current x position
        setFlipped(0 > playerX - (x + xOffSet));
    }

    public void checkBulletAndDamage(ArrayList<AP9Bullet> bullets, int enX, int enY, double damageDone) {/*uses the co-ordinates of the enemy (enX and enY) and the bullet object (and it's co-ordinate) and
    decreases the enemy health by the value of damageDone*/
        for (int i = 0; i < bullets.size(); i++) {

            if (Math.abs(enX + hitBoxXOffSet - bullets.get(i).x) < hitBoxX
                    && Math.abs(enY + hitBoxYOffSet - bullets.get(i).y) < hitBoxY
            ) {
                health -= damageDone;
                bullets.remove(i);
            }
        }
        if (!alive) {
            dispose();//if enemy is dead free up memory using this method
        }
    }

    public void dispose() {//used to dispose of the enemy animation textures and free up memory
        for (Texture[] animationTexture : animationTextures) {
            for (Texture texture : animationTexture) {
                texture.dispose();
            }
        }
        x = 10000;
        y = 10000;
    }


}
