package janggi.model.board;

import janggi.model.Team;
import janggi.model.piece.Piece;
import janggi.model.position.absolute.Position;
import java.util.Map;

public interface Board {

    Board move(Team team, Position from, Position to);

    boolean isWinnerDetermined();

    Map<Position, Piece> getBoardInfo();

    Team winner();

    int getMaterialScoreOf(Team team);
}
