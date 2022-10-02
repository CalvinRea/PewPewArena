package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class UndeadProjectile extends Projectile {

    private boolean tracking;
    private static boolean creationAnimationFinished;
    private static Animation<Texture> animation;
    private static final Texture[][] animationTextures=populate();

    public UndeadProjectile(boolean flipped, int undeadX, int undeadY, int summonNumber) {

        creationAnimationFinished = false;
        tracking = true;
        originalHealth = 1;
        health = 1;
        damage = 1;
        damageRangeX = 30;
        damageRangeY = 80;
        xOffSet = 50;
        yOffSet = 50;
        speedY = 1;
        speedX = 2;
        animation = new Animation<>(0.2f, animationTextures[1]);

        switch (summonNumber) {
            case 1:
                x = undeadX;
                y = undeadY + 800;
                break;

            case 2:
                x = undeadX + 200;
                y = undeadY + 600;
                break;

            case 3:
                x = undeadX - 200;
                y = undeadY + 600;
                break;
        }
    }

    public void ai(Player player, Healthbar playerHealthbar, float timeElapsed) {

        if (Math.abs(player.getHitBoxX() - (x - xOffSet)) < damageRangeX && Math.abs(player.getHitBoxY() - (y - yOffSet)) < damageRangeY) {
            attack(playerHealthbar, player);
        }
        if (!creationAnimationFinished && animation.isAnimationFinished(timeElapsed)) {
            animation = new Animation<>(0.2f, animationTextures[0]);
            animation.setPlayMode(Animation.PlayMode.LOOP);
            creationAnimationFinished = true;
        }
        move(player);

    }
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

    protected void attack(Healthbar playerHealthbar, Player player) {
        playerHealthbar.setCurrentHealth(playerHealthbar.getCurrentHealth() - damage);
        alive = false;
    }

    public static Texture[][] populate() {
        Texture[][] temp = {new Texture[4], new Texture[6]};
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                temp[i][j] = new Texture(Gdx.files.internal("Temp assets folder/Sprites/Undead Executioner/projectile/" + i + "/frame (" + (j + 1) + ").png"));
            }

        }
        return temp;
    }

    public static Animation<Texture> getAnimation() {
        return animation;
    }
}

