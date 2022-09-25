package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.io.*;
import java.sql.Date;
import java.util.Scanner;

public class LoginSignupScreen extends ScreenAdapter {
    //TODO: get rid of useless global variables
    int age;
    MyGdxGame game;
    Stage logStage;
    Table logTable;
    Texture[] logTextures;
    SpriteBatch logBatch;
    float timeElapsed;
    int logNumFrames;
    Animation<Texture> logAnimation;
    String[] usernames;
    String[] passwords;
    int count;
    String usernameEntry;
    String passwordEntry;
    String error;
    Label lblErr;
    Label lblAge;
    int yearEntry;
    int dayEntry;
    String monthEntry;
    TextureRegionDrawable chkBoxCheckedTexture;
    TextureRegionDrawable chkBoxUncheckedTexture;

    public LoginSignupScreen(MyGdxGame game) {
        this.game = game;
    }

    @Override
    public void show() {

        logStage = new Stage();
        logNumFrames = 1;
        logBatch = new SpriteBatch();
        timeElapsed = 0f;
        logTextures = new Texture[logNumFrames];
        populateTextArray();
        logAnimation = new Animation<>(0.016f, logTextures);
        populateList();
        stageInitializer();

    }

    public void populateTextArray() {
        for (int j = 0; j < logNumFrames; j++) {
            logTextures[j] = new Texture("Temp assets folder/frames/frame (" + (j + 1) + ").png");
        }
    }

    public void stageInitializer() {
        logStage = new Stage();
        Gdx.input.setInputProcessor(logStage);
        tableInitializer();
        logStage.addActor(logTable);
    }

