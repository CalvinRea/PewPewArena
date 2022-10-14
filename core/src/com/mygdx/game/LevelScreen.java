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

    public LevelScreen(MyGdxGame game, Player player) {//constructor used to initialise the player and game fields by parsing them in
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

    }//similar to a constructor this method runs when the screen is created and is used to initialize many screen attributes

    @Override
    public void render(float Delta) {

        player.controls(originalY, timeElapsed, playerHealthbar);
        graphics();
        damageEnemies();
        aiThink();
        musicManager.playMusic();
    }//the main game loop that runs through the many methods required to play the game
    // based on Delta which is the time that has paassed


    @Override
    public void hide() {
        dispose();
    }//called after done with screen

    @Override
    public void dispose() {
        musicManager.dispose();
        batch.dispose();
    }//called to dispose assets and free up memory

    public void flipper() {
        int playerX = player.getX();
        if (evWizzEnemy.alive) {
            evWizzEnemy.flipper(playerX);
        }
        if (undeadExecutioner.alive) {
            undeadExecutioner.flipper(playerX);
        }

    }//flips enemy sprites to face the player


    public void aiThink() {
        if (evWizzEnemy.alive) {
            evWizzEnemy.ai(player, playerHealthbar, originalY);
        }
        if (undeadExecutioner.alive) {
            undeadExecutioner.ai(player, playerHealthbar, timeElapsed);
        }

    }//calls all enemy ais to run

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
            if (undeadExecutioner.getUndeadProjectiles() != null) {
                for (int i = 0; i < undeadExecutioner.getUndeadProjectiles().size(); i++) {
                    batch.draw(undeadExecutioner.getUndeadProjectiles().get(i).getAnimation().getKeyFrame(timeElapsed),
                            undeadExecutioner.getUndeadProjectiles().get(i).x, undeadExecutioner.getUndeadProjectiles().get(i).y, 150, 150);
                }
            }
        }

        Texture playerTexture = (Texture) player.getAnimation().getKeyFrame(timeElapsed, true);
        batch.draw(playerTexture, player.getX(), player.getY() - 50, 280, 280, 0, 0, 128, 98, player.isFlipped(), false);
        player.getAp9().getSprite().draw(batch);
        if (player.getAp9().getBullets() != null) {
            for (int i = 0; i < player.getAp9().getBullets().size(); i++) {
                batch.draw(player.getAp9().getBullets().get(i).getAnimation().getKeyFrame(timeElapsed), player.getAp9().getBullets().get(i).x, player.getAp9().getBullets().get(i).y, 30, 30);
            }
        }

        batch.end();

        if(!player.isAlive()&&player.getAnimation().isAnimationFinished(timeElapsed)){
            game.setScreen(new GameOverScreen(game));
        }
    }//displays all enemy,player,background textures,animations etc to the screen

    public void damageEnemies() {
        undeadExecutioner.checkBulletAndDamage(player.getAp9().getBullets(), undeadExecutioner.x, undeadExecutioner.y, player.getAp9().getDamage());
        evWizzEnemy.checkBulletAndDamage(player.getAp9().getBullets(), evWizzEnemy.x, evWizzEnemy.y, player.getAp9().getDamage());
        if(player.getScore()==maxScore){
            game.setScreen(new WinScreen(game));
        }
    }//checks and damages all enemies that come into contact with the players bullets

    public void setLocations() {
        player.setX(100);
        player.setY(60);
        originalY = player.getY();
        evWizzEnemy.y = -110;
        evWizzEnemy.x = 900;
        undeadExecutioner.x = 500;
        undeadExecutioner.y = -250;
    }//sets the spawn locations of the enemies

    public void setupMusic() {
        musicManager = new MusicManager();
        musicManager.playMusic();
    }//creates a music manager object used to manage the music played in this screen

}
