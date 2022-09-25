package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;

public class LevelScreen extends ScreenAdapter {
    MusicManager musicManager;
    MyGdxGame game;
    Player player;
    SpriteBatch batch;
    Texture background;
    int originalY;
    PlayerHealthbar playerHealthbar;
    BossHealthbar bossHealthbar;
    Torch torch;
    UndeadExecutioner undeadExecutioner;
    EvWizzEnemy evWizzEnemy;

    float timeElapsed = 0;

    public LevelScreen(MyGdxGame game, Player player) {
        this.game = game;
        this.player = player;

    }

    @Override
    public void show() {

        setupMusic();
        undeadExecutioner=new UndeadExecutioner();
        evWizzEnemy=new EvWizzEnemy();
        torch = new Torch();
        bossHealthbar=new BossHealthbar(undeadExecutioner.originalHealth);
        playerHealthbar = new PlayerHealthbar();
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("Temp assets folder/Backgrounds/Background" + 1 + ".png"));
        setLocations();

        ScreenUtils.clear(Color.CLEAR);

    }

    @Override
    public void render(float Delta) {

        player.controls(originalY,timeElapsed,playerHealthbar);
        deleteDeadParticles();
        graphics();
        enemyDamage();//TODO: if not alive don't draw
        aiThink();
        musicManager.playMusic();
    }


    @Override
    public void hide() {
        dispose();
    }//call after done with screen

    @Override
    public void dispose() {
        musicManager.dispose();
        background.dispose();
        batch.dispose();
    }//call to dispose

    public void flipper() {
        int playerX = player.getX();
        if (evWizzEnemy.alive) {
            evWizzEnemy.flipper(playerX);
        }
        if (undeadExecutioner.alive) {
            undeadExecutioner.flipper(playerX);
        }

    }

    public void deleteDeadParticles() {
        if (undeadExecutioner.alive) {
            undeadExecutioner.undeadProjectiles=undeadExecutioner.deleteDeadProjectiles();
        }
    }

    public void aiThink() {
        if (evWizzEnemy.alive) {
            evWizzEnemy.ai(player, playerHealthbar, originalY);
        }
        if (undeadExecutioner.alive) {
            undeadExecutioner.ai(player, playerHealthbar,timeElapsed);
        }

    }

    public void graphics() {
        playerHealthbar.setCurrentTexture(playerHealthbar.getHealth());
        bossHealthbar.setCurrentTexture(undeadExecutioner.health);//TODO: find the difference between health and original health

        flipper();

        timeElapsed += Gdx.graphics.getDeltaTime();

        batch.begin();
        //each draw draws on top of previous draws
        batch.draw(background, 0, 0, 1920, 1080);
        Texture torchTexture = torch.getTorchAnimation().getKeyFrame(timeElapsed, true);
        batch.draw(torchTexture, 303, 230, 70, 100);
        batch.draw(torchTexture, 593, 230, 70, 100);
        batch.draw(torchTexture, 1267, 230, 70, 100);
        batch.draw(torchTexture, 1557, 230, 70, 100);
        batch.draw(playerHealthbar.getCurrentTexture(), playerHealthbar.getX(), playerHealthbar.getY(), 500, 80);

        if(bossHealthbar.bossAlive)
        batch.draw(bossHealthbar.getCurrentTexture(),bossHealthbar.getX(),bossHealthbar.getY(),600,200);

        if (evWizzEnemy.alive) {
            Texture evWizzEnemyTexture = evWizzEnemy.enemyAnimation.getKeyFrame(timeElapsed, true);
            batch.draw(evWizzEnemyTexture, evWizzEnemy.x, evWizzEnemy.y, 500, 500, 0, 0, 150, 150, evWizzEnemy.isFlipped(), false);
        }

        if (undeadExecutioner.alive) {
            Texture undeadExecutionerTexture = undeadExecutioner.enemyAnimation.getKeyFrame(timeElapsed, true);
            batch.draw(undeadExecutionerTexture, undeadExecutioner.x, undeadExecutioner.y, 700, 700, 0, 0, 150, 150, undeadExecutioner.isFlipped(), false);
        }

        Texture[] undeadProjectilesTextures = undeadExecutioner.populateUndeadProjectileTextures(timeElapsed);

       if(undeadProjectilesTextures!=null){
           for (int i = 0; i < undeadExecutioner.undeadProjectiles.length; i++) {
               batch.draw(undeadProjectilesTextures[i],
                       undeadExecutioner.undeadProjectiles[i].x, undeadExecutioner.undeadProjectiles[i].y, 150, 150);
                 }
       }
        Texture texture = (Texture) player.getAnimation().getKeyFrame(timeElapsed, true);
        batch.draw(texture, player.getX(), player.getY()-50, 280, 280,0,0,128,98,player.isFlipped(),false);
        player.getAp9().sprite.draw(batch);//280 width 280 height
        if(player.getAp9().bullets!=null){
            for (int i = 0; i <player.getAp9().bullets.size() ; i++) {
                if(player.getAp9().bullets.get(i).alive)
                batch.draw(player.getAp9().bullets.get(i).animation.getKeyFrame(timeElapsed),player.getAp9().bullets.get(i).x,player.getAp9().bullets.get(i).y,30,30);
            }
        }

        batch.end();
    }

    public void enemyDamage(){
        undeadExecutioner.checkBulletAndPain(player.getAp9().bullets, undeadExecutioner.x, undeadExecutioner.y, player.getAp9().damage);
        evWizzEnemy.checkBulletAndPain(player.getAp9().bullets, evWizzEnemy.x, evWizzEnemy.y, player.getAp9().damage);
    }

    public void setLocations() {
        player.setX(100);
        player.setY(60);
        originalY = player.getY();
        evWizzEnemy.y = -110;
        evWizzEnemy.x = 900;
        undeadExecutioner.x = 500;
        undeadExecutioner.y = -250;
    }

    public void setupMusic(){
    musicManager = new MusicManager();
    musicManager.playMusic();
    }



}
