package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
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

public class MainMenu extends ScreenAdapter {

    MyGdxGame game;
    Stage stage;
    Table table;
    Texture[] arr;
    SpriteBatch batch;
    float timeElapsed;
    int numFrames;
    Animation<Texture> mainMenu;
    Music music;
    String username;

    public MainMenu(MyGdxGame game, String username) {
        this.game = game;
        this.username = username;
    }

    @Override
    public void show() {

        music = Gdx.audio.newMusic(Gdx.files.internal("Temp assets folder/Music/Universe.mp3"));
        music.setVolume(1.0f);
        music.setLooping(true);
        stage = new Stage();
        numFrames = 182;
        batch = new SpriteBatch();
        timeElapsed = 0f;
        arr = new Texture[numFrames];
        populateTextArray();
        mainMenu = new Animation<Texture>(0.033f, arr);
        stageInitializer();
    }

    @Override
    public void render(float delta) {
        music.play();
        timeElapsed += Gdx.graphics.getDeltaTime();
        Texture toDraw = mainMenu.getKeyFrame(timeElapsed, true);
        batch.begin();
        batch.draw(toDraw, 0, 0);
        batch.end();
        stage.act(timeElapsed);//math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)
        stage.draw();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        music.dispose();
    }

    public void populateTextArray() {
        for (int j = 0; j < numFrames; j++) {
            arr[j] = new Texture("Temp assets folder/Looping/background (" + (j + 1) + ").jpg");
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

        TextButton btnNewGame = new TextButton("New Game", buttonStyle);
        TextButton btnContinue = new TextButton("Continue", buttonStyle);
        TextButton btnDifficulty = new TextButton("Difficulty Settings", buttonStyle);
        TextButton btnGraphics = new TextButton("Graphics Settings", buttonStyle);
        TextButton btnControls = new TextButton("Controls", buttonStyle);
        TextButton btnExit = new TextButton("Exit", buttonStyle);

        final MainMenuFunctionality mainMenuFunctionality = new MainMenuFunctionality(game, username);
        btnNewGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainMenuFunctionality.newGame();
            }
        });
        btnContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainMenuFunctionality.continueGame();
            }
        });
        btnDifficulty.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainMenuFunctionality.difficutlySettings();
            }
        });
        btnGraphics.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainMenuFunctionality.graphicsSettings();
            }
        });
        btnControls.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainMenuFunctionality.controlls();
            }
        });
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainMenuFunctionality.exit();
            }
        });

        table.add(btnNewGame).uniform(true).size(stage.getWidth() / 2, stage.getHeight() / 6);
        table.row();
        table.add(btnContinue).uniform(true).fill();
        table.row();
        table.add(btnDifficulty).uniform(true).fill();
        table.row();
        table.add(btnGraphics).uniform(true).fill();
        table.row();
        table.add(btnControls).uniform(true).fill();
        table.row();
        table.add(btnExit).uniform(true).fill();
        return table;
    }


}
