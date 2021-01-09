package tech.szymanskazdrzalik.weather_game.gameView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {
    private Background background;
    private static long start = 0, diff, wait;
    private Paint paint;

    private void capFrameRate(long fps) throws InterruptedException {
        GameView.wait = 1000 / fps;
        GameView.diff = System.currentTimeMillis() - start;
        if (diff < wait) {
            Thread.sleep(wait - diff);
        }
        start = System.currentTimeMillis();
    }

    private Thread thread;
    private boolean isPlaying = true;

    private void update() {

    }

    private void draw() {

        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(this.background.getBackground(), this.background.getX(), this.background.getY(), this.paint);
            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    /**
     * Caps the programs framerate to the specified amount.
     * @param fps
     */

    private void sleep(long fps) {
        try {
            this.capFrameRate(fps);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        this.paint = new Paint();
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
