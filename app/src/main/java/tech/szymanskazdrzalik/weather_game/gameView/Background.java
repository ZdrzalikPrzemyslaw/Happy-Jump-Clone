package tech.szymanskazdrzalik.weather_game.gameView;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import tech.szymanskazdrzalik.weather_game.R;

public class Background {

    private int x, y;

    Bitmap background;

    private Background() {
        this.x = 0;
        this.y = 0;
    }

    public Background(int x, int y, Bitmap background) {
        this();
        this.background = Bitmap.createScaledBitmap(background, this.x, this.y, false);
    }

    public Background(int x, int y, Resources resources, int id) {
        this();
        background = BitmapFactory.decodeResource(resources, id);
        background = Bitmap.createScaledBitmap(background, this.x, this.y, false);
    }
    /**
     * Creates a background with the default background
     */
    public Background(int x, int y, Resources resources) {
        this();
        background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.example_background), x, y, false);
    }
}
