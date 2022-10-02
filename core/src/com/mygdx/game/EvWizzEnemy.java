package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class EvWizzEnemy extends Enemy {

    public EvWizzEnemy() {
        hitBoxX = 60;
        hitBoxY = 80;
        hitBoxXOffSet = 250;//these values are right
        hitBoxYOffSet = 225;
        originalHealth = 10;
        health = 10;
        damage = 0.05;
        xOffSet = 145;
        yOffSet = -170;
        animationTextures = populateAnimationTextures();
        attackDistance = 210;
        moveDistance = 1000;
        speed = 2;
        state = 0;
        enemyAnimation = new Animation<>(0.2f, animationTextures[0]);

    }

    private Texture[][] populateAnimationTextures() {

        Texture[][] temp = {new Texture[8], new Texture[8], new Texture[8], new Texture[4], new Texture[5]};
        return populateAnimationTextures(temp, "Temp assets folder/Sprites/Evil Wizard/");
    }

    public void ai(Player player, Healthbar healthBar, int originalY) {

        if (health <= 0) {
            state = 4;
        } else {
            if (health != originalHealth) {
                state = 3;
                originalHealth = health;
            } else if (Math.abs(player.getHitBoxX() - (x + xOffSet)) < attackDistance) {
                state = 2;
            } else if (Math.abs(player.getHitBoxX() - (x + xOffSet)) < moveDistance) {
                state = 1;
            } else {
                state = 0;
            }


        }
        enemyAnimation = new Animation<>(0.2f, animationTextures[state]);
//0-idle,1-move,2-attack,3-hurt,4-death
        switch (state) {
            case 0:
                break;
            case 1:
                move(player);
                break;
            case 2:
                if (player.getY() < originalY + 200) {
                    attack(healthBar);
                }

                break;
            case 3:
                break;
            case 4:
                alive = false;
                player.setScore(player.getScore()+1);
                break;

        }

    }


}

