package janggi.model.turn;

import janggi.model.Board;
import janggi.model.Score;
import janggi.model.position.Position;
import java.util.function.BiConsumer;

public interface Turn {

    Turn play(Position from, Position to);

    boolean isGameOver();

    void accept(BiConsumer<Board, String> consumer);

    void acceptScore(BiConsumer<Score, Score> consumer);
}
