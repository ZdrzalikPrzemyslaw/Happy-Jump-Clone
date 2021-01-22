package tech.szymanskazdrzalik.weather_game.game.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.ArrayList;
import java.util.List;

import tech.szymanskazdrzalik.weather_game.R;
import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.HostileEntity;

public class SnowballEntity extends HostileEntity {

    public final static int DEFAULT_TEXTURE_WIDTH = 256;
    public final static int DEFAULT_TEXTURE_HEIGHT = 193;
    private static List<Bitmap> bitmapList;
    private final double xSpeed = 10;
    private int currentTexture = 0;
    private EntityDirections directions = EntityDirections.LEFT;

    public SnowballEntity(int xPos, int yPos) {
        super(xPos, yPos, DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, bitmapList.get(0));
    }

    public SnowballEntity(int xPos, int yPos, EntityDirections directions) {
        super(xPos, yPos, DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, bitmapList.get(0));
        this.directions = directions;
    }

    public static void init(Resources resources) {
        bitmapList = new ArrayList<>();
        Bitmap bitmap1 = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.snowball_01)), DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, false);
        Bitmap bitmap2 = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.snowball_02)), DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, false);
        Bitmap bitmap3 = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.snowball_03)), DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, false);
        Bitmap bitmap4 = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.snowball_04)), DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, false);
        Bitmap bitmap5 = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.snowball_05)), DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, false);
        Bitmap bitmap6 = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.snowball_06)), DEFAULT_TEXTURE_WIDTH, DEFAULT_TEXTURE_HEIGHT, false);
        for (int i = 0; i < 4; i++) {
            bitmapList.add(bitmap1);
        }
        for (int i = 0; i < 4; i++) {
            bitmapList.add(bitmap2);
        }
        for (int i = 0; i < 4; i++) {
            bitmapList.add(bitmap3);
        }
        for (int i = 0; i < 4; i++) {
            bitmapList.add(bitmap4);
        }
        for (int i = 0; i < 4; i++) {
            bitmapList.add(bitmap5);
        }
        for (int i = 0; i < 4; i++) {
            bitmapList.add(bitmap6);
        }
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

    public EntityDirections getDirections() {
        return directions;
    }

    public void setDirections(EntityDirections directions) {
        this.directions = directions;
    }

    private void changeTexture() {
        Bitmap currentBitmap = bitmapList.get(this.currentTexture);
        if (this.directions == EntityDirections.RIGHT) {
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
        if (this.directions == EntityDirections.LEFT) {
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
        if (this.directions == EntityDirections.LEFT) {
            this.changeXPos(-this.xSpeed);
        } else {
            this.changeXPos(this.xSpeed);
        }
    }
}
