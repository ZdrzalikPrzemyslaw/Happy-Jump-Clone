package tech.szymanskazdrzalik.weather_game.gameView;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import tech.szymanskazdrzalik.weather_game.R;

public class Background {

    private int x, y;

    private Bitmap background;

    public Bitmap getBackground() {
        return background;
    }

    public void setBackground(Bitmap background) {
        this.background = background;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private Background() {
        this.x = 0;
        this.y = 0;
    }

    public Background(int x, int y, Bitmap background) {
        this();
        this.background = Bitmap.createScaledBitmap(background, x, y, false);
    }

    public Background(int x, int y, Resources resources, int id) {
        this();
        background = BitmapFactory.decodeResource(resources, id);
        background = Bitmap.createScaledBitmap(background, x, y, false);
    }
    /**
     * Creates a background with the default background
     */
    public Background(int x, int y, Resources resources) {
        this();
        System.out.println("EJJ");
        System.out.println(x);
        System.out.println(y);
        background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.example_background), x, y, false);
    }
}
