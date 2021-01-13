package tech.szymanskazdrzalik.weather_game.gameView;

import java.util.ArrayList;
import java.util.List;

public class GameEvents {
    private final List<GameEvent> gameEventList = new ArrayList<>();

    public void addGameEvent(GameEvent e) {
        gameEventList.add(e);
    }

    public void runGameEvents() throws GameOverException {
        for (GameEvent e : gameEventList) {
            e.event();
        }
        gameEventList.clear();
    }
}
