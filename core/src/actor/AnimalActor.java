package actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by yehia on 05/09/16.
 */
public class AnimalActor extends Image {

    private Texture texture;
    private int row,col;
    private String type;

    public AnimalActor(int row, int col, String type, Texture texture){
        this.row=row;
        this.col=col;
        this.type = type;
        this.texture = texture;
    }

    public String getType() {
        return type;
    }

    public void draw(SpriteBatch batch, float originX, float originY){
        int x = row*texture.getWidth();
        int y = col*texture.getHeight();
        setOrigin(originX, originY);
        batch.draw(texture, originX + x, originY + y, texture.getWidth(), texture.getHeight());

    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AnimalActor))
            return false;
        AnimalActor that = (AnimalActor)o;
        return this.getType().equals(that.getType());
    }
}
