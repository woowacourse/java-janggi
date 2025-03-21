package domain.game;

import domain.JanggiPosition;

public interface GameState {
    GameState start();

    GameState move(JanggiPosition beforePosition, JanggiPosition afterPosition);

    GameState end();
}
