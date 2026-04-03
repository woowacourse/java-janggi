package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.team.TeamType;
import java.util.LinkedHashMap;
import java.util.Map;

public class Board implements BoardMediator {

    private final Map<Position, Piece> positionPieceMap;

    public Board(final Map<Position, Piece> positionPieceMap) {
        this.positionPieceMap = new LinkedHashMap<>(positionPieceMap);
    }

    public void movePiece(final Position from, final Position to) {
        positionPieceMap.put(to, positionPieceMap.remove(from));
    }

    @Override
    public boolean hasPieceAt(final Position position) {
        return hasPieceIn(position);
    }

    @Override
    public Piece getPieceInPosition(final Position position) {
        return findPieceByPosition(position);
    }

    @Override
    public boolean hasGeneral(TeamType teamType) {
        return positionPieceMap.values().stream()
                .anyMatch(piece -> piece.getPieceType() == PieceType.GENERAL && piece.getTeamType() == teamType);
    }

    public Map<Position, Piece> getPositionPieceMapForDTO() {
        return Map.copyOf(positionPieceMap);
    }

    private boolean hasPieceIn(final Position position) {
        return positionPieceMap.containsKey(position);
    }

    private Piece findPieceByPosition(final Position position) {
        if (!hasPieceIn(position)) {
            throw new IllegalArgumentException("요청된 위치에는 기물이 존재하지 않습니다.");
        }
        return positionPieceMap.get(position);
    }
}
