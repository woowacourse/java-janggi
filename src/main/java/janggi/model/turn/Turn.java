package janggi.model.turn;

import janggi.model.position.Position;

public interface Turn {

    public Turn play(Position from, Position to);
    public boolean isGameOver();
}
