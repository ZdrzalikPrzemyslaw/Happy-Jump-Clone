package tech.szymanskazdrzalik.weather_game.gameView;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;

import tech.szymanskazdrzalik.weather_game.R;
import tech.szymanskazdrzalik.weather_game.game.entities.TexturedGameEntity;

public class Background extends TexturedGameEntity {

    /**
     * Creates a background with the default texture.
     */
    public Background(int textureWidth, int textureHeight, Resources resources) {
        super(Background.getDefaultPosition(), textureWidth, textureHeight, resources, R.drawable.bitmapa);
    }

    public Background(int textureWidth, int textureHeight, Bitmap background) {
        super(Background.getDefaultPosition(), textureWidth, textureHeight, background);
    }

    public Background(int textureWidth, int textureHeight, Resources resources, int id) {
        super(Background.getDefaultPosition(), textureWidth, textureHeight, resources, id);
    }

    private static Point getDefaultPosition() {
        return new Point(0, 0);
    }

}
