package com.mygdx.game;

public class Entity {

    protected boolean alive = true;

    protected int x;
    protected int y;
    protected int xOffSet;
    protected int yOffSet;

    protected int speed;
    protected double health;
    protected double originalHealth;

    public Entity() {
    }

    protected void setPosition(int newX, int newY){
        x=newX;
        y=newY;
    }

    protected void translate(double translationX, double translationY){

        x+=translationX;
        y+=translationY;

    }

    protected void moveLeft(double speed) {
        x -= speed;
    }

    protected void moveRight(double speed) {
        x += speed;
    }

    protected void moveUp(double speed) {
        y += speed;
    }

    protected void moveDown(double speed) {
        y -= speed;
    }

    public boolean isAlive(){return alive;}
}
