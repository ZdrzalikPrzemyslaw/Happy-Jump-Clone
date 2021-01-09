package tech.szymanskazdrzalik.weather_game.gameView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {
    private Background background;
    private static long start = 0, diff, wait;

    private void capFrameRate(long fps) throws InterruptedException {
        GameView.wait = 1000 / fps;
        GameView.diff = System.currentTimeMillis() - start;
        if (diff < wait) {
            Thread.sleep(wait - diff);
        }
        start = System.currentTimeMillis();
    }

    private Thread thread;
    private boolean isPlaying;

    private void update() {

    }

    private void draw() {

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

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        background = new Background(w, h, getResources());
    }

    @Override
    public void run() {

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
