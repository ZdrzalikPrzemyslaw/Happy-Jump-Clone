package tech.szymanskazdrzalik.weather_game.gameView;

import tech.szymanskazdrzalik.weather_game.game.entities.parent_entities.CharacterEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.PresentEntity;

public interface CollisionEventListener {
    void onHostileCollision(CharacterEntity e);
    void onHostileCollision();
    void onFriendlyCollision(CharacterEntity e);
    void onFriendlyCollision(PresentEntity e);
    void onFriendlyCollision();
}
