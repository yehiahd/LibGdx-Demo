package handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

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
    public static List<Sprite> animalSprites;

    public static void load(){
        loadImages();
        loadSounds();
    }

    private static void loadSounds() {
        sound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/3.wav"));
    }

    private static Pixmap getPixMap(FileHandle fileHandle){
        Pixmap pixmap200 = new Pixmap(fileHandle);
        Pixmap pixmap100 = new Pixmap(40, 40, pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );
        return pixmap100;
    }

    private static void loadImages(){
        bg = new Texture(Gdx.files.internal("data/images/background_tile.png"));
        centerBottom = new Texture(Gdx.files.internal("data/images/center_bottom.png"));
        logo = new Texture(Gdx.files.internal("data/images/logo.png"));
        bear = new Texture(getPixMap(Gdx.files.internal("data/images/game_bear.png")));
        bunny = new Texture(getPixMap(Gdx.files.internal("data/images/game_bunny.png")));
        carrot = new Texture(getPixMap(Gdx.files.internal("data/images/game_carrot.png")));
        lemon = new Texture(getPixMap(Gdx.files.internal("data/images/game_lemon.png")));
        panda = new Texture(getPixMap(Gdx.files.internal("data/images/game_panda.png")));
        pig = new Texture(getPixMap(Gdx.files.internal("data/images/game_piratePig.png")));

        animalTextures = new ArrayList<Texture>();
        animalTextures.add(pig);
        animalTextures.add(lemon);
        animalTextures.add(panda);
        animalTextures.add(carrot);
        animalTextures.add(bunny);
        animalTextures.add(bear);

        animalSprites = new ArrayList<Sprite>();

        for (Texture texture:animalTextures){
            Sprite sprite = new Sprite(texture);
            animalSprites.add(sprite);
        }
        /*"pig",
        "lemon",
        "panda",
        "carrot",
        "bunny",
        "bear"*/
    }


}
