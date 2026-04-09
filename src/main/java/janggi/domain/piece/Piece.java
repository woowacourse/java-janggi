package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.team.TeamType;
import java.util.List;

public interface Piece {

    boolean isOnSameTeamAs(Piece other);

    boolean isSameTypeAs(Piece other);

    List<Position> calculateMovablePositions(Position from, BoardMediator boardMediator);

    boolean canCatch(Piece target);

    PieceType getPieceType();

    TeamType getTeamType();

    double getScore();
}
