package tech.szymanskazdrzalik.weather_game.game.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;

public class HostileEntity extends CharacterEntity {
    public HostileEntity(Point location, int textureWidth, int textureHeight, Bitmap background) {
        super(location, textureWidth, textureHeight, background);
    }

    public HostileEntity(int xPos, int yPos, int textureWidth, int textureHeight, Bitmap background) {
        super(xPos, yPos, textureWidth, textureHeight, background);
    }

    public HostileEntity(Point location, int textureWidth, int textureHeight, Resources resources, int id) {
        super(location, textureWidth, textureHeight, resources, id);
    }

    public HostileEntity(int xPos, int yPos, int textureWidth, int textureHeight, Resources resources, int id) {
        super(xPos, yPos, textureWidth, textureHeight, resources, id);
    }
}
