package tech.szymanskazdrzalik.weather_game.game.entities.util;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tech.szymanskazdrzalik.weather_game.game.entities.HostileEntityDirections;
import tech.szymanskazdrzalik.weather_game.game.entities.PlatformEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.SnowballEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.CharacterEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.ObjectEntity;

public class GenerationPatterns {

    private GenerationPatterns() {

    }

    public static Pair<Iterable<ObjectEntity>, Iterable<CharacterEntity>> getRandomPattern(int spawnWidth, int startPlatformY) {
        switch (new Random().nextInt(1)) {
            case 0:
                return getDefaultPlatformPattern(spawnWidth, startPlatformY);
            default:
                return new Pair<>(new ArrayList<>(), new ArrayList<>());
        }
    }

    public static Pair<Iterable<ObjectEntity>, Iterable<CharacterEntity>> getDefaultPlatformPattern(int spawnWidth, int startPlatformY) {
        List<ObjectEntity> objectEntityList = new ArrayList<>();
        List<CharacterEntity> characterEntityList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            int newPlatformY = startPlatformY - 300 - random.nextInt(1);
            int newPlatformX = random.nextInt(spawnWidth - PlatformEntity.getTextureWidth(1));
            objectEntityList.add(new PlatformEntity(newPlatformX, newPlatformY, 1));
            startPlatformY = newPlatformY;
            if (i == 10) {
                characterEntityList.add(
                        new SnowballEntity(
                                random.nextInt(spawnWidth - SnowballEntity.DEFAULT_TEXTURE_WIDTH),
                                newPlatformY - SnowballEntity.DEFAULT_TEXTURE_HEIGHT - 10,
                                random.nextBoolean() ? HostileEntityDirections.LEFT : HostileEntityDirections.RIGHT));
            }
        }

        return new Pair<>(objectEntityList, characterEntityList);
    }

}
