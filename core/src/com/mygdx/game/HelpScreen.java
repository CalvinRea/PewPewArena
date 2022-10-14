package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HelpScreen extends ScreenAdapter {
    SpriteBatch batch;
    MyGdxGame game;
    Stage stage;
    Table table;
    Texture background;
    float timeElapsed;

    public HelpScreen(MyGdxGame game) {
        this.game = game;
    }//used to set the classes MyGdxGame to
    // the current game and update which screen is to be displayed

    @Override
    public void show() {//like create method but runs on screens instantiation like a constructor
        timeElapsed = 0f;
        stage = new Stage();
        batch = new SpriteBatch();
        background = new Texture("Temp assets folder/Backgrounds/helpBackground.png");
        stageInitializer();
    }

    public void stageInitializer() {//used to initialise the stage and set the stage to be the current input processor
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);//size to stage
        table = tableInitializer();
        stage.addActor(table);

    }

    public Table tableInitializer() {//used to create a table object and change its layout and add buttons,specific fonts etc to the table before it is returned
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
        table.add(btnBack).uniform(true).size(stage.getWidth() / 2, stage.getHeight() / 2).padBottom(100);
        return table;
    }

    @Override
    public void hide() {
        dispose();
    }//call after done with screen

    @Override
    public void dispose() {//call to dispose textures etc. and free up memory
        batch.dispose();
        stage.dispose();
    }

    @Override
    public void render(float delta) {//renders the current animations/textures to the screen based on the time that has passed
        batch.begin();
        batch.draw(background, 0, 0, 1920, 1080);
        batch.end();
        stage.draw();
        timeElapsed += Gdx.graphics.getDeltaTime();
        stage.act(timeElapsed);
    }
}
