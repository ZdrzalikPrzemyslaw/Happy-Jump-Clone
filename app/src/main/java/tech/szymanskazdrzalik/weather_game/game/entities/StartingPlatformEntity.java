package tech.szymanskazdrzalik.weather_game.game.entities;

import android.content.res.Resources;

public class StartingPlatformEntity extends PlatformEntity {

    public StartingPlatformEntity(int yPos) {
        super(-100, yPos + 20, 20);
    }
}
