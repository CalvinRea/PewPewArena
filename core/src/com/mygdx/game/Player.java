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
    private final int jumpHeight;
    private int state;
    private final Texture[][] animationTextures=populateTextures();
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
        isJumping = false;
        isMoving = false;
        jumpHeight = 400;
        health = 10;
        state = 0;
        flipped = false;
        alive = true;
        score=0;
        animation = new Animation(0.2f, animationTextures[state]);
    }//used to initialise the attributes of the class

    public Texture[][] populateTextures() {
        Texture[][] temp = new Texture[][]{new Texture[6], new Texture[12], new Texture[14], new Texture[8], new Texture[6]};
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                temp[i][j] = new Texture(Gdx.files.internal("Temp assets folder/Sprites/Player/" + i + "/frame (" + (j + 1) + ").png"));
            }
        }
        return temp;
    }//Used to initialise the player’s animation textures

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
    }//Takes input from the user and outputs the appropriate action: movement, shooting, jumping etc.

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

    }//is used to change the player animation based on the current player state

    public AP9 getAp9() {
        return ap9;
    }//returns the players ap9

    public int getX() {
        return x;
    }//returns the players x co-ordinate

    public void setX(int newX) {
        this.x = newX;
    }//sets the players x co-ordinate to a new x co-ordinate

    public int getY() {
        return y;
    }//returns the players y co-ordinate

    public void setY(int newY) {
        this.y = newY;
    }//sets the players y co-ordinate to a new y co-ordinate (newY)

    public int getJumpHeight() {
        return jumpHeight;
    }//returns the players jump height

    public Animation getAnimation() {
        return animation;
    }//returns the animation attribute

    public int getHitBoxX() {
        return HitBoxX;
    }//returns the HitBoxX attribute

    public int getHitBoxY() {
        return HitBoxY;
    }//returns the HitBoxY attribute

    public boolean isFlipped() {
        return flipped;
    }//returns the flipped attribute

    public void alignHitBoxes() {
        HitBoxX = x + xOffSet;
        HitBoxY = y;
    }//used to update the players hitboxes based on the players’ current position

    public boolean isAlive() {
        return alive;
    }//returns the alive attribute

    public int getScore() {
        return score;
    }//returns the score attribute

    public void setScore(int score) {
        this.score = score;
    }//sets the score attribute to a new value

    public double getHealth() {
        return health;
    }//returns the players current health

}


