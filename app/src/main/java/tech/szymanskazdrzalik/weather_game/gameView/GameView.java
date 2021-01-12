package tech.szymanskazdrzalik.weather_game.gameView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tech.szymanskazdrzalik.weather_game.GameActivity;
import tech.szymanskazdrzalik.weather_game.game.GameEntities;
import tech.szymanskazdrzalik.weather_game.game.entities.ObjectEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.PlatformEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.PlayerEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.StartingPlatformEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.TexturedGameEntity;
import tech.szymanskazdrzalik.weather_game.sensors.OrientationSensorsService;

public class GameView extends SurfaceView implements Runnable {
    private static int tick = 0;
    private boolean isPlaying = false;
    private Background background;
    private Paint paint;
    private double score;
    private double moved = 0;
    private GameEvents gameEvents;
    private GameActivity.ScoreListener scoreListener;
    private GameEntities gameEntities;
    private final OnTouchListener touchListener = (v, event) -> {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.gameEvents.addGameEvent(this::setPlayerSpeedBoostEvent);
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
        this.gameEvents = new GameEvents();
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
        this.scoreListener.onScoreChange();
    }

    public void setScoreListener(GameActivity.ScoreListener scoreListener) {
        this.scoreListener = scoreListener;
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
            if (t.getYPos() > this.background.getTexture().getHeight() + 300) {
                System.out.println(t + " " + t.getYPos());
                texturedGameEntitiesToRemove.add(t);
            }
        }
        this.gameEntities.removeEntities(texturedGameEntitiesToRemove);
    }

    private void movePlayer() throws GameOverException {
        this.movePlayerXAxis();
        this.moveAllEntitiesYAxis();
        this.gameEntities.getPlayerEntity().changeSpeedAfterGameTick();
    }

    private void moveAllEntitiesYAxis() throws GameOverException {
        this.gameEntities.getPlayerEntity().changeYPos(this.gameEntities.getPlayerEntity().getYSpeed());
        StartingPlatformEntity startingPlatformEntity = null;
        for (TexturedGameEntity e : this.gameEntities.getAllEntities()) {
            if (e instanceof StartingPlatformEntity) {
                startingPlatformEntity = (StartingPlatformEntity) e;
            }
        }
        double distanceToMiddle = (this.background.getTexture().getHeight() / 2.4f) - this.gameEntities.getPlayerEntity().getYPos();
        if (distanceToMiddle > 0) {
            this.moved += distanceToMiddle;
            if (moved > score)
                this.setScore(moved);
            for (TexturedGameEntity e : this.gameEntities.getAllEntities()) {
                e.changeYPos(distanceToMiddle);
            }
            return;
        }
        double distanceToBottomQuarter = (this.background.getTexture().getHeight() * 3f / 4f) - this.gameEntities.getPlayerEntity().getYPos();
        if (distanceToBottomQuarter < 0) {
            boolean shouldLose = true;
            if (moved > -distanceToBottomQuarter) {
                for (TexturedGameEntity e : this.gameEntities.getAllEntities()) {
                    if (e.getYPos() > this.gameEntities.getPlayerEntity().getYPos()) {
                        shouldLose = false;
                    }
                    e.changeYPos(distanceToBottomQuarter);
                }
                moved += distanceToBottomQuarter;
            } else if (moved != 0) {
                for (TexturedGameEntity e : this.gameEntities.getAllEntities()) {
                    if (e.getYPos() > this.gameEntities.getPlayerEntity().getYPos()) {
                        shouldLose = false;
                    }
                    e.changeYPos(-moved);
                }
                moved = 0;
            }
            if (shouldLose && startingPlatformEntity == null) {
                throw new GameOverException();
            }
        }

    }

    private void movePlayerXAxis() {
        this.gameEntities.getPlayerEntity().changeXPos((int) (this.orientationSensorsService.getRollConvertedIntoPlayerPositionChangeCoeff() * 35));
    }

    private void handleGameEvents() {
        this.gameEvents.runGameEvents();
    }

    private void checkCollisions() {
        if (this.gameEntities.detectCollisionWithObjects(this.gameEntities.getPlayerEntity(), this.gameEntities.getObjectGameEntitiesWithYCoordinatesHigherThanParam((int) (this.gameEntities.getPlayerEntity().getYPos() + this.gameEntities.getPlayerEntity().getTexture().getHeight()) - 100))) {
            this.gameEvents.addGameEvent(() -> GameView.this.gameEntities.getPlayerEntity().setYSpeedAfterBoostEvent());
        }
    }

    private void update() throws GameOverException {
        callEveryTick();
        callEvery30Ticks();
        callEvery60Ticks();
        increaseTickCount();
        resetTickCount();
    }

    private void generatePlatforms() {
        int highestPlatformY = this.background.getTexture().getHeight();
        for (TexturedGameEntity i : this.gameEntities.getObjectGameEntities()) {
            if (i.getYPos() < highestPlatformY) {
                highestPlatformY = (int) i.getYPos();
            }
        }

        Random random = new Random();

        List<ObjectEntity> objectEntityList = new ArrayList<>();

        while (highestPlatformY > -this.background.getTexture().getHeight()) {
            int newPlatformY = highestPlatformY - 300 - random.nextInt(1);
            int newPlatformX = random.nextInt(this.background.getTexture().getWidth() - 400);
            objectEntityList.add(new PlatformEntity(newPlatformX, newPlatformY, 1));
            highestPlatformY = newPlatformY;
        }

        this.gameEntities.addObjectEntities(objectEntityList);
    }

    private void increaseTickCount() {
        tick++;
    }

    private void resetTickCount() {
        if (tick % 120 == 0 && tick != 0) {
            tick = 0;
        }
    }

    private void callEvery60Ticks() {
        if (tick % 60 == 0 && tick != 0) {
            this.checkIfEntitiesAreOnScreenYAxis();
            this.generatePlatforms();
        }
    }

    private void callEvery30Ticks() throws GameOverException {
        if (tick % 30 == 0 && tick != 0) {
            this.checkGameOverConditions();
        }
    }

    private void callEveryTick() throws GameOverException {
        this.handleGameEvents();
        this.checkCollisions();
        this.movePlayer();
        this.checkIfEntitiesAreOnScreenXAxis();
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
        this.gameEvents = new GameEvents();
        this.setOnTouchListener(touchListener);
        // TODO: 09.01.2021
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        background = new Background(w, h, getResources());
        // TODO: 09.01.2021 Change, testing
        PlatformEntity.init(getResources());
        this.gameEntities = new GameEntities(new PlayerEntity(w / 2, (int) (h - PlayerEntity.defaultTextureHeight - PlatformEntity.getTextureHeight() - 50), getResources()));
        // TODO: 11.01.2021 Ultra krzywe, co jeśli 20 platform nie wypełni ekranu xD
        this.gameEntities.addEntity(new StartingPlatformEntity(h));
        this.generatePlatforms();
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
