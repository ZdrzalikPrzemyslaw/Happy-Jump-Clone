package tech.szymanskazdrzalik.weather_game.game.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class TexturedGameEntity extends GameEntity {


    private Bitmap texture;

    public Bitmap getTexture() {
        return texture;
    }

    public void setTexture(Bitmap texture) {
        this.texture = texture;
    }

    public TexturedGameEntity(Point location, int textureWidth, int textureHeight, Bitmap bitmap) {
        super(location);
        this.texture = Bitmap.createScaledBitmap(bitmap, textureWidth, textureHeight, false);
    }

    public TexturedGameEntity(int xPos, int yPos, int textureWidth, int textureHeight, Bitmap bitmap) {
        this(new Point(xPos, yPos), textureWidth, textureHeight, bitmap);
    }

    public TexturedGameEntity(Point location, int textureWidth, int textureHeight, Resources resources, int id) {
        super(location);
        this.texture = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, id), textureWidth, textureHeight, false);
    }

    public TexturedGameEntity(int xPos, int yPos, int textureWidth, int textureHeight, Resources resources, int id) {
        this(new Point(xPos, yPos), textureWidth, textureHeight, resources, id);
    }
}
