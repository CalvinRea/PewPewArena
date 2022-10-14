package com.mygdx.game;

public class Entity {

    protected boolean alive;
    protected int x;
    protected int y;
    protected int xOffSet;
    protected int yOffSet;
    protected int speed;
    protected double health;
    protected double originalHealth;

    public Entity() {
        alive=true;
    }//constructor used to initialise alive attribute

    protected void setPosition(int newX, int newY) {//a method that updates/changes the entities position to the new x and y positions
        x = newX;
        y = newY;
    }

    protected void translate(double translationX, double translationY) {//adds specified x and y values to the current entity x and y values

        x += translationX;
        y += translationY;

    }

    protected void moveLeft(double speed) {//decreases the x value by the specified speed
        x -= speed;
    }

    protected void moveRight(double speed) {
        x += speed;
    }//increases the x value by the specified speed

    protected void moveUp(double speed) {
        y += speed;
    }//increases the y value by the specified speed

    protected void moveDown(double speed) {
        y -= speed;
    }//decreases the y value by the specified speed

    public boolean isAlive() {
        return alive;
    }//returns the alive variables value
}
