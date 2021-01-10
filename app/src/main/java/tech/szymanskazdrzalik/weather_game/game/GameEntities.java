package tech.szymanskazdrzalik.weather_game.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import tech.szymanskazdrzalik.weather_game.game.entities.CharacterEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.GameEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.ObjectEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.PlayerEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.TexturedGameEntity;

// TODO: 09.01.2021 Good name?
public class GameEntities {

    private final List<CharacterEntity> characterGameEntities = new ArrayList<>();
    private final List<ObjectEntity> objectGameEntities = new ArrayList<>();
    private PlayerEntity playerEntity;

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

    public List<CharacterEntity> getCharacterEntities() {
        return Collections.unmodifiableList(this.characterGameEntities);
    }

    public List<ObjectEntity> getObjectGameEntities() {
        return Collections.unmodifiableList(this.objectGameEntities);
    }

    public List<TexturedGameEntity> getAllEntities() {
        List<TexturedGameEntity> texturedGameEntities = new ArrayList<>();
        texturedGameEntities.addAll(this.characterGameEntities);
        texturedGameEntities.addAll(this.objectGameEntities);
        texturedGameEntities.add(this.playerEntity);
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
