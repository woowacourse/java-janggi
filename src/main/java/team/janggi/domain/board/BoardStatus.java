package team.janggi.domain.board;

import java.util.Map;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.piece.Piece;

public interface BoardStatus {

    void movePiece(Team team, Position source, Position destination);

    void setPiece(Position position, Piece piece);

    Map<Position, Piece> getBoardStatus();

    boolean isKingDisappeared();

    double getScore(Team team);
}
