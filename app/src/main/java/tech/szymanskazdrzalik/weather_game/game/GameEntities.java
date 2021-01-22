package tech.szymanskazdrzalik.weather_game.game;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.CharacterEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.GameEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.HostileEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.ObjectEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.PlayerEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.PresentEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.TexturedGameEntity;
import tech.szymanskazdrzalik.weather_game.gameView.CollisionEventListener;

// TODO: 09.01.2021 Good name?
public class GameEntities {

    private final List<CharacterEntity> characterGameEntities = new ArrayList<>();
    private final List<ObjectEntity> objectGameEntities = new ArrayList<>();
    private PlayerEntity playerEntity;
    private CollisionEventListener collisionEventListener;

    private ObjectEntity highestObject = null;

    public void setCollisionEventListener(CollisionEventListener collisionEventListener) {
        this.collisionEventListener = collisionEventListener;
    }

    public GameEntities(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public GameEntities(PlayerEntity playerEntity, Iterable<CharacterEntity> characterGameEntities, Iterable<ObjectEntity> objectGameEntities) {
        this(playerEntity);
        this.addCharacterEntities(characterGameEntities);
        this.addObjectEntities(objectGameEntities);
    }

    private static Optional<GameEntity> getFirstElementFromIterable(Iterable<GameEntity> rhsList) {
        if (rhsList != null) {
            for (GameEntity o : rhsList) {
                return Optional.ofNullable(o);
            }
        }
        return Optional.empty();
    }

    public boolean detectCollisionWithObjects(PlayerEntity entityToCheck, @NotNull Iterable<ObjectEntity> entitiesToCollide) {
        for (ObjectEntity o : entitiesToCollide) {
            PlayerEntity playerEntityAfterMove = new PlayerEntity(entityToCheck);
            playerEntityAfterMove.changeYPos(entityToCheck.getYSpeed() + PlayerEntity.ySpeedChange);
            boolean b = !checkCollision(entityToCheck, o);
            boolean b2 = checkCollision(playerEntityAfterMove, o);
            if (b && b2 && entityToCheck.getYSpeed() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean detectCollisionWithCharacters(PlayerEntity entityToCheck, @NotNull Iterable<CharacterEntity> entitiesToCollide) {
        for (CharacterEntity o : entitiesToCollide) {
            PlayerEntity playerEntityAfterMove = new PlayerEntity(entityToCheck);
            playerEntityAfterMove.changeYPos(entityToCheck.getYSpeed());
            if (checkCollision(playerEntityAfterMove, o)) {
                if (o instanceof HostileEntity) {
                    if (this.collisionEventListener != null)
                        collisionEventListener.onHostileCollision(o);
                }
                else if (o instanceof PresentEntity) {
                    if (this.collisionEventListener != null)
                        collisionEventListener.onFriendlyCollision((PresentEntity) o);
                }
            }
        }
        return false;
    }

    private boolean checkCollision(TexturedGameEntity e1, TexturedGameEntity e2) {
        double e1_x_start = e1.getHitboxStartX();
        double e1_y_start = e1.getHitboxStartY();
        double e1_x_end = e1.getHitboxStartX() + e1.getHitboxWidth();
        double e1_y_end = e1.getHitboxStartY() + e1.getHitboxHeight();
        double e2_x_start = e2.getHitboxStartX();
        double e2_y_start = e2.getHitboxStartY();
        double e2_x_end = e2.getHitboxStartX() + e2.getHitboxWidth();
        double e2_y_end = e2.getHitboxStartY() + e2.getHitboxHeight();

        return e1_x_start < e2_x_end &&
                e1_x_end > e2_x_start &&
                e1_y_start < e2_y_end &&
                e1_y_end > e2_y_start;
    }

    public PlayerEntity getPlayerEntity() {
        return playerEntity;
    }

    public void setPlayerEntity(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public void addEntity(CharacterEntity entity) {
        characterGameEntities.add(entity);
    }

    public void addEntity(ObjectEntity entity) {
        if (this.highestObject == null || entity.getYPos() < this.highestObject.getYPos()) {
            this.highestObject = entity;
        }
        objectGameEntities.add(entity);
    }

    public void addCharacterEntities(Iterable<CharacterEntity> entities) {
        for (CharacterEntity e : entities) {
            this.addEntity(e);
        }
    }

    public void addObjectEntities(Iterable<ObjectEntity> entities) {
        for (ObjectEntity o : entities) {
            addEntity(o);
        }
    }

    @Nullable
    public ObjectEntity getHighestObject() {
        return this.highestObject;
    }

    public List<CharacterEntity> getCharacterEntities() {
        return Collections.unmodifiableList(this.characterGameEntities);
    }

    public List<ObjectEntity> getObjectGameEntities() {
        return Collections.unmodifiableList(this.objectGameEntities);
    }

    public List<ObjectEntity> getObjectGameEntitiesWithYCoordinatesHigherThanParam(int yCoordinate) {
        List<ObjectEntity> list = new ArrayList<>();
        for (ObjectEntity o : this.objectGameEntities) {
            if (o.getYPos() > yCoordinate) {
                list.add(o);
            }
        }
        return Collections.unmodifiableList(list);
    }

    public List<TexturedGameEntity> getAllEntities() {
        List<TexturedGameEntity> texturedGameEntities = new ArrayList<>();
        texturedGameEntities.addAll(this.characterGameEntities);
        texturedGameEntities.addAll(this.objectGameEntities);
        texturedGameEntities.add(this.playerEntity);
        return texturedGameEntities;
    }

    public List<TexturedGameEntity> getAllNonPlayerEntities() {
        List<TexturedGameEntity> texturedGameEntities = new ArrayList<>();
        texturedGameEntities.addAll(this.characterGameEntities);
        texturedGameEntities.addAll(this.objectGameEntities);
        return texturedGameEntities;
    }

    /**
     * @return True if successful, false if failure
     */
    public boolean removeEntity(CharacterEntity entity) {
        return this.characterGameEntities.remove(entity);
    }

    /**
     * @return True if successful, false if failure
     */
    public boolean removeEntity(ObjectEntity entity) {
        return this.objectGameEntities.remove(entity);
    }

    public boolean removeEntities(Iterable<TexturedGameEntity> texturedGameEntities) {
        boolean correctlyRemovedAll = true;
        for (TexturedGameEntity t : texturedGameEntities) {
            if (t instanceof ObjectEntity) {
                if (!this.removeEntity((ObjectEntity) t)) {
                    correctlyRemovedAll = false;
                }
            } else if (t instanceof CharacterEntity) {
                if (!this.removeEntity((CharacterEntity) (t))) {
                    correctlyRemovedAll = false;
                }
            }
        }
        return correctlyRemovedAll;
    }
}
