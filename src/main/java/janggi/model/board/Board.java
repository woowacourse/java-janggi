package janggi.model.board;

import janggi.model.Team;
import janggi.model.piece.Piece;
import janggi.model.position.absolute.Position;
import java.util.Map;

public interface Board {

    Board move(Team team, Position from, Position to);

    boolean isGameOver();

    Map<Position, Piece> getBoard();

    Team winner();
}
