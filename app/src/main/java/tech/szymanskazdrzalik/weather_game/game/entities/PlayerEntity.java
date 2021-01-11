package tech.szymanskazdrzalik.weather_game.game.entities;

import android.content.res.Resources;
import android.graphics.Point;

import tech.szymanskazdrzalik.weather_game.R;

public class PlayerEntity extends CharacterEntity {
    private final static int defaultResource = R.drawable.santa_idle;
    private final static int defaultTextureWidth = 300;
    private final static int defaultTextureHeight = 300;
    private double ySpeed = 0;

    public PlayerEntity(Point location, Resources resources) {
        super(location, defaultTextureWidth, defaultTextureHeight, resources, defaultResource);
    }

    public PlayerEntity(PlayerEntity playerEntity) {
        super((int) playerEntity.getXPos(), (int) playerEntity.getYPos(), defaultTextureWidth, defaultTextureHeight, playerEntity.getTexture());
    }

    public PlayerEntity(int xPos, int yPos, Resources resources) {
        super(xPos, yPos, defaultTextureWidth, defaultTextureHeight, resources, defaultResource);
    }

    public void setYSpeedAfterBoostEvent() {
        this.ySpeed = -15;
    }

    public double getYSpeed() {
        return ySpeed;
    }

    public void changeSpeedAfterGameTick() {
        this.ySpeed += 0.5;
    }
}
