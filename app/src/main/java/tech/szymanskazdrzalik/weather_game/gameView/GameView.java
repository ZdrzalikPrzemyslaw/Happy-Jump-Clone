package tech.szymanskazdrzalik.weather_game.gameView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

import tech.szymanskazdrzalik.weather_game.GameActivity;
import tech.szymanskazdrzalik.weather_game.game.GameEntities;
import tech.szymanskazdrzalik.weather_game.game.entities.PlayerEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.PresentEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.SnowballEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.StartingPlatformEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.CharacterEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.ObjectEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.TexturedGameEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.util.GenerationPatterns;
import tech.szymanskazdrzalik.weather_game.sensors.OrientationSensorsService;

public class GameView extends SurfaceView implements Runnable {
    private static int tick = 0;
    private boolean isPlaying;
    private boolean isPaused;
    private Background background;
    private Paint paint;
    private double score;
    private double moved = 0;
    private GameEvents gameEvents;
    private GameActivity.ScoreListener scoreListener;
    private GameEntities gameEntities;
    private final CollisionEventListener collisionEventListener = new CollisionEventListener() {

        @Override
        public void onHostileCollision(CharacterEntity e) {
            GameView.this.gameEvents.addGameEvent(() -> {
                GameView.this.gameEntities.removeEntity(e);
                if (!GameView.this.gameEntities.getPlayerEntity().isHasDied()) {
                    throw new GameOverException();
                }
            });
        }

        @Override
        public void onHostileCollision() {
            this.onHostileCollision(null);
        }

        @Override
        public void onFriendlyCollision(CharacterEntity e) {
            GameView.this.gameEvents.addGameEvent(() -> {
                GameView.this.gameEntities.removeEntity(e);
            });
        }

        @Override
        public void onFriendlyCollision(PresentEntity e) {
            GameView.this.gameEntities.getPlayerEntity().setYSpeedAfterPresentCollision();
            this.onFriendlyCollision((CharacterEntity) e);
        }


        @Override
        public void onFriendlyCollision() {
            this.onFriendlyCollision(null);
        }
    };
    private OrientationSensorsService orientationSensorsService;
    private Thread thread;
    private GameActivity.GameOverListener gameOverListener;
    private boolean hasLoaded = false;

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
            if (!GameView.this.gameEntities.getPlayerEntity().isHasDied()) {
                throw new GameOverException();
            }
        }
    }

    private void checkIfEntitiesAreOnScreenXAxis() {
        try {
            for (TexturedGameEntity t : this.gameEntities.getAllEntities()) {
                if (t.getXPos() + t.getTexture().getWidth() < 0) {
                    t.changeXPos(this.background.getTexture().getWidth() + t.getTexture().getWidth());
                } else if (t.getXPos() > this.background.getTexture().getWidth()) {
                    t.changeXPos(-this.background.getTexture().getWidth() - t.getTexture().getWidth());
                }
            }
        } catch (GameOverException e) {
            e.printStackTrace();
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
        if (this.isPlaying) {
            this.moveAllEntitiesYAxis();
            this.gameEntities.getPlayerEntity().changeSpeedAfterGameTick();
        }
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
                if (!GameView.this.gameEntities.getPlayerEntity().isHasDied()) {
                    throw new GameOverException();
                }
            }
        }

    }

    private void movePlayerXAxis() throws GameOverException {
        this.gameEntities.getPlayerEntity().changeXPos((int) (this.orientationSensorsService.getRollConvertedIntoPlayerPositionChangeCoeff() * 35));
    }

    private void handleGameEvents() throws GameOverException {
        this.gameEvents.runGameEvents();
    }

    private void checkCollisions() {
        if (this.isPlaying) {
            if (this.gameEntities.detectCollisionWithObjects(
                    this.gameEntities.getPlayerEntity(),
                    this.gameEntities.getObjectGameEntitiesWithYCoordinatesHigherThanParamAndLowerThanParam(
                            (int) (this.gameEntities.getPlayerEntity().getYPos()),
                            this.background.getTexture().getHeight()))) {
                this.gameEvents.addGameEvent(() -> GameView.this.gameEntities.getPlayerEntity().setYSpeedAfterPlatformCollision());
            }
            this.gameEntities.detectCollisionWithCharacters(
                    this.gameEntities.getPlayerEntity(),
                    this.gameEntities.getCharacterEntitiesWithYCoordinatesHigherThanParamAndLowerThanParam(
                            (int) (this.gameEntities.getPlayerEntity().getYPos()),
                            this.background.getTexture().getHeight())
            );
        }
    }

    private void update() throws GameOverException {
        callEveryTick();
        callEvery10Ticks();
        callEvery30Ticks();
        callEvery60Ticks();
        increaseTickCount();
        resetTickCount();
    }

    private void generatePlatforms() {
        int highestPlatformY = this.generatePlatformsCheckHeight();

        if (highestPlatformY < -600) {
            return;
        }
        Pair<Iterable<ObjectEntity>, Iterable<CharacterEntity>> pair = GenerationPatterns.getRandomPattern(this.background.getTexture().getWidth(), highestPlatformY);
        this.gameEntities.addObjectEntities(pair.first);
        this.gameEntities.addCharacterEntities(pair.second);
    }

    private int generatePlatformsCheckHeight() {
        return (int) this.gameEntities.getHighestObject().getYPos();
    }

    private void generateInitialPlatforms() {
        int highestPlatformY = this.generatePlatformsCheckHeight();
        Pair<Iterable<ObjectEntity>, Iterable<CharacterEntity>> pair = GenerationPatterns.getDefaultPlatformPattern(this.background.getTexture().getWidth(), highestPlatformY);
        this.gameEntities.addObjectEntities(pair.first);
        this.gameEntities.addCharacterEntities(pair.second);
    }

    private void increaseTickCount() {
        tick++;
    }

    private void resetTickCount() {
        if (tick % 60 == 0 && tick != 0) {
            tick = 0;
        }
    }

    private void callEvery10Ticks() {
        if (tick % 10 == 0) {
            this.generatePlatforms();
        }
    }

    private void callEvery60Ticks() {
        if (tick % 60 == 0) {
            this.checkIfEntitiesAreOnScreenYAxis();
        }
    }

    private void callEvery30Ticks() throws GameOverException {
        if (tick % 30 == 0) {
            this.checkGameOverConditions();
        }
    }

    private void callEveryTick() throws GameOverException {
        this.handleGameEvents();
        this.checkCollisions();
        this.movePlayer();
        this.moveCharacters();
        this.checkIfEntitiesAreOnScreenXAxis();
    }

    private void moveCharacters() {
        this.moveCharactersXAxis();
    }

    private void moveCharactersXAxis() {
        try {
            for (CharacterEntity e : this.gameEntities.getCharacterEntities()) {
                if (e instanceof SnowballEntity) {
                    ((SnowballEntity) e).changeXPos();
                }
            }
        } catch (GameOverException e) {
            e.printStackTrace();
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
        this.isPlaying = false;
        this.isPaused = false;
        this.paint = new Paint();
        this.orientationSensorsService = new OrientationSensorsService(getContext());
        this.gameEvents = new GameEvents();
        // TODO: 09.01.2021
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        background = new Background(w, h, getResources());
        StartingPlatformEntity startingPlatformEntity = new StartingPlatformEntity(h);
        this.gameEntities = new GameEntities(new PlayerEntity(w / 2, (int) (startingPlatformEntity.getYPos() - PlayerEntity.defaultTextureHeight)));
        this.gameEntities.addEntity(startingPlatformEntity);
        this.gameEntities.setCollisionEventListener(this.collisionEventListener);
        this.generateInitialPlatforms();
        this.hasLoaded = true;
    }

    public void setGameOverListener(GameActivity.GameOverListener gameOverListener) {
        this.gameOverListener = gameOverListener;
    }

    public void startGame() {
        this.isPlaying = true;
    }

    @Override
    public void run() {
        while (true) {
            while (!this.hasLoaded) {
            }
            try {
                if (!this.isPaused()) {
                    this.update();
                }
                this.draw();
                this.sleep(60);
            } catch (GameOverException e) {
                if (!gameEntities.getPlayerEntity().isHasDied()) {
                    gameEntities.getPlayerEntity().setDeadTexture();
                } else {
                    gameOverListener.onGameOver();
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    public void resume() {
        this.orientationSensorsService.registerListeners();
        thread = new Thread(this);
        thread.start();
    }

    public void togglePauseGame() {
        this.isPaused = !this.isPaused;
    }

    public boolean isPaused() {
        return isPaused;
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
        this.gameEntities.getPlayerEntity().setYSpeedAfterPlatformCollision();
    }

}
