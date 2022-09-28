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

public class WinScreen extends ScreenAdapter {

    MyGdxGame game;
    Stage stage;
    Table table;
    Texture[] arr;
    SpriteBatch batch;
    float timeElapsed;
    int numFrames;
    Animation<Texture> winScreen;

    public WinScreen(MyGdxGame game) {
        this.game = game;

    }

    @Override
    public void show() {
        stage = new Stage();
        numFrames = 73;
        batch = new SpriteBatch();
        timeElapsed = 0f;
        arr = new Texture[numFrames];
        populateTextArray();
        winScreen = new Animation<>(0.08f, arr);
        stageInitializer();
    }//like create method

    @Override
    public void render(float delta) {
        timeElapsed += Gdx.graphics.getDeltaTime();
        Texture toDraw = winScreen.getKeyFrame(timeElapsed, true);
        batch.begin();
        batch.draw(toDraw, 0, 0);
        batch.end();
        stage.act(timeElapsed);
        stage.draw();
    }

    public void populateTextArray() {
        for (int j = 0; j < numFrames; j++) {
            if (j < 10) {
                arr[j] = new Texture("Temp assets folder/Backgrounds/WinScreen/frame_0" + j + "_delay-0.08s.gif");
            } else {
                arr[j] = new Texture("Temp assets folder/Backgrounds/WinScreen/frame_" + j + "_delay-0.08s.gif");
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
                game.setScreen(new MainMenu(game));
            }
        });
        table.add(btnBack).uniform(true).size(stage.getWidth() / 2, stage.getHeight() / 2);
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

