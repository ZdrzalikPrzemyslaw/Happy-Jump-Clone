package tech.szymanskazdrzalik.weather_game.gameView;

import tech.szymanskazdrzalik.weather_game.game.entities.CharacterEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.PresentEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.TexturedGameEntity;

public interface CollisionEventListener {
    void onHostileCollision(CharacterEntity e);
    void onHostileCollision();
    void onFriendlyCollision(CharacterEntity e);
    void onFriendlyCollision(PresentEntity e);
    void onFriendlyCollision();
}
