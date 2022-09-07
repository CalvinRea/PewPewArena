package com.mygdx.game;

import java.io.*;
import java.util.Scanner;

public class MainMenuFunctionality {
    MyGdxGame game;
    String username;

    public MainMenuFunctionality(MyGdxGame game, String username) {
        this.game = game;
        this.username = username;
    }

    public void newGame() {
        File file = new File("Temp assets folder/" + username + ".txt");

        try {
            file.createNewFile();

        } catch (IOException e) {
            file.delete();//file exists
        }

        try {
            file.createNewFile();
        } catch (IOException exception) {//never happens
            System.out.println(exception.getMessage());
        }


        try {
            FileWriter writer = new FileWriter(file);
            writer.write("Player inventory:\n");
            writer.write("Player location:1");
            writer.close();
            continueGame();
        } catch (IOException e1) {
            System.out.println(e1.getMessage());
        }


    }

    public void continueGame() {
        String inventory = "";
        int levelNum = 0;
        try {
            FileReader fileReader = new FileReader("C:\\Users\\reaca\\Desktop\\game\\Temp assets folder\\SaveFile.txt");
            Scanner scan = new Scanner(fileReader);
            char[] temp1 = scan.nextLine().toCharArray();
            char[] temp2 = scan.nextLine().toCharArray();

            scan.close();
            for (int i = 16; i < temp1.length; i++) {
                inventory += temp1[i];
            }
            for (int i = 15; i < temp2.length; i++) {
                levelNum = temp2[i] - 48;
            }

//game.setScreen(new LevelScreen1(game,new Player(inventory)));

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    public void difficutlySettings() {

    }

    public void graphicsSettings() {

    }

    public void controlls() {

    }

    public void exit() {
        exit();
    }


}


