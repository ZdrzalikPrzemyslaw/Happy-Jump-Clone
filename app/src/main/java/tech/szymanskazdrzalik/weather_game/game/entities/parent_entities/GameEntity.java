package tech.szymanskazdrzalik.weather_game.game.entities.parent_entities;

import android.graphics.Point;

import tech.szymanskazdrzalik.weather_game.gameView.GameOverException;

public abstract class GameEntity {
    private double x_pos;
    private double y_pos;
    protected GameEntity(int x, int y) {
        this.setPosition(x, y);
    }

    protected GameEntity(double x, double y) {
        this.setPosition(x, y);
    }

    protected GameEntity (Point point) {
        this.x_pos = point.x;
        this.y_pos = point.y;
    }

    public double getXPos() {
        return this.x_pos;
    }

    public double getYPos() {
        return this.y_pos;
    }

    public void setPosition(int x, int y) {
        this.x_pos = x;
        this.y_pos = y;
    }

    public void setPosition(double x, double y) {
        this.x_pos = x;
        this.y_pos = y;
    }

    public void changeXPos(int delta) throws GameOverException {
        this.x_pos = this.x_pos + delta;
    }

    public void changeXPos(double delta) throws GameOverException {
        this.x_pos = this.x_pos + delta;
    }

    public void changeYPos(int delta) {
        this.y_pos = this.y_pos + delta;
    }

    public void changeYPos(double delta) {
        this.y_pos = this.y_pos + delta;
    }


}
