package tech.szymanskazdrzalik.weather_game.game.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

import tech.szymanskazdrzalik.weather_game.R;

public class SnowballEntity extends HostileEntity {

    private final static int DEFAULT_TEXTURE_WIDTH = 256;
    private final static int DEFAULT_TEXTURE_HEIGHT = 193;
    private static List<Bitmap> bitmapList;
    private int currentTexture = 0;
    private HostileEntityDirections directions = HostileEntityDirections.LEFT;

    private double xSpeed = 10;

    public SnowballEntity(int xPos, int yPos) {
        super(xPos, yPos, DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, bitmapList.get(0));
    }

    public static void init(Resources resources) {
        bitmapList = new ArrayList<>();
        bitmapList.add(Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.snowball_01)), DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, false));
        bitmapList.add(Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.snowball_02)), DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, false));
        bitmapList.add(Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.snowball_03)), DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, false));
        bitmapList.add(Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.snowball_04)), DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, false));
        bitmapList.add(Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.snowball_05)), DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, false));
        bitmapList.add(Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.snowball_06)), DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, false));
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
        this.setTexture(bitmapList.get(this.currentTexture));
        this.currentTexture++;
        if (this.currentTexture >=  bitmapList.size()) {
            this.currentTexture = 0;
        }
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
