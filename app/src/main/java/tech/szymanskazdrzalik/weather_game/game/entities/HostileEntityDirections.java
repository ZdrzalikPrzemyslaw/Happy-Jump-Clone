package tech.szymanskazdrzalik.weather_game.game.entities;

public enum HostileEntityDirections {
    LEFT(0),
    RIGHT(1);

    private HostileEntityDirections(int direction) {
        this.direction = direction;
    }

    int direction;
}
