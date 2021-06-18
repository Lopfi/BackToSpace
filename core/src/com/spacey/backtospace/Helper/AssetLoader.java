package com.spacey.backtospace.Helper;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
public class AssetLoader {
    public final AssetManager manager = new AssetManager();
	
    public void loadAssets(boolean music){

        //load texture assets that are needed
        manager.load("player/Spaceman_walk.png", Texture.class);
        manager.load("menu/options.png", Texture.class);
        manager.load("menu/team.png", Texture.class);
        manager.load("items/Wood10x8.png", Texture.class);
        manager.load("items/Stone10x8.png", Texture.class);
        manager.load("player/Itembar.png", Texture.class);
        manager.load("tiles/ground/ground0.png", Texture.class);
        manager.load("tiles/ground/ground1.png", Texture.class);
        manager.load("tiles/ground/ground2.png", Texture.class);
        manager.load("tiles/ground/ground3.png", Texture.class);
        manager.load("tiles/space/space0.png", Texture.class);
        manager.load("tiles/space/space1.png", Texture.class);
        manager.load("tiles/space/space2.png", Texture.class);
        manager.load("tiles/space/space3.png", Texture.class);
        manager.load("tiles/space/space4.png", Texture.class);
        manager.load("tiles/space/space5.png", Texture.class);
        manager.load("tiles/space/space6.png", Texture.class);
        manager.load("tiles/space/space7.png", Texture.class);
        manager.load("tiles/dev_grid.png", Texture.class);

        //load music assets
        if (music){
            this.loadMusic();
        }
        //manager.load("music/BossMusic.mp3", Sound.class); Idk not loading yet cuz not needed here. maybe some if level = 2 load this

        //load other assets
    }

    public void loadMusic() {
        manager.load("music/IntroMusic.mp3", Sound.class);
        manager.load("music/GameMusic.mp3", Sound.class);
    }
}





