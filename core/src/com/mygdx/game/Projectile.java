package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Projectile extends Entity{
    protected int damageRangeX;
    protected int damageRangeY;
    protected double speedX;
    protected double speedY;
    protected Animation<Texture> animation;
    protected double damage;

}
