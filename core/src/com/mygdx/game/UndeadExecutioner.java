package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.ArrayList;

public class UndeadExecutioner extends Enemy {
    private static final int summonDistance=800;//TODO: make more variables static final if constant and the same across objects
    private static final int summonNumber=3;
    private ArrayList<UndeadProjectile> undeadProjectiles;

    public UndeadExecutioner() {
        hitBoxX = 125;
        hitBoxY = 125;
        hitBoxXOffSet = 100;
        hitBoxYOffSet = 425;
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
        enemyAnimation = new Animation<>(0.2f, animationTextures[state]);
    }//used to initialise the attributes of the class

    public Texture[][] populateSpecificAnimationTextures() {

        Texture[][] temp = {new Texture[8], new Texture[8], new Texture[13], new Texture[12], new Texture[18]};
        return populateAnimationTextures(temp, "Temp assets folder/Sprites/Undead executioner/");
    }//used to specify the size of each individual array based on how many textures there are per animation

    public void ai(Player player, Healthbar playerHealthbar, float timeElapsed) {
        //States 4-death , 3-summon , 2-attack , 1-move , 0-idle
        if (isFlipped()) {
            hitBoxXOffSet = 550;
            hitBoxYOffSet = 425;
        } else {
            hitBoxXOffSet = 100;
            hitBoxYOffSet = 425;
        }

        if (health <= 0) {
            state = 4;
        } else if (Math.abs(player.getHitBoxX() - (x + xOffSet)) < attackDistance) {
            state = 2;
        } else if (Math.abs(player.getHitBoxX() - (x + xOffSet)) < summonDistance) {
            state = 3;
        } else if (Math.abs(player.getHitBoxX() - (x + xOffSet)) < moveDistance) {
            state = 1;
        } else {
            state = 0;
        }


        switch (state) {
            case 0:
                enemyAnimation = new Animation<>(0.2f, animationTextures[state]);
                break;
            case 1:
                enemyAnimation = new Animation<>(0.2f, animationTextures[state]);
                move(player.getHitBoxX());
                break;
            case 2:
                enemyAnimation = new Animation<>(0.2f, animationTextures[state]);
                attack(playerHealthbar);
                break;
            case 3:
                enemyAnimation = new Animation<>(0.2f, animationTextures[state]);
                if (undeadProjectiles == null || undeadProjectiles.size()==0) {
                    undeadProjectiles = summon();
                }
                break;
            case 4:
                enemyAnimation = new Animation<>(0.2f, animationTextures[state]);
                alive = false;
                player.setScore(player.getScore()+1);
                break;

        }
        int i = 0;
        while (undeadProjectiles != null && i < undeadProjectiles.size()) {
            if (undeadProjectiles.get(i).isAlive()) {
                undeadProjectiles.get(i).ai(player, playerHealthbar, timeElapsed);
            } else {

                undeadProjectiles.remove(i);
            }
            i++;
        }

    }//used to determine what state the undead executioner is in, whether it will move, attack, etc.

    public ArrayList<UndeadProjectile> summon() {
        ArrayList<UndeadProjectile> temp = new ArrayList<>(3);
        for (int i = 0; i < summonNumber; i++) {
            temp.add(new UndeadProjectile(flipped, x + xOffSet, y + yOffSet, i + 1));
        }
        return temp;
    }//used to summon undead projectiles and return an array list of them

    public ArrayList<UndeadProjectile> getUndeadProjectiles(){return undeadProjectiles;}//returns an arraylist of the current undead projectiles
}