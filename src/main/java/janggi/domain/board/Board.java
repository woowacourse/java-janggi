package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.team.Team;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> positionPieceMap;

    public Board(final Map<Position, Piece> positionPieceMap) {
        this.positionPieceMap = positionPieceMap;
    }

    public Map<Position, Piece> getPositionPieceMap() {
        return positionPieceMap;
    }

    public boolean isBlank(final Position position) {
        return !positionPieceMap.containsKey(position);
    }
}
