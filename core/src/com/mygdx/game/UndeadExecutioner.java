package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.ArrayList;
import java.util.List;

public class UndeadExecutioner extends Enemy {
    int summonDistance;
    UndeadProjectile[] undeadProjectiles;

    public UndeadExecutioner() {
        hitBoxX=125;
        hitBoxY=125;
        hitBoxXOffSet=100;
        hitBoxYOffSet=425;
        originalHealth = 50;
        health = 50;
        damage = 0.2;
        xOffSet = 350;
        yOffSet = -310;
        animationTextures = populateSpecificAnimationTextures();
        attackDistance = 65;
        moveDistance = 1500;
        speed = 1;
        state = 0;
        summonDistance = 800;
        enemyAnimation = new Animation<Texture>(0.2f, animationTextures[state]);//TODO: need this?
    }

    public Texture[][] populateSpecificAnimationTextures() {

        Texture[][] temp = {new Texture[8], new Texture[8], new Texture[13], new Texture[12], new Texture[18]};
        return populateAnimationTextures(temp, "Temp assets folder/Sprites/Undead executioner/");
    }

    public void ai(Player player, PlayerHealthbar playerHealthbar, float timeElapsed) {
        //States 5-hurt (no animation bossbar), 4-death , 3-summon , 2-attack , 1-move , 0-idle
        if(isFlipped()){
            hitBoxXOffSet=550;
            hitBoxYOffSet=425;
        }else{
            hitBoxXOffSet=100;
            hitBoxYOffSet=425;
        }
        //TODO: ask if this is best practice
        if (health <= 0) {
            state = 4;
        } else if (health != originalHealth) {
            state = 5;
        } else if (Math.abs(player.getX() - (x + xOffSet)) < attackDistance) {
            state = 2;
        } else if (Math.abs(player.getX() - (x + xOffSet)) < summonDistance) {
            state = 3;
        } else if (Math.abs(player.getX() - (x + xOffSet)) < moveDistance) {
            state = 1;
        } else {
            state = 0;
        }


        switch (state) {
            case 0:
                enemyAnimation = new Animation<Texture>(0.2f, animationTextures[state]);
                break;
            case 1:
                enemyAnimation = new Animation<Texture>(0.2f, animationTextures[state]);
                move(player);
                break;
            case 2:
                enemyAnimation = new Animation<Texture>(0.2f, animationTextures[state]);
                attack(playerHealthbar, player);
                break;
            case 3:
                enemyAnimation = new Animation<Texture>(0.2f, animationTextures[state]);
                if(undeadProjectiles==null){undeadProjectiles = summon();}
                break;
            case 4:
                enemyAnimation = new Animation<Texture>(0.2f, animationTextures[state]);
                alive = false;
                break;

        }
        int i = 0;
        while (undeadProjectiles != null && i < undeadProjectiles.length) {
            undeadProjectiles[i].ai(player, playerHealthbar,timeElapsed);
            i++;
        }

    }

    public UndeadProjectile[] summon() {
        UndeadProjectile[] temp = new UndeadProjectile[3];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = new UndeadProjectile( flipped,x+xOffSet, y+yOffSet, i + 1);
        }
        return temp;
    }

    public Texture[] populateUndeadProjectileTextures(float timeElapsed) {

        Texture[] projTextures = null;
        if (undeadProjectiles != null) {
            projTextures = new Texture[undeadProjectiles.length];
            for (int j = 0; j < undeadProjectiles.length; j++) {
                projTextures[j] = undeadProjectiles[j].animation.getKeyFrame(timeElapsed, true);}


            }


        return projTextures;
    }

    public UndeadProjectile[] deleteDeadProjectiles() {

        List<UndeadProjectile> currentAliveList = new ArrayList<>();
        UndeadProjectile[] temp;
 int i = 0;
        while (undeadProjectiles != null && i < undeadProjectiles.length) {
            if (undeadProjectiles[i].alive) {
               currentAliveList.add(undeadProjectiles[i]);
            }
            i++;
        }
if(currentAliveList.size()==0){
    temp=null;
}else{
   temp=currentAliveList.toArray(new UndeadProjectile[currentAliveList.size()]);
}
return temp;


    }

}