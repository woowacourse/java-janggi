package janggi.domain.team;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import java.util.Map;

public interface Team {

    Map<Position, Piece> generatePieces();

    boolean hasPiece(Piece piece);

    TeamType getTeamType();

    String getName();

}
