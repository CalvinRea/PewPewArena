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

public class MainMenuScreen extends ScreenAdapter {

    static MyGdxGame game;
    Stage stage;
    Table table;
    Texture[] arr;
    SpriteBatch batch;
    float timeElapsed;
    int numFrames;
    Animation<Texture> mainMenu;
    Music music;

    public MainMenuScreen(MyGdxGame game) {
        this.game = game;
    }//used to set the classes MyGdxGame to
    // the current game and update which screen is to be displayed

    public static void controls() {
        game.setScreen(new HelpScreen(game));
    }//used to transition to the help screen

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
        mainMenu = new Animation<>(0.033f, arr);
        stageInitializer();
    }//like create method but runs on screens instantiation like a constructor

    @Override
    public void render(float delta) {
        music.play();
        timeElapsed += Gdx.graphics.getDeltaTime();
        Texture toDraw = mainMenu.getKeyFrame(timeElapsed, true);
        batch.begin();
        batch.draw(toDraw, 0, 0);
        batch.end();
        stage.act(timeElapsed);
        stage.draw();
    }//renders the current animations/textures to the screen based on the time that has passed (delta)

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        dispose();
    }//called after done with screen

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        music.dispose();
    }//call to dispose textures etc. and free up memory

    public void populateTextArray() {
        for (int j = 0; j < numFrames; j++) {
            arr[j] = new Texture("Temp assets folder/Looping/background (" + (j + 1) + ").jpg");
        }
    }//used to populate arr texture array with textures

    public void stageInitializer() {
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);//size to stage
        table = tableInitializer();
        stage.addActor(table);

    }//used to initialise the stage and set the stage to be the current input processor

    public Table tableInitializer() {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.fontColor = (Color.WHITE);

        buttonStyle.font = new BitmapFont(Gdx.files.internal("Temp assets folder/GameFont.fnt"));
        buttonStyle.font.getData().setScale(2f);
        TextButton btnPlay = new TextButton("Play", buttonStyle);
        TextButton btnControls = new TextButton("Controls", buttonStyle);
        TextButton btnExit = new TextButton("Exit", buttonStyle);


        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game, new Player()));
            }
        });

        btnControls.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controls();
            }
        });
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
            }
        });

        table.add(btnPlay).uniform(true).size(stage.getWidth() / 2, stage.getHeight() / 6);
        table.row();
        table.add(btnControls).uniform(true).fill();
        table.row();
        table.add(btnExit).uniform(true).fill();
        return table;
    }//used to create a table object and change its layout and add buttons,specific fonts etc to the table before it is returned
}