    public void tableInitializer() {
        age = 0;
        error = "";
        usernameEntry = "";
        passwordEntry = "";
        yearEntry = 0;
        dayEntry = 0;
        monthEntry = "";

        logTable = new Table();
        logTable.setFillParent(true);
        logTable.defaults().padBottom(50);
        logTable.setX(170);

        TextureRegion region = new TextureRegion();
        region.setRegion(new Texture(Gdx.files.internal("Temp assets folder/cursor.jpg")));

        Skin skin = new Skin();
        skin.add("cursor", region);

        final BitmapFont bitFont = new BitmapFont(Gdx.files.internal("Temp assets folder/GameFont.fnt"));
        bitFont.getData().setScale(1f);

        Drawable drawable = new BaseDrawable();
        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        List.ListStyle listStyle = new List.ListStyle(bitFont, Color.BLUE, Color.TEAL, drawable);
        SelectBox.SelectBoxStyle monthAppearance = new SelectBox.SelectBoxStyle(bitFont, Color.WHITE, null, scrollPaneStyle, listStyle);

        Drawable drawableKnob = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Temp assets folder/sliderKnob.png"))));
        Drawable drawableBackground = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Temp assets folder/sliderContainer.png"))));
        Slider.SliderStyle ageAppearance = new Slider.SliderStyle(drawableBackground, drawableKnob);

        TextField.TextFieldStyle userPassAppearance = new TextField.TextFieldStyle();
        userPassAppearance.font = bitFont;
        userPassAppearance.fontColor = Color.WHITE;
        userPassAppearance.cursor = skin.newDrawable("cursor");
        userPassAppearance.cursor.setMinWidth(20);

        CheckBox.CheckBoxStyle robotStyle = new CheckBox.CheckBoxStyle();
        robotStyle.font = bitFont;
        robotStyle.fontColor = Color.WHITE;
        robotStyle.checkboxOn = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Temp assets folder/chkRobotChecked.png"))));
        robotStyle.checkboxOff = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Temp assets folder/chkRobotUnchecked.png"))));

        TextField.TextFieldFilter numFilter = new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField textField, char c) {
                boolean bool = true;
                if (!Character.isDigit(c)) {
                    bool = false;
                    error = "Error,enter a number";
                }
                return bool;
            }
        };

        TextField.TextFieldListener usernameListener = new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                usernameEntry = textField.getText();
            }
        };
        TextField.TextFieldListener passwordListener = new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                passwordEntry = textField.getText();
            }
        };
        TextField.TextFieldListener yearListener = new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                try {
                    yearEntry = Integer.parseInt(textField.getText());
                } catch (NumberFormatException e) {
                    yearEntry = 0;
                }
            }
        };


        TextField.TextFieldListener dayListener = new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                try {
                    dayEntry = Integer.parseInt(textField.getText());
                    if (!isWithinRange()) {
                        error = "Out of day range";
                        textField.setText("");
                    }
                } catch (NumberFormatException e) {
                    dayEntry = 0;
                }

            }
        };

        usernameEntry = usernameEntry.trim();
        passwordEntry = passwordEntry.trim();

        final SelectBox<String> cmbMonth = new SelectBox<>(monthAppearance);
        cmbMonth.setItems("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

        final Slider sldAge = new Slider(0, 100, 1, false, ageAppearance);
        sldAge.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                age = (int) sldAge.getValue();
                return true;
            }
        });

        final CheckBox chkRobot = new CheckBox("I am not a robot", robotStyle);

        final TextField txtYear = new TextField("", userPassAppearance);
        txtYear.setMaxLength(4);
        txtYear.setTextFieldListener(yearListener);
        txtYear.setTextFieldFilter(numFilter);

        final TextField txtDay = new TextField("", userPassAppearance);
        txtDay.setMaxLength(2);
        txtDay.setTextFieldListener(dayListener);
        txtDay.setTextFieldFilter(numFilter);

        final TextField txtUsername = new TextField("", userPassAppearance);
        txtUsername.setMaxLength(10);
        txtUsername.setTextFieldListener(usernameListener);

        final TextField txtPassword = new TextField("", userPassAppearance);
        txtPassword.setMaxLength(10);
        txtPassword.setPasswordMode(true);
        txtPassword.setPasswordCharacter('*');
        txtPassword.setTextFieldListener(passwordListener);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.fontColor = (Color.WHITE);
        buttonStyle.font = new BitmapFont(Gdx.files.internal("Temp assets folder/GameFont.fnt"));


        TextButton btnRegister = new TextButton("Register", buttonStyle);
        btnRegister.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!usernameEntry.equals("")|| !passwordEntry.equals("")) {
                    if (count < 5) {
                        age = (int) sldAge.getValue();
                        if (checkAge()) {//add later validation for if you are a robot
                            Label.LabelStyle greenStyle = new Label.LabelStyle(bitFont, Color.GREEN);
                            lblErr.setStyle(greenStyle);
                            error = "Success!";
                            createNewUserPass();
                        } else {
                            error = "Error,too young/incorrect age";
                        }
                    } else {
                        error = "Error, can have a maximum of 5 accounts";
                    }
                } else {
                    error = "Error, an empty space is not a username/password";
                }

            }
        });

        TextButton btnLogin = new TextButton("Login", buttonStyle);
        btnLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (usernames[0] != null) {
                    boolean exists = searchUserAndPass();
                    if (exists) {
                        game.setScreen(new MainMenu(game));
                    }
                }

            }
        });

        Label.LabelStyle errorLabelStyle = new Label.LabelStyle(bitFont, Color.RED);
        lblErr = new Label(error, errorLabelStyle);
        lblErr.setFontScale(1f);

        Label.LabelStyle sliderLabelStyle = new Label.LabelStyle(bitFont, Color.WHITE);
        lblAge = new Label("0", sliderLabelStyle);

        logTable.row();
        logTable.add(lblErr).fill().padTop(100);
        logTable.row();
        logTable.add(txtUsername).fill().width(500);
        logTable.row();
        logTable.add(txtPassword).fill().width(500);
        logTable.row();
        logTable.add(lblAge);
        logTable.add(sldAge).width(800);
        logTable.row();
        logTable.add(txtYear).fill();
        logTable.add(cmbMonth);
        logTable.add(txtDay);
        logTable.row();
        logTable.add(btnLogin);
        logTable.row();
        logTable.add(btnRegister);
        logTable.row();
        logTable.add(chkRobot).getActor().getImage().setScale(0.5f);

    }

    @Override
    public void render(float delta) {
        timeElapsed += Gdx.graphics.getDeltaTime();
        Texture toDraw = logAnimation.getKeyFrame(timeElapsed, false);
        logBatch.begin();
        logBatch.draw(toDraw, 0, 0, 1920, 1080);
        logBatch.end();
        if (logAnimation.isAnimationFinished(timeElapsed)) {
            logStage.act(timeElapsed);
            logStage.draw();
            updateLabels();
        }

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        logBatch.dispose();
        logStage.dispose();
    }

    public void populateList() {
        usernames = new String[5];
        passwords = new String[usernames.length];
        count = 0;
        try {

            Scanner scan = new Scanner(new FileReader("Temp assets folder/UsernamesPasswords.txt"));
            while (scan.hasNext()) {
                String line = scan.nextLine();
                String[] split = line.split("#");
                usernames[count] = split[0];
                passwords[count] = split[1];
                count++;
            }
            scan.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }


    }

    public int searchUser() {
        int index = -1;
        int j = 0;
        while (index == -1 && j < count) {
            if (usernames[j].equals(usernameEntry)) {
                index = j;
            }
            j++;
        }
        return index;
    }

    public boolean searchUserAndPass() {
        //used by login
        int index = searchUser();
        boolean bool = false;
        if (index == -1) {
            error = "Username does not exist";
        } else {
            if (passwords[index].equals(passwordEntry)) {
                bool = true;
            } else {
                error = "Incorrect Password";
            }
        }
        return bool;
    }

    public void createNewUserPass() {

        if (usernameEntry.contains("#") || passwordEntry.contains("#")) {
            error = "Username and password cannot contain # characters";
        } else if (searchUser() != -1) {
            error = "Username already exists";
        } else {

            File file = new File("Temp assets folder/UsernamesPasswords.txt");
            try {
                FileWriter writer = new FileWriter(file, true);

                writer.write(usernameEntry + "#" + passwordEntry + "\n");

                writer.close();
                populateList();
            } catch (IOException ioException) {
                System.out.println(ioException);
            }

        }

    }

    public boolean checkAge() {
        if (age < 8) {
            return false;
        } else {
            return calculateAge() == age;
        }
    }

    public int calculateAge() {
        int monthBirth = 0;
        switch (monthEntry) {
            case "January":
                monthBirth = 1;
                break;
            case "February":
                monthBirth = 2;
                break;
            case "March":
                monthBirth = 3;
                break;
            case "April":
                monthBirth = 4;
                break;
            case "May":
                monthBirth = 5;
                break;
            case "June":
                monthBirth = 6;
                break;
            case "July":
                monthBirth = 7;
                break;
            case "August":
                monthBirth = 8;
                break;
            case "September":
                monthBirth = 9;
                break;
            case "October":
                monthBirth = 10;
                break;
            case "November":
                monthBirth = 11;
                break;
            case "December":
                monthBirth = 12;
                break;
        }
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        String[] currentDate = date.toString().split("-");//leap year too
        int currentYear = Integer.parseInt(currentDate[0]);
        int currentMonth = Integer.parseInt(currentDate[1]);
        int currentDay = Integer.parseInt(currentDate[2]);

        int calculatedAge = currentYear - yearEntry - 1;
        //bottom code checks if birthdays passed
        if (currentMonth > monthBirth) {//adds a year if this year birthday has passed
            calculatedAge += 1;
        } else if (currentMonth == monthBirth) {
            if (currentDay > dayEntry) {
                calculatedAge += 1;
            }
        }

        return calculatedAge;
    }

    public boolean isWithinRange() {
        boolean bool = false;

        int maxDays = 0;
        switch (monthEntry) {
            case "January":
                maxDays = 31;
                break;
            case "February":
                if (yearEntry % 4 == 0) {
                    maxDays = 29;
                } else {
                    maxDays = 28;
                }
                break;
            case "March":
                maxDays = 31;
                break;
            case "April":
                maxDays = 30;
                break;
            case "May":
                maxDays = 31;
                break;
            case "June":
                maxDays = 30;
                break;
            case "July":
                maxDays = 31;
                break;
            case "August":
                maxDays = 31;
                break;
            case "September":
                maxDays = 30;
                break;
            case "October":
                maxDays = 31;
                break;
            case "November":
                maxDays = 30;
                break;
            case "December":
                maxDays = 31;
                break;
        }

        if (0 < dayEntry && dayEntry >= maxDays) {
            bool = true;
        }
        return bool;
    }


    public void updateLabels() {
        lblErr.setText(error);
        lblAge.setText(age + "");
    }


}
