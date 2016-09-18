package handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yehia on 05/09/16.
 */
public class Assets {

    public static Texture bg,centerBottom,logo;
    public static Texture bear,bunny,carrot,lemon,panda,pig;
    public static Sound sound;
    public static List<Texture> animalTextures;

	// Pre-Loads all the required assets needed throughout the whole app life-cycle.
    public static void load(){
        loadImages();
        loadSounds();
    }

    private static void loadSounds() {
        sound = Gdx.audio.newSound(getFile("data/sounds/3.wav"));
    }

//	// StackOverFlow'ed...apparently.
//    private static Pixmap FileHandle fileHandle{
//        Pixmap pixmap200 = new Pixmap(fileHandle);
//        Pixmap pixmap100 = new Pixmap(40, 40, pixmap200.getFormat());
//        pixmap100.drawPixmap(pixmap200,
//                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
//                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
//        );
//        return pixmap100;
//    }

    private static void loadImages(){
        bg = new Texture(getFile("data/images/background_tile.png"));
        centerBottom = new Texture(getFile("data/images/center_bottom.png"));
	    logo = new Texture(getFile("data/images/logo.png"));
	    pig = new Texture(getFile("data/images/game_piratePig.png"));
	    lemon = new Texture(getFile("data/images/game_lemon.png"));
	    panda = new Texture(getFile("data/images/game_panda.png"));
	    carrot = new Texture(getFile("data/images/game_carrot.png"));
	    bunny = new Texture(getFile("data/images/game_bunny.png"));
	    bear = new Texture(getFile("data/images/game_bear.png"));

	    animalTextures = new ArrayList<Texture>();
        animalTextures.add(pig);
        animalTextures.add(lemon);
        animalTextures.add(panda);
        animalTextures.add(carrot);
        animalTextures.add(bunny);
        animalTextures.add(bear);
    }

	public static FileHandle getFile(String path) {
		return Gdx.files.internal(path);
	}

	public static Texture getTexture(int index) {
		return animalTextures.get(index);
	}
}
