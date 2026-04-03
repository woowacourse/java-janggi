package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;

public interface BoardMediator {
    boolean hasPieceAt(Position position);

    boolean hasGeneral(TeamType teamType);

    Piece getPieceInPosition(Position position);

    boolean isCannon(Position position);
}
