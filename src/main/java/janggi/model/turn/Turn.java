package janggi.model.turn;

import janggi.model.gimul.AbstractGimul;
import janggi.model.position.Position;
import java.util.Map;

public interface Turn {

    Turn play(Position from, Position to);

    boolean isGameOver();

    Map<Position, AbstractGimul> getBoard();

    boolean isChoTurn();
}
