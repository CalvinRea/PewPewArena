package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class UndeadProjectile extends EnemyProjectile {

    private static boolean creationAnimationFinished;
    private static Animation<Texture> animation;
    private static Texture[][] animationTextures;

    public UndeadProjectile(boolean flipped,int undeadX, int undeadY, int summonNumber) {

        creationAnimationFinished=false;
        tracking = true;
        originalHealth = 1;
        health = 1;
        damage = 1;
        damageRangeX = 30;
        damageRangeY= 80;
        xOffSet = 50;
        yOffSet = 50;
        speedY = 1;
        speedX = 2;
        if(animationTextures==null){animationTextures = populate();
            animation = new Animation<>(0.2f, animationTextures[1]);}

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

        if (Math.abs(player.getHitBoxX() - (x - xOffSet)) < damageRangeX && Math.abs(player.getHitBoxY() - (y - yOffSet)) < damageRangeY) {
           attack(playerHealthbar, player);
        }
        if(!creationAnimationFinished && animation.isAnimationFinished(timeElapsed)){
            animation =new Animation<>(0.2f,animationTextures[0]);
            creationAnimationFinished=true;
        }
            move(player);

    }

    public static Animation<Texture> getAnimation() {
        return animation;
    }
}

