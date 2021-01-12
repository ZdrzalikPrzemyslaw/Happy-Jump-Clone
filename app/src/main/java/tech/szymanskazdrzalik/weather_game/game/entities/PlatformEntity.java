package tech.szymanskazdrzalik.weather_game.game.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import tech.szymanskazdrzalik.weather_game.R;

public class PlatformEntity extends ObjectEntity {

    public PlatformEntity(Point location, Resources resources, int centerPieceCount) {
        super(location, createPlatformBitmap(centerPieceCount, resources).getWidth(), createPlatformBitmap(centerPieceCount, resources).getHeight(), createPlatformBitmap(centerPieceCount, resources));
    }

    public PlatformEntity(int xPos, int yPos, Resources resources, int centerPieceCount) {
        super(xPos, yPos - getTextureHeight(resources), createPlatformBitmap(centerPieceCount, resources).getWidth(), getTextureHeight(resources), createPlatformBitmap(centerPieceCount, resources));
        System.out.println("HEJKA " + this.getTexture() + " " + this.getTexture().getWidth() + " " + this.getTexture().getHeight());
    }

    private static int getTextureHeight(Resources resources) {
        return Bitmap.createBitmap(BitmapFactory.decodeResource(resources, R.drawable.tile_left)).getHeight();
    }

    public static int getTextureWidth(Resources resources) {
        Bitmap tileLeft = Bitmap.createBitmap(BitmapFactory.decodeResource(resources, R.drawable.tile_left));
        Bitmap tileRight = Bitmap.createBitmap(BitmapFactory.decodeResource(resources, R.drawable.tile_right));
        Bitmap tileCenter = Bitmap.createBitmap(BitmapFactory.decodeResource(resources, R.drawable.tile_center));
        return tileLeft.getWidth() + tileCenter.getWidth() + tileRight.getWidth();
    }

    private static Bitmap createPlatformBitmap(int centerPieceCount, Resources resources) {
        if (centerPieceCount < 0) {
            throw new IllegalArgumentException();
        }
        Bitmap tileLeft = Bitmap.createBitmap(BitmapFactory.decodeResource(resources, R.drawable.tile_left));
        Bitmap tileRight = Bitmap.createBitmap(BitmapFactory.decodeResource(resources, R.drawable.tile_right));
        Bitmap tileCenter = Bitmap.createBitmap(BitmapFactory.decodeResource(resources, R.drawable.tile_center));

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
