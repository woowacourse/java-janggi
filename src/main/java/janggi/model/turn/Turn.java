package janggi.model.turn;

import janggi.model.ScorePolicy;
import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.position.absolute.Position;

public interface Turn {

    Turn play(Position from, Position to);

    boolean isGameOver();

    Board board();

    boolean isChoTurn();

    Team getWinner(ScorePolicy scorePolicy);
}
