package janggi.model.turn;

import janggi.model.piece.Piece;
import janggi.model.board.position.Position;
import java.util.Map;

public interface Turn {

    Turn play(Position from, Position to);

    boolean isGameOver();

    Map<Position, Piece> getBoard();

    boolean isChoTurn();
}
