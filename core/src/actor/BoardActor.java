package actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import handler.AnimalFactory;
import handler.Assets;

/**
 * Created by yehia on 05/09/16.
 */
public class BoardActor extends Image {
    private final int rows;
    private final int cols;
	private final int H;
	private final int W;
	private AnimalActor[][] animalActors;
	private AnimalActor selectedAnimal;

	public BoardActor(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
	    this.W = rows * Assets.panda.getWidth();
	    this.H = cols * Assets.panda.getHeight();
    }

    public void initialize(Stage stage) {
        animalActors = new AnimalActor[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                AnimalActor actor = AnimalFactory.getRandomizedAnimal(i, j, this);
                animalActors[i][j] =  actor;
	            stage.addActor(actor);
            }
        }
    }

	public void drawBoard() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        ShapeRenderer renderer = new ShapeRenderer();
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.setColor(new Color(1,1,1,0.3f));

//        setOrigin((Gdx.graphics.getWidth() - W)/2.0f, (Gdx.graphics.getHeight() - H) /2.0f);
        renderer.rect(getOriginX(), getOriginY(), W,H);

        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

//		for (int i = 0; i < rows; i++) {
//			for (int j = 0; j < cols; j++) {
////				animalActors[i][j].setOrigin(getOriginX(), getOriginY());
//				stage.addActor(animalActors[i][j]);
//			}
//		}
    }

	public void setSelectedAnimal(AnimalActor selectedAnimal) {
		this.selectedAnimal = selectedAnimal;
	}

	public void touchUp(int row, int col) {
		if (outOfBoundary(row, col)) return;

		AnimalActor otherAnimal = animalActors[row][col];
		if (selectedAnimal != null && selectedAnimal != otherAnimal){
			int tmpType = selectedAnimal.getTypeID();
			selectedAnimal.setTypeID(otherAnimal.getTypeID());
			otherAnimal.setTypeID(tmpType);
		}
	}

	private boolean outOfBoundary(int row, int col) {
		return row >= rows || col >= cols || row < 0 || col < 0;
	}
}
