package tech.szymanskazdrzalik.weather_game.game.entities;

import android.content.res.Resources;
import android.graphics.Point;

import tech.szymanskazdrzalik.weather_game.R;
import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.CharacterEntity;

public class PlayerEntity extends CharacterEntity {
    private final static int defaultResource = R.drawable.santa_idle;
    private final static int defaultTextureWidth = 421 * 4 / 7;
    public final static int defaultTextureHeight = 579 * 4 / 7;
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

    public void setYSpeedAfterPlatformCollision() {
        this.ySpeed = -22;
    }

    public void setYSpeedAfterPresentCollision() {
        this.ySpeed = -30;
    }

    public double getYSpeed() {
        return ySpeed;
    }

    public static final double ySpeedChange = 0.5;

    public void changeSpeedAfterGameTick() {
        this.ySpeed += ySpeedChange;
    }
}
