package tech.szymanskazdrzalik.weather_game.game.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.ArrayList;
import java.util.List;

import tech.szymanskazdrzalik.weather_game.R;

public class SnowballEntity extends HostileEntity {

    private final static int DEFAULT_TEXTURE_WIDTH = 256;
    private final static int DEFAULT_TEXTURE_HEIGHT = 193;
    private static List<Bitmap> bitmapList;
    private final double xSpeed = 10;
    private int currentTexture = 0;
    private HostileEntityDirections directions = HostileEntityDirections.LEFT;
    private double movedAmount = 0;

    public SnowballEntity(int xPos, int yPos) {
        super(xPos, yPos, DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, bitmapList.get(0));
    }

    public SnowballEntity(int xPos, int yPos, HostileEntityDirections directions) {
        super(xPos, yPos, DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, bitmapList.get(0));
        this.directions = directions;
    }

    public static void init(Resources resources) {
        bitmapList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            bitmapList.add(Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                    resources, R.drawable.snowball_01)), DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, false));
        }
        for (int i = 0; i < 4; i++) {
            bitmapList.add(Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                    resources, R.drawable.snowball_02)), DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, false));
        }
        for (int i = 0; i < 4; i++) {
            bitmapList.add(Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                    resources, R.drawable.snowball_03)), DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, false));
        }
        for (int i = 0; i < 4; i++) {
            bitmapList.add(Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                    resources, R.drawable.snowball_04)), DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, false));
        }
        for (int i = 0; i < 4; i++) {
            bitmapList.add(Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                    resources, R.drawable.snowball_05)), DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, false));
        }
        for (int i = 0; i < 4; i++) {
            bitmapList.add(Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                    resources, R.drawable.snowball_06)), DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, false));
        }
    }

    public double getMovedAmount() {
        return movedAmount;
    }

    public void setMovedAmount(double movedAmount) {
        this.movedAmount = movedAmount;
    }

    @Override
    public Bitmap getTexture() {
        return super.getTexture();
    }

    @Override
    public void changeXPos(int delta) {
        super.changeXPos(delta);
        this.changeTexture();
    }

    public HostileEntityDirections getDirections() {
        return directions;
    }

    public void setDirections(HostileEntityDirections directions) {
        this.directions = directions;
    }

    private void changeTexture() {
        Bitmap currentBitmap = bitmapList.get(this.currentTexture);
        if (this.directions == HostileEntityDirections.RIGHT) {
            Matrix matrix = new Matrix();
            matrix.postRotate(180);
            this.setTexture(Bitmap.createBitmap(currentBitmap, 0, 0, currentBitmap.getWidth(), currentBitmap.getHeight(), matrix, true));
        } else {
            this.setTexture(bitmapList.get(this.currentTexture));
        }
        this.currentTexture++;
        if (this.currentTexture >= bitmapList.size()) {
            this.currentTexture = 0;
        }
    }

    @Override
    public int getHitboxHeight() {
        return 1;
    }


    @Override
    public double getHitboxStartX() {
        if (this.directions == HostileEntityDirections.LEFT) {
            return this.getXPos() + this.getTexture().getWidth() / 4.f;
        } else {
            return this.getXPos() + this.getTexture().getWidth() * 3 / 4.f;
        }
    }

    @Override
    public double getHitboxStartY() {
        return this.getYPos() + this.getTexture().getHeight() / 2.f;
    }

    @Override
    public int getHitboxWidth() {
        return 1;
    }

    @Override
    public void changeXPos(double delta) {
        super.changeXPos(delta);
        this.changeTexture();
    }

    public void changeXPos() {
        if (this.directions == HostileEntityDirections.LEFT) {
            this.changeXPos(-this.xSpeed);
        } else {
            this.changeXPos(this.xSpeed);
        }
    }
}
