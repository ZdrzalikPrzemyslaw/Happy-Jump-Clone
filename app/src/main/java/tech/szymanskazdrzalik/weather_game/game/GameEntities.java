package tech.szymanskazdrzalik.weather_game.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: 09.01.2021 Good name?
public class GameEntities{

    public GameEntities(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public GameEntities(PlayerEntity playerEntity, Iterable<TexturedGameEntity> gameEntities) {
        this(playerEntity);
        this.addEntities(gameEntities);
    }

    private PlayerEntity playerEntity;

    public PlayerEntity getPlayerEntity() {
        return playerEntity;
    }

    public void setPlayerEntity(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    private List<TexturedGameEntity> gameEntities = new ArrayList<>();

    public void addEntity(TexturedGameEntity entity) {
        gameEntities.add(entity);
    }

    public void addEntities(Iterable<TexturedGameEntity> entities) {
        for (TexturedGameEntity t : entities) {
            gameEntities.add(t);
        }
    }

    public List<TexturedGameEntity> getEntities() {
        return Collections.unmodifiableList(this.gameEntities);
    }

    /**
     * @return True if successful, false if failure
     */
    public boolean removeEntity(TexturedGameEntity entity) {
        return this.gameEntities.remove(entity);
    }

}
