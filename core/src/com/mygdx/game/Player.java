package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Player {

    private final AP9 ap9;
    private boolean isJumping;
    private int x;
    private int y;
    private int HitBoxX;
    private int HitBoxY;
    private int jumpHeight;
    private int state;
    private Texture[][] animationTextures;
    private Animation animation;
    private double health;
    private boolean isMoving;
    private boolean flipped;
    private int xOffSet;
    private boolean alive;
    private int score;


    public Player() {
        ap9 = new AP9();
        x = 20;
        y = 20;
        xOffSet = 0;
        populateTextures();
        isJumping = false;
        isMoving = false;
        jumpHeight = 400;
        health = 10;
        state = 0;
        flipped = false;
        alive = true;
        score=0;
    }



    public void populateTextures() {
        animationTextures = new Texture[][]{new Texture[6], new Texture[12], new Texture[14], new Texture[8], new Texture[6]};
        String filePath = "Temp assets folder/Sprites/Player/";
        for (int i = 0; i < animationTextures.length; i++) {
            for (int j = 0; j < animationTextures[i].length; j++) {
                animationTextures[i][j] = new Texture(Gdx.files.internal(filePath + i + "/frame (" + (j + 1) + ").png"));
            }
        }
        animation = new Animation(0.2f, animationTextures[state]);
    }

    public void controls(int originalY, float timeElapsed, Healthbar healthbar) {

        if (Gdx.input.isKeyPressed(Input.Keys.A) && x > -100) {
            x -= 20;
            isMoving = true;
            flipped = true;
            xOffSet = 80;

        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && x < 1800) {
            x += 20;
            isMoving = true;
            flipped = false;
            xOffSet = 0;

        } else {
            isMoving = false;
        }

        if (!isJumping) {
            if (y > originalY) {
                y -= 10;
            } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                isJumping = true;
            }
        } else if (y < getJumpHeight()) {
            y += 10;
        } else if (getY() == getJumpHeight()) {
            isJumping = false;

        }
        if (Gdx.input.isTouched()) {
            ap9.shoot();
        }
        ap9.updateSprite(timeElapsed, getX(), getY(), flipped);
        animate(healthbar,timeElapsed);
        alignHitBoxes();
    }

    public void animate(Healthbar healthbar,float timeElapsed) {
        if (healthbar.getCurrentHealth() <= 0) {
            state = 4;
        } else if (healthbar.getCurrentHealth() != health) {
            health = healthbar.getCurrentHealth();
            state = 3;
        } else if (isJumping) {
            state = 2;
        } else if (isMoving) {
            state = 1;
        } else {
            state = 0;
        }

        animation = new Animation(0.1f, animationTextures[state]);
        if (state==4&&animation.isAnimationFinished(timeElapsed)){
            alive=false;
        }

    }
    public AP9 getAp9() {
        return ap9;
    }

    public int getX() {
        return x;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public int getY() {
        return y;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    public boolean getJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    public int getJumpHeight() {
        return jumpHeight;
    }

    public void setJumpHeight(int jumpHeight) {
        this.jumpHeight = jumpHeight;
    }

    public Animation getAnimation() {
        return animation;
    }

    public int getHitBoxX() {
        return HitBoxX;
    }

    public void setHitBoxX(int hitBoxX) {
        HitBoxX = hitBoxX;
    }

    public int getHitBoxY() {
        return HitBoxY;
    }

    public void setHitBoxY(int hitBoxY) {
        HitBoxY = hitBoxY;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void alignHitBoxes() {
        HitBoxX = x + xOffSet;
        HitBoxY = y;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getHealth() {
        return health;
    }

}


