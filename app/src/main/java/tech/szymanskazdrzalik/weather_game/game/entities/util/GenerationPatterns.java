package tech.szymanskazdrzalik.weather_game.game.entities.util;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tech.szymanskazdrzalik.weather_game.game.entities.EntityDirections;
import tech.szymanskazdrzalik.weather_game.game.entities.PlatformEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.PresentEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.SnowballEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.CharacterEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.ObjectEntity;

public class GenerationPatterns {

    private GenerationPatterns() {

    }

    public static Pair<Iterable<ObjectEntity>, Iterable<CharacterEntity>> getRandomPattern(int spawnWidth, int startPlatformY) {
        switch (new Random().nextInt(3)) {
            case 0:
                return getDefaultPlatformPattern(spawnWidth, startPlatformY);
            case 1:
                return getGiftPlatformPattern(spawnWidth, startPlatformY);
            case 2:
                return getMixedPattern(spawnWidth, startPlatformY);
            default:
                return new Pair<>(new ArrayList<>(), new ArrayList<>());
        }
    }

    public static Pair<Iterable<ObjectEntity>, Iterable<CharacterEntity>> getDefaultPlatformPattern(int spawnWidth, int startPlatformY) {
        List<ObjectEntity> objectEntityList = new ArrayList<>();
        List<CharacterEntity> characterEntityList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            startPlatformY = generateOneStandardPatternPiece(spawnWidth, startPlatformY, objectEntityList);
            if (i == 10) {
                characterEntityList.add(
                        new SnowballEntity(
                                random.nextInt(spawnWidth - SnowballEntity.DEFAULT_TEXTURE_WIDTH),
                                startPlatformY - SnowballEntity.DEFAULT_TEXTURE_HEIGHT - 10,
                                random.nextBoolean() ? EntityDirections.LEFT : EntityDirections.RIGHT));
            }
        }

        return new Pair<>(objectEntityList, characterEntityList);
    }

    private static int generateOneStandardPatternPiece(int spawnWidth, int startPlatformY, List<ObjectEntity> objectEntityList) {
        Random random = new Random();
        int newPlatformY = startPlatformY - 300 - random.nextInt(100);
        int newPlatformX = random.nextInt(spawnWidth - PlatformEntity.getTextureWidth(1));
        objectEntityList.add(new PlatformEntity(newPlatformX, newPlatformY, 1));
        return newPlatformY;
    }

    public static Pair<Iterable<ObjectEntity>, Iterable<CharacterEntity>> getGiftPlatformPattern(int spawnWidth, int startPlatformY) {
        List<ObjectEntity> objectEntityList = new ArrayList<>();
        List<CharacterEntity> characterEntityList = new ArrayList<>();
        Random random = new Random();
        int whereSnowball = random.nextInt(12);
        for (int i = 0; i < 4; i++) {
            startPlatformY = generateOnePyramidPatternPiece(spawnWidth, startPlatformY, characterEntityList);
            if (whereSnowball == i) {
                characterEntityList.add(new SnowballEntity(random.nextInt(spawnWidth), startPlatformY - 200));
            }
        }
        int newPlatformY = startPlatformY - 300 - random.nextInt(1);
        int newPlatformX = random.nextInt(spawnWidth - PlatformEntity.getTextureWidth(1));
        objectEntityList.add(new PlatformEntity(newPlatformX, newPlatformY, 0));
        return new Pair<>(objectEntityList, characterEntityList);
    }

    public static Pair<Iterable<ObjectEntity>, Iterable<CharacterEntity>> getMixedPattern(int spawnWidth, int startPlatformY) {
        List<ObjectEntity> objectEntityList = new ArrayList<>();
        List<CharacterEntity> characterEntityList = new ArrayList<>();
        Random random = new Random();
        int whereSnowball = random.nextInt(12);
        for (int i = 0; i < 6; i++) {
            if (random.nextBoolean()) {
                startPlatformY = generateOnePyramidPatternPiece(spawnWidth, startPlatformY, characterEntityList);
            } else {
                for (int j = 0; j < 5; j++) {
                    startPlatformY = generateOneStandardPatternPiece(spawnWidth, startPlatformY, objectEntityList);
                }
            }
            if (whereSnowball == i) {
                characterEntityList.add(new SnowballEntity(random.nextInt(spawnWidth), startPlatformY - 200));
            }
        }
        int newPlatformY = startPlatformY - 300 - random.nextInt(1);
        int newPlatformX = random.nextInt(spawnWidth - PlatformEntity.getTextureWidth(1));
        objectEntityList.add(new PlatformEntity(newPlatformX, newPlatformY, 0));
        return new Pair<>(objectEntityList, characterEntityList);
    }

    private static List<CharacterEntity> giftPyramidPattern(int x, int y) {
        List<CharacterEntity> characterEntities = new ArrayList<>();
        int start_x = x;
        for (int i = 0; i < 3; i++) {
            characterEntities.add(new PresentEntity(x, y));
            x = x + PresentEntity.DEFAULT_TEXTURE_WIDTH + 10;
        }
        y = y - PresentEntity.DEFAULT_TEXTURE_HEIGHT - 10;
        x = start_x + PresentEntity.DEFAULT_TEXTURE_WIDTH / 2;
        for (int i = 0; i < 2; i++) {
            characterEntities.add(new PresentEntity(x, y));
            x = x + PresentEntity.DEFAULT_TEXTURE_WIDTH + 10;
        }
        y = y - PresentEntity.DEFAULT_TEXTURE_HEIGHT - 10;
        x = start_x + PresentEntity.DEFAULT_TEXTURE_WIDTH;
        characterEntities.add(new PresentEntity(x, y));
        return characterEntities;
    }

    private static int generateOnePyramidPatternPiece(int spawnWidth, int startPlatformY, List<CharacterEntity> characterEntityList) {
        Random random = new Random();
        int newPlatformY = startPlatformY - 400 - random.nextInt(200);
        int newPlatformX = random.nextInt(spawnWidth - (3 * PresentEntity.DEFAULT_TEXTURE_WIDTH + 3 * 10));
        List<CharacterEntity> characterEntities = giftPyramidPattern(newPlatformX, newPlatformY);
        characterEntityList.addAll(characterEntities);
        for (
                CharacterEntity e : characterEntities) {
            if (e.getYPos() < newPlatformY) {
                newPlatformY = (int) e.getYPos();
            }
        }

        return newPlatformY;
    }

}
