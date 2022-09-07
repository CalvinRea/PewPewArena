package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;


public class UndeadProjectile extends EnemyProjectile {
    boolean creationAnimationFinished;
    public UndeadProjectile(boolean flipped,int undeadX, int undeadY, int summonNumber) {

        creationAnimationFinished=false;
        tracking = true;
        originalHealth = 1;
        health = 1;
        damage = 1;
        damageRangeX = 37;
        damageRangeY= 70;
        xOffSet = 50;
        yOffSet = 50;
        animationTextures = populate();
        speedY = 1;
        speedX = 2;
        animation = new Animation<Texture>(0.2f, animationTextures[1]);


            switch (summonNumber) {
                case 1:
                    x = undeadX;
                    y = undeadY + 800;
                    break;

                case 2:
                    x = undeadX + 200;
                    y = undeadY+600;
                    break;

                case 3:
                    x = undeadX - 200;
                    y = undeadY+600;
                    break;
            }

    }

    public Texture[][] populate() {
        Texture[][] temp = {new Texture[4],new Texture[6]};
        return populateAnimationTextures(temp, "Temp assets folder/Sprites/Undead Executioner/projectile/");

    }

    public void ai(Player player, PlayerHealthbar playerHealthbar, float timeElapsed) {
        //two states attacking
        if (Math.abs( x - xOffSet) < player.getHitBoxX()
                && Math.abs(y-yOffSet) < player.getHitBoxY()) {
            attack(playerHealthbar, player);
        }
        if(!creationAnimationFinished && animation.isAnimationFinished(timeElapsed)){
            animation =new Animation<Texture>(0.2f,animationTextures[0]);
            creationAnimationFinished=true;
        }
            move(player);

    }


}

