package janggi.model.turn;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.position.Position;
import java.util.function.BiConsumer;

public interface Turn {

    Turn play(Position from, Position to);

    boolean isGameOver();

    void withBoard(BiConsumer<Board, Team> consumer);

    void withScore(BiConsumer<Score, Score> consumer);
}
