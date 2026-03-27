package janggi.model.turn;

import janggi.model.Board;
import janggi.model.position.Position;
import java.util.function.Consumer;

public interface Turn {

    Turn play(Position from, Position to);

    boolean isGameOver();

    void accept(Consumer<Board> consumer);
}
