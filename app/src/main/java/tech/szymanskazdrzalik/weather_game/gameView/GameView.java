package tech.szymanskazdrzalik.weather_game.gameView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

import tech.szymanskazdrzalik.weather_game.R;
import tech.szymanskazdrzalik.weather_game.game.GameEntities;
import tech.szymanskazdrzalik.weather_game.game.PlayerEntity;
import tech.szymanskazdrzalik.weather_game.game.TexturedGameEntity;

public class GameView extends SurfaceView implements Runnable {
    private Background background;
    private Paint paint;
    private GameEntities gameEntities;


    private Thread thread;
    private boolean isPlaying = true;

    private void update() {

    }

    private void drawBackground(Canvas canvas) {
        this.drawEntity(this.background, canvas);
    }

    private void drawCharacterEntities(Canvas canvas) {
        for (TexturedGameEntity e : this.gameEntities.getCharacterEntities()) {
            this.drawEntity(e, canvas);
        }
    }

    private void drawObjectEnetities(Canvas canvas) {
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
            this.drawObjectEnetities(canvas);
            this.drawCharacterEntities(canvas);
            this.drawEntity(gameEntities.getPlayerEntity(), canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    /**
     * Caps the programs framerate to the specified amount.
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
        // TODO: 09.01.2021
    }

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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        background = new Background(w, h, getResources());
        // TODO: 09.01.2021 Change, testing
        this.gameEntities = new GameEntities(new PlayerEntity(w/2, h/2, 300, 300, getResources(), R.drawable.santa_idle));
    }

    @Override
    public void run() {
        while (this.isPlaying) {
            this.update();
            this.draw();
            this.sleep(60);
        }
    }

    public void resume() {
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
