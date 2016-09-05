package handler;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by yehia on 05/09/16.
 */
public class Drawer {

    public static void draw(SpriteBatch batch, int width, int height){
        // TODO: 05/09/16 resizing logic.
        batch.draw(Assets.bg,0,0, width, height);
        batch.draw(Assets.centerBottom,0, 0, width, 150);
    }
}
