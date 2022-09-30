package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameOverScreen extends ScreenAdapter {

    MyGdxGame game;
    Stage stage;
    Table table;
    Texture[] arr;
    SpriteBatch batch;
    float timeElapsed;
    int numFrames;
    Animation<Texture> gameOver;

    public GameOverScreen(MyGdxGame game) {
        this.game = game;

    }

    @Override
    public void show() {
        stage = new Stage();
        numFrames = 52;
        batch = new SpriteBatch();
        timeElapsed = 0f;
        arr = new Texture[numFrames];
        populateTextArray();
        gameOver = new Animation<>(0.2f, arr);
        stageInitializer();
    }//like create method

    @Override
    public void render(float delta) {
        timeElapsed += Gdx.graphics.getDeltaTime();
        Texture toDraw = gameOver.getKeyFrame(timeElapsed, true);
        batch.begin();
        batch.draw(toDraw, 0, 0, 1920, 1080);
        batch.end();
        stage.act(timeElapsed);
        stage.draw();
    }

    public void populateTextArray() {
        for (int j = 0; j < numFrames; j++) {
            if (j < 10) {
                arr[j] = new Texture("Temp assets folder/Backgrounds/GameOver/frame_0" + j + "_delay-0.2s.gif");
            } else {
                arr[j] = new Texture("Temp assets folder/Backgrounds/GameOver/frame_" + j + "_delay-0.2s.gif");
            }
        }
    }

    public void stageInitializer() {
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);//size to stage
        table = tableInitializer();
        stage.addActor(table);

    }

    public Table tableInitializer() {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.fontColor = (Color.WHITE);
        buttonStyle.font = new BitmapFont(Gdx.files.internal("Temp assets folder/GameFont.fnt"));
        buttonStyle.font.getData().setScale(2f);
        TextButton btnBack = new TextButton("Back to main menu", buttonStyle);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        table.add(btnBack).size(stage.getWidth() / 2, stage.getHeight() / 2).padTop(500);
        return table;
    }

    @Override
    public void hide() {
        dispose();
    }//call after done with screen

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }//call to dispose
}
