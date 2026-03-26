package janggi.turn;

import janggi.Board;
import janggi.position.Position;

public interface Turn {

    public Turn play(Position from, Position to);
    public boolean isGameOver();
}
