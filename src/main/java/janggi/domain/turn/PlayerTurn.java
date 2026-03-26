package janggi.domain.turn;

import janggi.domain.Position;

public interface PlayerTurn {
    PlayerTurn move(Position start, Position end);
}