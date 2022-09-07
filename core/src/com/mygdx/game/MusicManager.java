package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;

public class MusicManager {
    private ArrayList<Music> musicList = new ArrayList<Music>();

    private int i =0;

    public MusicManager() {
        musicList.add(Gdx.audio.newMusic(Gdx.files.internal("Temp assets folder/Music/Robin Hustin - On Fire [NCS Release].mp3")));
        musicList.add(Gdx.audio.newMusic(Gdx.files.internal("Temp assets folder/Music/Clarx - H.A.Y [NCS Release].mp3")));
        for (Music m : musicList) {
            m.setVolume(1f);
            m.setLooping(false);
        }
    }

    public void playMusic() {
        if(i== musicList.size())
            i=0;
        if(i==0||!musicList.get(i-1).isPlaying())
            musicList.get(i).play();
        }

    public void dispose(){
        for (Music m: musicList) {
            m.dispose();
        }
    }
}
