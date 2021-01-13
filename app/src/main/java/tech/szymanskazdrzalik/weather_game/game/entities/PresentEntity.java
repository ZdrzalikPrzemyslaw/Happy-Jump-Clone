package tech.szymanskazdrzalik.weather_game.game.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tech.szymanskazdrzalik.weather_game.R;

public class PresentEntity extends FriendlyEntity {

    private final static int DEFAULT_TEXTURE_WIDTH = 128;
    private final static int DEFAULT_TEXTURE_HEIGHT = 128;

    private static List<Bitmap> presentsTextures;

    public PresentEntity(int xPos, int yPos) {
        super(xPos, yPos, DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, presentsTextures.get(new Random().nextInt(presentsTextures.size())));
    }

    public static void init(Resources resources) {
        presentsTextures = new ArrayList<>();
        presentsTextures.add(Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.present_1)), DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, false));
    }

}
