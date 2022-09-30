package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class LevelScreen extends ScreenAdapter {
    final int maxScore=2;
    MusicManager musicManager;
    MyGdxGame game;
    Player player;
    SpriteBatch batch;
    Texture background;
    int originalY;
    Healthbar playerHealthbar;
    Healthbar bossHealthbar;
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
        undeadExecutioner = new UndeadExecutioner();
        evWizzEnemy = new EvWizzEnemy();
        torch = new Torch();
        bossHealthbar = new Healthbar(800,800,undeadExecutioner.originalHealth,39,"Temp assets folder/Sprites/boss healthbar/frame (",").gif");
        playerHealthbar = new Healthbar(50,Gdx.graphics.getHeight() - 100,10, 11,"Temp assets folder/Sprites/MyHealthbar/",".png");
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("Temp assets folder/Backgrounds/Background" + 1 + ".png"));
        setLocations();

        ScreenUtils.clear(Color.CLEAR);

    }

    @Override
    public void render(float Delta) {

        player.controls(originalY, timeElapsed, playerHealthbar);
        graphics();
        damageEnemies();
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


    public void aiThink() {
        if (evWizzEnemy.alive) {
            evWizzEnemy.ai(player, playerHealthbar, originalY);
        }
        if (undeadExecutioner.alive) {
            undeadExecutioner.ai(player, playerHealthbar, timeElapsed);
        }

    }

    public void graphics() {
        playerHealthbar.setCurrentTexture(player.getHealth());
        bossHealthbar.setCurrentTexture(undeadExecutioner.health);

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

        if (bossHealthbar.getCurrentHealth()>0)
            batch.draw(bossHealthbar.getCurrentTexture(), bossHealthbar.getX(), bossHealthbar.getY(), 600, 200);

        if (evWizzEnemy.alive) {
            Texture evWizzEnemyTexture = evWizzEnemy.enemyAnimation.getKeyFrame(timeElapsed, true);
            batch.draw(evWizzEnemyTexture, evWizzEnemy.x, evWizzEnemy.y, 500, 500, 0, 0, 150, 150, evWizzEnemy.isFlipped(), false);
        }

        if (undeadExecutioner.alive) {
            Texture undeadExecutionerTexture = undeadExecutioner.enemyAnimation.getKeyFrame(timeElapsed, true);
            batch.draw(undeadExecutionerTexture, undeadExecutioner.x, undeadExecutioner.y, 700, 700, 0, 0, 150, 150, undeadExecutioner.isFlipped(), false);
            if (undeadExecutioner.undeadProjectiles != null) {
                for (int i = 0; i < undeadExecutioner.undeadProjectiles.size(); i++) {
                    batch.draw(undeadExecutioner.undeadProjectiles.get(i).getAnimation().getKeyFrame(timeElapsed),
                            undeadExecutioner.undeadProjectiles.get(i).x, undeadExecutioner.undeadProjectiles.get(i).y, 150, 150);
                }
            }
        }

        Texture playerTexture = (Texture) player.getAnimation().getKeyFrame(timeElapsed, true);
        batch.draw(playerTexture, player.getX(), player.getY() - 50, 280, 280, 0, 0, 128, 98, player.isFlipped(), false);
        player.getAp9().sprite.draw(batch);
        if (player.getAp9().bullets != null) {
            for (int i = 0; i < player.getAp9().bullets.size(); i++) {
                batch.draw(player.getAp9().bullets.get(i).animation.getKeyFrame(timeElapsed), player.getAp9().bullets.get(i).x, player.getAp9().bullets.get(i).y, 30, 30);
            }
        }

        batch.end();

        if(!player.isAlive()&&player.getAnimation().isAnimationFinished(timeElapsed)){
            game.setScreen(new GameOverScreen(game));
        }
    }

    public void damageEnemies() {
        undeadExecutioner.checkBulletAndPain(player.getAp9().bullets, undeadExecutioner.x, undeadExecutioner.y, player.getAp9().damage);
        evWizzEnemy.checkBulletAndPain(player.getAp9().bullets, evWizzEnemy.x, evWizzEnemy.y, player.getAp9().damage);
        if(player.getScore()==maxScore){
            game.setScreen(new WinScreen(game));
        }
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

    public void setupMusic() {
        musicManager = new MusicManager();
        musicManager.playMusic();
    }

}
