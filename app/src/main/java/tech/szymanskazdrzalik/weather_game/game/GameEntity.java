package tech.szymanskazdrzalik.weather_game.game;

import android.graphics.Point;

public abstract class GameEntity {
    private final Point position;
    protected GameEntity(int x, int y) {
        this.position = new Point();
        this.setPosition(x, y);
    }

    protected GameEntity (Point point) {
        this.position = point;
    }

    public int getXPos() {
        return position.x;
    }

    public int getYPos() {
        return position.y;
    }

    public void setPosition(int x, int y) {
        this.position.set(x, y);
    }

    public void setPosition(Point position) {
        this.position.set(position.x, position.y);
    }

}
