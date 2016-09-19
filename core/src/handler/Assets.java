package handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yehia on 05/09/16.
 */
public class Assets implements Disposable{

	private static Assets instance;
	private Texture bottomImage, logo, background;
	private Texture bear,bunny,carrot,lemon,panda,pig;
	public Sound sound;
	private List<Texture> textures;

	public static Assets getInstance(){
		if (instance == null)
			instance = new Assets();

		return instance;
	}

	private Assets(){}

	// Pre-Loads all the required assets needed throughout the whole app life-cycle.
	public void load(){
		loadImages();
		loadSounds();
	}

	private void loadSounds() {
		sound = Gdx.audio.newSound(getFile("data/sounds/3.wav"));
	}

	private void loadImages(){
		background = new Texture(getFile("data/images/background_tile.png"));
		bottomImage = new Texture(getFile("data/images/center_bottom.png"));
		logo = new Texture(getFile("data/images/logo.png"));
		pig = new Texture(getFile("data/images/game_piratePig.png"));
		lemon = new Texture(getFile("data/images/game_lemon.png"));
		panda = new Texture(getFile("data/images/game_panda.png"));
		carrot = new Texture(getFile("data/images/game_carrot.png"));
		bunny = new Texture(getFile("data/images/game_bunny.png"));
		bear = new Texture(getFile("data/images/game_bear.png"));

		textures = new ArrayList<Texture>();
		textures.add(pig);
		textures.add(lemon);
		textures.add(panda);
		textures.add(carrot);
		textures.add(bunny);
		textures.add(bear);
	}

	public FileHandle getFile(String path) {
		return Gdx.files.internal(path);
	}

	public Texture getTextureAt(int index) {
		return textures.get(index);
	}

	public Texture getBottomImage() {
		return bottomImage;
	}

	public Texture getLogo() {
		return logo;
	}

	public Texture getBackground() {
		return background;
	}

	@Override
	public void dispose() {
		for (Texture texture : textures) {
			texture.dispose();
		}
	}
}
