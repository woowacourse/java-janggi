package janggi.domain.Turn;

import janggi.domain.position.Position;

public interface GameState {
    GameState move(Position from, Position to);
}
