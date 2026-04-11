package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.team.TeamType;
import java.util.List;

public interface Piece {
    boolean isSameTeamType(TeamType teamType);

    List<Position> calculateMovablePositions(Position from, BoardMediator boardMediator);

    default boolean isGeneral() {
        return false;
    }

    default boolean isCannon() {
        return false;
    }

    default boolean isPalacePiece() {
        return false;
    }

    TeamType teamType();

    PieceType pieceType();

    int score();

}
