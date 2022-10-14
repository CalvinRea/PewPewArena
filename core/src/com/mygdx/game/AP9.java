package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.radDeg;

public class AP9  {//an AP9 is a type of gun

    private ArrayList<AP9Bullet> bullets;//ArrayList used because amount of bullets that will be shot is unknown
    private float angle;//the angle that that the users cursor is at in relation to the sprite
    private final double damage;
    private Sprite sprite;
    private final Animation<Texture> gunShot;
    private Texture gunTexture;
    private boolean shooting;

    public AP9() {
        damage = 1;
        bullets=new ArrayList<>();
        gunShot = new Animation(0.025f, populateAnimationTextures());
        gunShot.setPlayMode(Animation.PlayMode.LOOP);
        gunTexture = new Texture(Gdx.files.internal("Temp assets folder/Sprites/Gun pack 4/Animations/AP-9/frame (1).gif"));
        setupSprite();
    }

    public void setupSprite() {//used to initialise the sprite attribute
        sprite = new Sprite(gunTexture, 375, 250);
        sprite.setSize(100, 100);
        sprite.setOriginCenter();
        sprite.setOriginBasedPosition(1920 / 2f, 1080 / 2f);
        sprite.flip(true, false);
    }
    public void updateSprite(float timeElapsed, int playerX, int playerY, boolean flipped) {
    /*the time that has passed, player position and whether the player is flipped or not is used to
      update the sprite */
        if (shooting) {
            if (gunShot.isAnimationFinished(timeElapsed)) {
                shooting = false;
            }
            sprite.setTexture(gunShot.getKeyFrame(timeElapsed));
            if (gunShot.getKeyFrameIndex(timeElapsed) == 2) {
                Gdx.audio.newSound(Gdx.files.internal("Temp assets folder/Gun sounds/275151__bird-man__gun-shot.wav")).play(1f);
                spawnBullet(playerX, playerY);
            }
        } else {
            sprite.setTexture(gunTexture);
        }
        if (!flipped) {
            sprite.setPosition(playerX + 40, playerY + 50);
        } else {
            sprite.setPosition(playerX + 110, playerY + 50);
        }

        float cursorX = Gdx.input.getX();//gets the x co-ordinate of the cursor
        float cursorY = Gdx.graphics.getHeight() - Gdx.input.getY();//gets the y co-ordinate of the cursor
        float spriteX = sprite.getX() + sprite.getOriginX();
        float spriteY = sprite.getY() + sprite.getOriginY();
        float xDifference = cursorX - spriteX;// difference between the sprites x position respective to the origin and the cursors x position
        float yDifference = cursorY - spriteY;// difference between the sprites y position respective to the origin and the cursors y position
        angle = radDeg * MathUtils.atan2(yDifference, xDifference);//calculates the angle that the sprite should be rotated by based of off the atan of the differences
        sprite.setRotation(angle);
        updateBullets();
    }

    public void spawnBullet(int playerX, int playerY) {//creates a new bullet object based off of the players position
        bullets.add(new AP9Bullet());
        bullets.get(bullets.size() - 1).initialisePositioning(sprite, playerX, playerY);

    }


    public void updateBullets() {//updates each AP9 bullet object in the bullets array list
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).isAlive()) {
                bullets.get(i).move();
            } else {
                bullets.remove(i);
            }
        }
    }
    private Texture[] populateAnimationTextures() {//used to create and return a texture array used to initialise the gunshot animation
      Texture[] temp = new Texture[9];
        for (int i = 0; i < temp.length; i++) {

            temp[i] = new Texture(Gdx.files.internal("Temp assets folder/Sprites/Gun pack 4/Animations/AP-9/frame (" + (i + 1) + ").gif"));
        }
        return temp;
    }

    public void shoot() {// used to shoot the AP9 and sets shooting to true

        shooting = true;
    }

    public double getDamage(){return damage;}//returns the amount of damage the AP9 does

    public Sprite getSprite(){return sprite;}//returns the current AP9 sprite

    public ArrayList<AP9Bullet> getBullets(){return bullets;}//returns the current AP9 AP9Bullet objects present in the Bullets array list

}
