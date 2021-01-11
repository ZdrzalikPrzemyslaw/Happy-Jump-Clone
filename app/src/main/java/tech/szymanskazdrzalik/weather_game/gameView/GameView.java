package tech.szymanskazdrzalik.weather_game.gameView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

import tech.szymanskazdrzalik.weather_game.GameActivity;
import tech.szymanskazdrzalik.weather_game.game.GameEntities;
import tech.szymanskazdrzalik.weather_game.game.entities.PlatformEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.PlayerEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.TexturedGameEntity;
import tech.szymanskazdrzalik.weather_game.sensors.OrientationSensorsService;

public class GameView extends SurfaceView implements Runnable {
    private final List<GameEvent> gameEventList = new ArrayList<>();
    private boolean isPlaying = false;
    private Background background;
    private Paint paint;
    private GameEntities gameEntities;
    private final OnTouchListener touchListener = (v, event) -> {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                gameEventList.add(this::setPlayerSpeedBoostEvent);
                break;
        }
        v.performClick();
        return true;
    };

    private OrientationSensorsService orientationSensorsService;
    private Thread thread;
    private GameActivity.GameOverListener gameOverListener;

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

    private void checkGameOverConditions() throws GameOverException {
        if (this.gameEntities.getPlayerEntity().getYPos() > (this.background.getTexture().getHeight() + 10)) {
            throw new GameOverException("Game Over");
        }
    }

    private void checkIfEntitiesAreOnScreenXAxis() {
        for (TexturedGameEntity t : this.gameEntities.getAllEntities()) {
            if (t.getXPos() + t.getTexture().getWidth() < 0) {
                t.changeXPos(this.background.getTexture().getWidth() + t.getTexture().getWidth());
            } else if (t.getXPos() > this.background.getTexture().getWidth()) {
                t.changeXPos(-this.background.getTexture().getWidth() - t.getTexture().getWidth());
            }
        }
    }

    private void checkIfEntitiesAreOnScreenYAxis() {
        List<TexturedGameEntity> texturedGameEntitiesToRemove = new ArrayList<>();
        for (TexturedGameEntity t : this.gameEntities.getAllEntities()) {
            if (t.getYPos() > this.background.getTexture().getHeight()) {
               texturedGameEntitiesToRemove.add(t);
            }
        }
        this.gameEntities.removeEntities(texturedGameEntitiesToRemove);
    }

    private void movePlayer() {
        this.movePlayerXAxis();
        this.movePlayerYAxis();
    }

    private void movePlayerYAxis() {
        this.gameEntities.getPlayerEntity().changeYPos(this.gameEntities.getPlayerEntity().getYSpeed());
    }

    private void movePlayerXAxis() {
        this.gameEntities.getPlayerEntity().changeXPos((int) (this.orientationSensorsService.getRollConvertedIntoPlayerPositionChangeCoeff() * 35));
    }

    private void handleGameEvents() {
        for (GameEvent e : this.gameEventList) {
            e.event();
        }
        gameEventList.clear();
    }

    private void checkColisions() {
        if (this.gameEntities.detectCollisionWithObjects(this.gameEntities.getPlayerEntity(), this.gameEntities.getObjectGameEntities())) {
            this.gameEntities.getPlayerEntity().setYSpeedAfterBoostEvent();
        }
    }

    private void update() throws GameOverException {
        this.handleGameEvents();
        this.checkColisions();
        this.movePlayer();
        this.checkGameOverConditions();
        this.checkIfEntitiesAreOnScreenXAxis();
        this.checkIfEntitiesAreOnScreenYAxis();
        this.gameEntities.getPlayerEntity().changeSpeedAfterGameTick();
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
        canvas.drawBitmap(texturedGameEntity.getTexture(), (int) (texturedGameEntity.getXPos()), (int) (texturedGameEntity.getYPos()), this.paint);
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
        this.setOnTouchListener(touchListener);
        // TODO: 09.01.2021
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        background = new Background(w, h, getResources());
        // TODO: 09.01.2021 Change, testing
        this.gameEntities = new GameEntities(new PlayerEntity(w / 2, h / 2, getResources()));
        // TODO: 11.01.2021 Ultra krzywe, co jeśli 20 platform nie wypełni ekranu xD
        this.gameEntities.addEntity(new PlatformEntity(-100, h, getResources(), 20));
        this.isPlaying = true;

    }

    public void setGameOverListener(GameActivity.GameOverListener gameOverListener) {
        this.gameOverListener = gameOverListener;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (isPlaying) {
                    this.update();
                    this.draw();
                    this.sleep(60);
                }
            } catch (GameOverException e) {
                gameOverListener.onGameOver();
                e.printStackTrace();
                break;
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

    private void setPlayerSpeedBoostEvent() {
        this.gameEntities.getPlayerEntity().setYSpeedAfterBoostEvent();
    }

}
