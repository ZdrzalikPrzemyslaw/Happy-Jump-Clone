package tech.szymanskazdrzalik.weather_game.game.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.ArrayList;
import java.util.List;

import tech.szymanskazdrzalik.weather_game.R;
import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.CharacterEntity;
import tech.szymanskazdrzalik.weather_game.gameView.GameOverException;

public class PlayerEntity extends CharacterEntity {
    public final static int defaultTextureHeight = 579 * 4 / 7;
    private final static int defaultTextureWidth = 421 * 4 / 7;
    private static final double ySpeedChange = 0.5;
    private static List<List<Bitmap>> textureTypesList;
    private static List<Bitmap> runningTextureList;
    private static List<Bitmap> deadTextureList;
    private double ySpeed = 0;
    private int currentTexture = 0;
    private TextureType currentTextureType = TextureType.Running;
    private EntityDirections directions = EntityDirections.RIGHT;
    private boolean hasDied = false;
    public PlayerEntity(PlayerEntity playerEntity) {
        super((int) playerEntity.getXPos(), (int) playerEntity.getYPos(), defaultTextureWidth, defaultTextureHeight, runningTextureList.get(0));
    }
    public PlayerEntity(int xPos, int yPos) {
        super(xPos, yPos, defaultTextureWidth, defaultTextureHeight, runningTextureList.get(0));
    }

    public static double getySpeedChange() {
        return ySpeedChange;
    }

    public static void init(Resources resources) {
        textureTypesList = new ArrayList<>();
        addRunningBitmaps(resources);
        addDeadBitmaps(resources);
    }

    private static void addRunningBitmaps(Resources resources) {
        runningTextureList = new ArrayList<>();
        textureTypesList.add(TextureType.Running.textureType, runningTextureList);
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

    private static void addDeadBitmaps(Resources resources) {
        deadTextureList = new ArrayList<>();
        textureTypesList.add(TextureType.Dead.textureType, deadTextureList);
        Bitmap bitmap1 = (Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.santa_dead_1)), defaultTextureWidth, defaultTextureHeight, false));
        Bitmap bitmap2 = (Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.santa_dead_2)), defaultTextureWidth, defaultTextureHeight, false));
        Bitmap bitmap3 = (Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.santa_dead_3)), defaultTextureWidth, defaultTextureHeight, false));
        Bitmap bitmap4 = (Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.santa_dead_4)), defaultTextureWidth, defaultTextureHeight, false));
        Bitmap bitmap5 = (Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.santa_dead_5)), defaultTextureWidth, defaultTextureHeight, false));
        Bitmap bitmap6 = (Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.santa_dead_6)), defaultTextureWidth, defaultTextureHeight, false));
        Bitmap bitmap7 = (Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.santa_dead_7)), defaultTextureWidth, defaultTextureHeight, false));
        Bitmap bitmap8 = (Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.santa_dead_8)), defaultTextureWidth, defaultTextureHeight, false));
        Bitmap bitmap9 = (Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(
                resources, R.drawable.santa_dead_9)), defaultTextureWidth, defaultTextureHeight, false));
        for (int i = 0; i < 4; i++) {
            deadTextureList.add(bitmap1);
        }
        for (int i = 0; i < 4; i++) {
            deadTextureList.add(bitmap2);
        }
        for (int i = 0; i < 4; i++) {
            deadTextureList.add(bitmap3);
        }
        for (int i = 0; i < 4; i++) {
            deadTextureList.add(bitmap4);
        }
        for (int i = 0; i < 4; i++) {
            deadTextureList.add(bitmap5);
        }
        for (int i = 0; i < 4; i++) {
            deadTextureList.add(bitmap6);
        }
        for (int i = 0; i < 4; i++) {
            deadTextureList.add(bitmap7);
        }
        for (int i = 0; i < 4; i++) {
            deadTextureList.add(bitmap8);
        }
        for (int i = 0; i < 4; i++) {
            deadTextureList.add(bitmap9);
        }
    }

    public boolean isHasDied() {
        return hasDied;
    }

    public void setYSpeedAfterPlatformCollision() {
        this.ySpeed = -22;
    }

    public void setYSpeedAfterPresentCollision() {
        this.ySpeed = -30;
    }

    private void changeTexture() throws GameOverException {
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
            if (currentTextureType == TextureType.Dead) {
                throw new GameOverException();
            }
            this.currentTexture = 0;
        }
    }

    private void setRunningTexture() {
        this.currentTextureType = TextureType.Running;
        this.currentTexture = 0;
    }

    public void setDeadTexture() {
        this.currentTextureType = TextureType.Dead;
        this.currentTexture = 0;
        this.hasDied = true;
    }

    @Override
    public void changeXPos(double delta) throws GameOverException {
        super.changeXPos(delta);
        changeTextureDirection(delta);
        changeTexture();
    }

    private void changeTextureDirection(double delta) {
        if (delta > 0) {
            this.directions = EntityDirections.RIGHT;
        } else if (delta < 0) {
            this.directions = EntityDirections.LEFT;
        }
    }

    @Override
    public void changeXPos(int delta) throws GameOverException {
        super.changeXPos(delta);
        changeTextureDirection(delta);
        changeTexture();
    }

    public double getYSpeed() {
        return ySpeed;
    }

    public void changeSpeedAfterGameTick() {
        this.ySpeed += ySpeedChange;
    }

    private enum TextureType {
        Running(0),
        Dead(1);

        int textureType;

        TextureType(int textureType) {
            this.textureType = textureType;
        }
    }
}
