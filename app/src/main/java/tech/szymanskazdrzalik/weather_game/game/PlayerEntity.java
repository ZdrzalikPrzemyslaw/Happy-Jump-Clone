package tech.szymanskazdrzalik.weather_game.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;

public class PlayerEntity extends CharacterEntity {
    private int ySpeed = 0;

    public void setYSpeedAfterBoostEvent() {
        this.ySpeed = -15;
    }

    public int getYSpeed() {
        return ySpeed;
    }

    public void changeSpeedAfterGameTick() {
        this.ySpeed += 1;
    }

    public PlayerEntity(Point location, int textureWidth, int textureHeight, Bitmap background) {
        super(location, textureWidth, textureHeight, background);
    }

    public PlayerEntity(int xPos, int yPos, int textureWidth, int textureHeight, Bitmap background) {
        super(xPos, yPos, textureWidth, textureHeight, background);
    }

    public PlayerEntity(Point location, int textureWidth, int textureHeight, Resources resources, int id) {
        super(location, textureWidth, textureHeight, resources, id);
    }

    public PlayerEntity(int xPos, int yPos, int textureWidth, int textureHeight, Resources resources, int id) {
        super(xPos, yPos, textureWidth, textureHeight, resources, id);
    }
}
