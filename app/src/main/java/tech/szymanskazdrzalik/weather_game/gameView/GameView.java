package tech.szymanskazdrzalik.weather_game.gameView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.SurfaceView;

import tech.szymanskazdrzalik.weather_game.R;
import tech.szymanskazdrzalik.weather_game.game.GameEntities;
import tech.szymanskazdrzalik.weather_game.game.PlayerEntity;
import tech.szymanskazdrzalik.weather_game.game.TexturedGameEntity;
import tech.szymanskazdrzalik.weather_game.sensors.OrientationSensorsService;

public class GameView extends SurfaceView implements Runnable {
    private boolean isPlaying = false;
    private Sensor mSensorAccelerometer;
    private Sensor mSensorMagnetometer;
    private Sensor mSensorGyroscope;
    private Background background;
    private Paint paint;
    private GameEntities gameEntities;
    private SensorManager mSensorManager;
    private OrientationSensorsService orientationSensorsService;
    private Thread thread;

    public GameView(Context context) {
        super(context);
        this.init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init();
    }

    private void update() {
        System.out.println("CHANGE " + this.orientationSensorsService.getRollConvertedIntoPlayerPositionChangeCoeff());
        this.gameEntities.getPlayerEntity().changeXPos((int) (this.orientationSensorsService.getRollConvertedIntoPlayerPositionChangeCoeff() * 10));
        for (TexturedGameEntity t : this.gameEntities.getAllEntities()) {
            if (t.getXPos() + t.getTexture().getWidth() < 0) {
                t.changeXPos(this.background.getTexture().getWidth() + t.getTexture().getWidth());
            } else if (t.getXPos() > this.background.getTexture().getWidth()) {
                t.changeXPos(-this.background.getTexture().getWidth() - t.getTexture().getWidth());
            }
        }
    }

    private void drawBackground(Canvas canvas) {
        this.drawEntity(this.background, canvas);
    }

    private void drawCharacterEntities(Canvas canvas) {
        for (TexturedGameEntity e : this.gameEntities.getCharacterEntities()) {
            this.drawEntity(e, canvas);
        }
    }

    private void drawObjectEntities(Canvas canvas) {
        for (TexturedGameEntity e : this.gameEntities.getObjectGameEntities()) {
            this.drawEntity(e, canvas);
        }
    }

    private void drawEntity(TexturedGameEntity texturedGameEntity, Canvas canvas) {
        canvas.drawBitmap(texturedGameEntity.getTexture(), texturedGameEntity.getXPos(), texturedGameEntity.getYPos(), this.paint);
    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            this.drawBackground(canvas);
            this.drawObjectEntities(canvas);
            this.drawCharacterEntities(canvas);
            this.drawEntity(gameEntities.getPlayerEntity(), canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    /**
     * Caps the programs framerate to the specified amount.
     *
     * @param fps
     */

    private void sleep(long fps) {
        try {
            FrameRate.capFrameRate(fps);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        this.paint = new Paint();
        this.orientationSensorsService = new OrientationSensorsService(getContext());
        // TODO: 09.01.2021
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        background = new Background(w, h, getResources());
        // TODO: 09.01.2021 Change, testing
        this.gameEntities = new GameEntities(new PlayerEntity(w / 2, h / 2, 300, 300, getResources(), R.drawable.santa_idle));
        this.isPlaying = true;
    }

    @Override
    public void run() {
        while (true) {
            while (this.isPlaying) {
                this.update();
                this.draw();
                this.sleep(60);
            }
        }
    }

    public void resume() {
        this.orientationSensorsService.registerListeners();
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            this.orientationSensorsService.unregisterListeners();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
