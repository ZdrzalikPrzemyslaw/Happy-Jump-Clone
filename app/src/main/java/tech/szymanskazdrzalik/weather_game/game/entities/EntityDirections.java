package tech.szymanskazdrzalik.weather_game.game.entities;

public enum EntityDirections {
    LEFT(0),
    RIGHT(1);

    private EntityDirections(int direction) {
        this.direction = direction;
    }

    int direction;
}
