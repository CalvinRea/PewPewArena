package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;

public class MusicManager {
    private final ArrayList<Music> musicList = new ArrayList<>();
    private int i = 0;

    public MusicManager() {
        musicList.add(Gdx.audio.newMusic(Gdx.files.internal("Temp assets folder/Music/Robin Hustin - On Fire [NCS Release].mp3")));
        musicList.add(Gdx.audio.newMusic(Gdx.files.internal("Temp assets folder/Music/Clarx - H.A.Y [NCS Release].mp3")));
        for (Music m : musicList) {
            m.setVolume(1f);
            m.setLooping(false);
        }
    }

    public void playMusic() {
        if (i == musicList.size())
            i = 0;
        if (musicList.size()!=0&&(i == 0 || !musicList.get(i - 1).isPlaying()))
            musicList.get(i).play();
    }

    public void dispose() {
 int k = musicList.size();
        while(k>0) {
            musicList.get(0).stop();
            musicList.get(0).dispose();
            musicList.remove(0);
            k--;
        }

    }
}
