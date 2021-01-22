package tech.szymanskazdrzalik.weather_game.game.entities.util;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tech.szymanskazdrzalik.weather_game.game.entities.PlatformEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.CharacterEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.ObjectEntity;

public class GenerationPatterns {

    private GenerationPatterns() {

    }

    public static Pair<Iterable<ObjectEntity>, Iterable<CharacterEntity>> getDefaultPlatformPattern(int spawnWidth, int startPlatformY) {
        List<ObjectEntity> objectEntityList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            int newPlatformY = startPlatformY - 300 - random.nextInt(1);
            int newPlatformX = random.nextInt(spawnWidth - PlatformEntity.getTextureWidth(1));
            objectEntityList.add(new PlatformEntity(newPlatformX, newPlatformY, 1));
            startPlatformY = newPlatformY;
        }

        return new Pair<>(objectEntityList, new ArrayList<>());
    }

}
