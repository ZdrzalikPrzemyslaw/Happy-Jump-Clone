package tech.szymanskazdrzalik.weather_game.game.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import tech.szymanskazdrzalik.weather_game.R;
import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.ObjectEntity;

public class PlatformEntity extends ObjectEntity {

    private static Bitmap tileLeft;
    private static Bitmap tileCenter;
    private static Bitmap tileRight;

    // TODO: 12.01.2021 Zrobić żeby tekstury bazowe ładowały się tylko raz, w singletonie??? czy w statycznym polu żeby nie ładować przy kazdym tworzeniu od nowa
    public PlatformEntity(Point location, int centerPieceCount) {
        super(location, getTextureWidth(centerPieceCount), getTextureHeight(), createPlatformBitmap(centerPieceCount));
    }
    public PlatformEntity(int xPos, int yPos, int centerPieceCount) {
        super(xPos, yPos - getTextureHeight(), getTextureWidth(centerPieceCount), getTextureHeight(), createPlatformBitmap(centerPieceCount));
    }

    public static int getTextureHeight() {
        if (tileLeft == null) {
            // TODO: 12.01.2021 CUSTOM
            throw new RuntimeException();
        }
        return tileCenter.getHeight();
    }

    public static void init(Resources resources) {
        tileLeft = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.tile_left)), 128, 93, false);
        tileRight =  Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.tile_right)),128, 93, false);
        tileCenter = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.tile_center)),128, 93, false);
    }

    public static int getTextureWidth(int tileCenterCount) {
        if (tileLeft == null) {
            // TODO: 12.01.2021 CUSTOM
            throw new RuntimeException();
        }
        return tileLeft.getWidth() + tileCenterCount * tileCenter.getWidth() + tileRight.getWidth();
    }

    private static Bitmap createPlatformBitmap(int centerPieceCount) {
        if (centerPieceCount < 0) {
            throw new IllegalArgumentException();
        }

        if (tileLeft == null) {
            // TODO: 12.01.2021 CUSTOM
            throw new RuntimeException();
        }

        Bitmap result = Bitmap.createBitmap(tileLeft.getWidth() + tileRight.getWidth() + tileCenter.getWidth() * centerPieceCount, tileLeft.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(result);
        float x = 0;

        canvas.drawBitmap(tileLeft, x, 0, new Paint());
        x = x + tileLeft.getWidth();
        for (int i = 0; i < centerPieceCount; i++) {
            canvas.drawBitmap(tileCenter, x, 0, new Paint());
            x = x + tileCenter.getWidth();
        }
        canvas.drawBitmap(tileRight, x, 0, new Paint());
        return result;
    }
}
