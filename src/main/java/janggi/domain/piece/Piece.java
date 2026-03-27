package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.team.TeamType;
import java.util.List;

public interface Piece {
    PieceType getPieceType();

    TeamType getTeamType();

    boolean belongsToTeam(TeamType teamType);

    List<Position> calculateMovablePositions(Position from, BoardMediator boardMediator);

    boolean canKill(Piece target);
}
