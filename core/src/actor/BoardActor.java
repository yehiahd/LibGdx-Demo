package actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import handler.AnimalFactory;
import handler.Assets;

/**
 * Created by yehia on 05/09/16.
 */
public class BoardActor extends Image {
    private final int rows;
    private final int cols;
    private AnimalActor [][] animalActors;

    public BoardActor(int rows,int cols) {
        this.rows = rows;
        this.cols = cols;


    }

    public void initialize(SpriteBatch batch) {

        animalActors = new AnimalActor[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                AnimalActor actor = AnimalFactory.getRandomizedAnimal(i, j);
                animalActors[i][j] =  actor;
            }
        }
        draw(batch);
    }

    public void draw(SpriteBatch batch) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        ShapeRenderer renderer = new ShapeRenderer();
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.setColor(new Color(1,1,1,0.3f));

        float W = rows * Assets.panda.getWidth();
        float H = cols * Assets.panda.getHeight();

        setOrigin((Gdx.graphics.getWidth() - W)/2.0f, (Gdx.graphics.getHeight() - H) /2.0f);
        renderer.rect(getOriginX(), getOriginY(), W,H);

        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                animalActors[i][j].draw(batch, getOriginX(), getOriginY());
            }
        }
        batch.end();
    }


}
