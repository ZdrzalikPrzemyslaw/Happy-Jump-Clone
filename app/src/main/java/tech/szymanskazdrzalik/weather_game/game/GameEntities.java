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

    public boolean detectCollisionWithObjects(PlayerEntity entityToCheck, Iterable<ObjectEntity> entitiesToColide) {
        for (ObjectEntity o : entitiesToColide) {
            // check if x matches
            if (checkXCoordinates(entityToCheck, o) && checkYCoordinates(entityToCheck, o)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkXCoordinates(PlayerEntity e1, TexturedGameEntity e2) {
        double e1_x_start = e1.getXPos();
        double e1_x_end = e1.getXPos() + e1.getTexture().getWidth();
        double e2_x_start = e2.getXPos();
        double e2_x_end = e2.getXPos() + e2.getTexture().getWidth();

        if (e1_x_start < e2_x_start && e1_x_end > e2_x_start) {
            return true;
        }
        if (e1_x_start < e2_x_end && e1_x_end > e2_x_end) {
            return true;
        }
        if (e1_x_start > e2_x_start && e1_x_end < e2_x_end) {
            return true;
        }
        return false;

    }

    // TODO: 11.01.2021 Poprawić, przy wystarczająco dużej prędkości i wystarczająco cieńskiej teksturze obiekt przeleci przez teksturę
    //  Myślę żeby zrobić tak ze jeśli obiekt już znajduje się w teksturze w danej chwili to zwrócić false (że jakby odbić się może tylko jak leci z góry)
    //  I żeby zrobić tak że jeśli po ruchu jakaś część jego tekstury znalazła by się pod początkiem tekstury platformy to true

    private boolean checkYCoordinates(PlayerEntity e1, TexturedGameEntity e2) {
        double e1_y_start = e1.getYPos();
        double e1_y_end = e1.getYPos() + e1.getTexture().getHeight();
        double e2_y_start = e2.getYPos();
        double e2_y_end = e2.getYPos() + e2.getTexture().getHeight();


        if (e1_y_end + e1.getYSpeed() > e2_y_start && e1_y_end + e1.getYSpeed() < e2_y_end) {
            return true;
        }
        return false;


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
