package tech.szymanskazdrzalik.weather_game.game.entities.parent_entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;

import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.CharacterEntity;

public class FriendlyEntity extends CharacterEntity {

    public FriendlyEntity(Point location, int textureWidth, int textureHeight, Bitmap background) {
        super(location, textureWidth, textureHeight, background);
    }

    public FriendlyEntity(int xPos, int yPos, int textureWidth, int textureHeight, Bitmap background) {
        super(xPos, yPos, textureWidth, textureHeight, background);
    }

    public FriendlyEntity(Point location, int textureWidth, int textureHeight, Resources resources, int id) {
        super(location, textureWidth, textureHeight, resources, id);
    }

    public FriendlyEntity(int xPos, int yPos, int textureWidth, int textureHeight, Resources resources, int id) {
        super(xPos, yPos, textureWidth, textureHeight, resources, id);
    }
}
