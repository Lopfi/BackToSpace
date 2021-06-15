package com.spacey.backtospace.Helper;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
public class AssetLoad {
    public final AssetManager manager = new AssetManager();
	
    public void queueAddImages(){

        //load texture assets that are needed


        //load music assets
        manager.load("music/IntroMusic.mp3", Sound.class);
        manager.load("music/GameMusic.mp3", Sound.class);
        //manager.load("music/BossMusic.mp3", Sound.class); Idk not loading yet cuz not needed here. maybe some if level = 2 load this

        //load other assets
    }
}





