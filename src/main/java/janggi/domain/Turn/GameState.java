package janggi.domain.Turn;

import janggi.domain.position.Position;
import janggi.domain.space.Space;
import java.util.Map;

public interface GameState {
    GameState move(Position from, Position to);

    boolean isFinished();

    Map<Position, Space> captureBoard();
}
