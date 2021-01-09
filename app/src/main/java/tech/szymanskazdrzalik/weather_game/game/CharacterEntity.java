package tech.szymanskazdrzalik.weather_game.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;

public class CharacterEntity extends TexturedGameEntity {
    public CharacterEntity(Point location, int textureWidth, int textureHeight, Bitmap background) {
        super(location, textureWidth, textureHeight, background);
    }

    public CharacterEntity(int xPos, int yPos, int textureWidth, int textureHeight, Bitmap background) {
        super(xPos, yPos, textureWidth, textureHeight, background);
    }

    public CharacterEntity(Point location, int textureWidth, int textureHeight, Resources resources, int id) {
        super(location, textureWidth, textureHeight, resources, id);
    }

    public CharacterEntity(int xPos, int yPos, int textureWidth, int textureHeight, Resources resources, int id) {
        super(xPos, yPos, textureWidth, textureHeight, resources, id);
    }
}
