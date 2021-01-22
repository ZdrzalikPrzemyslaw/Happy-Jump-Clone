package tech.szymanskazdrzalik.weather_game.game.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.ArrayList;
import java.util.List;

import tech.szymanskazdrzalik.weather_game.R;
import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.CharacterEntity;

public class PlayerEntity extends CharacterEntity {
    private final static int defaultResource = R.drawable.santa_idle;
    private final static int defaultTextureWidth = 421 * 4 / 7;
    public final static int defaultTextureHeight = 579 * 4 / 7;
    private double ySpeed = 0;
    private int currentTexture = 0;
    private TextureType currentTextureType = TextureType.Running;
    private EntityDirections directions = EntityDirections.RIGHT;

    private static List<List<Bitmap>> textureTypesList;

    private enum TextureType {
        Running(0);
        private TextureType(int textureType) {
            this.textureType = textureType;
        }
        int textureType;
    }

    private static List<Bitmap> runningTextureList;


    public PlayerEntity(PlayerEntity playerEntity) {
        super((int) playerEntity.getXPos(), (int) playerEntity.getYPos(), defaultTextureWidth, defaultTextureHeight, runningTextureList.get(0));
    }

    public PlayerEntity(int xPos, int yPos) {
        super(xPos, yPos, defaultTextureWidth, defaultTextureHeight, runningTextureList.get(0));
    }

    public void setYSpeedAfterPlatformCollision() {
        this.ySpeed = -22;
    }

    public void setYSpeedAfterPresentCollision() {
        this.ySpeed = -30;
    }

    public static void init(Resources resources) {
        runningTextureList = new ArrayList<>();
        textureTypesList = new ArrayList<>();
        textureTypesList.add(0, runningTextureList);
    }

    private void addRunningBitmaps(Resources resources) {
        Bitmap bitmap1 = (Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.santa_run_01)), defaultTextureWidth, defaultTextureHeight, false));
        Bitmap bitmap2 = (Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.santa_run_02)), defaultTextureWidth, defaultTextureHeight, false));
        Bitmap bitmap3 = (Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.santa_run_03)), defaultTextureWidth, defaultTextureHeight, false));
        Bitmap bitmap4 = (Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.santa_run_04)), defaultTextureWidth, defaultTextureHeight, false));
        Bitmap bitmap5 = (Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.santa_run_05)), defaultTextureWidth, defaultTextureHeight, false));
        Bitmap bitmap6 = (Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.santa_run_06)), defaultTextureWidth, defaultTextureHeight, false));
        Bitmap bitmap7 = (Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.santa_run_07)), defaultTextureWidth, defaultTextureHeight, false));
        Bitmap bitmap8 = (Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.santa_run_08)), defaultTextureWidth, defaultTextureHeight, false));
        Bitmap bitmap9 = (Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.santa_run_09)), defaultTextureWidth, defaultTextureHeight, false));
        Bitmap bitmap10 = (Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.santa_run_10)), defaultTextureWidth, defaultTextureHeight, false));
        Bitmap bitmap11 = (Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.santa_run_11)), defaultTextureWidth, defaultTextureHeight, false));
        for (int i = 0; i < 4; i++) {
            runningTextureList.add(bitmap1);
        }
        for (int i = 0; i < 4; i++) {
            runningTextureList.add(bitmap2);
        }
        for (int i = 0; i < 4; i++) {
            runningTextureList.add(bitmap3);
        }
        for (int i = 0; i < 4; i++) {
            runningTextureList.add(bitmap4);
        }
        for (int i = 0; i < 4; i++) {
            runningTextureList.add(bitmap5);
        }
        for (int i = 0; i < 4; i++) {
            runningTextureList.add(bitmap6);
        }
        for (int i = 0; i < 4; i++) {
            runningTextureList.add(bitmap7);
        }
        for (int i = 0; i < 4; i++) {
            runningTextureList.add(bitmap8);
        }
        for (int i = 0; i < 4; i++) {
            runningTextureList.add(bitmap9);
        }
        for (int i = 0; i < 4; i++) {
            runningTextureList.add(bitmap10);
        }
        for (int i = 0; i < 4; i++) {
            runningTextureList.add(bitmap11);
        }
    }

    private void changeTexture() {
        Bitmap currentBitmap = textureTypesList.get(this.currentTextureType.textureType).get(this.currentTexture);
        if (this.directions == EntityDirections.LEFT) {
            Matrix matrix = new Matrix();
            matrix.preScale(-1, 1);
            this.setTexture(Bitmap.createBitmap(currentBitmap, 0, 0, currentBitmap.getWidth(), currentBitmap.getHeight(), matrix, true));
        } else {
            this.setTexture(currentBitmap);
        }
        this.currentTexture++;
        if (this.currentTexture >= textureTypesList.get(this.currentTextureType.textureType).size()) {
            this.currentTexture = 0;
        }
    }

    @Override
    public void changeXPos(double delta) {
        super.changeXPos(delta);
        changeTextureDirection(delta);
        changeTexture();
    }

    private void changeTextureDirection(double delta) {
        if (delta > 0) {
            this.directions = EntityDirections.RIGHT;
        }
        else if (delta < 0) {
            this.directions = EntityDirections.LEFT;
        }
    }

    @Override
    public void changeXPos(int delta) {
        super.changeXPos(delta);
        changeTextureDirection(delta);
        changeTexture();
    }

    public double getYSpeed() {
        return ySpeed;
    }

    public static final double ySpeedChange = 0.5;

    public void changeSpeedAfterGameTick() {
        this.ySpeed += ySpeedChange;
    }
}
