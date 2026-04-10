package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Team;
import janggi.domain.vo.position.Position;

import java.util.List;
import java.util.Map;

public interface BoardView {
    Piece findByPosition(Position position);

    boolean isEmptyPosition(Position position);

    PieceType findTypeByPosition(Position position);

    Team findTeamByPosition(Position position);

    Palace palace();

    List<Piece> kingsOnBoard();

    List<Piece> piecesOf(Team team);

    Map<Position, Piece> getBoard();

    boolean canInnerGo(Position from, Position to);
}
