package janggi.model.turn;

import janggi.model.Board;
import janggi.model.position.Position;
import java.util.function.Consumer;

public interface Turn {

    public Turn play(Position from, Position to);
    public boolean isGameOver();
    public void accept(Consumer<Board> consumer);
}
