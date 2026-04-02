package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.Map;

public class Board implements BoardMediator {

    private final Map<Position, Piece> positionPieceMap;

    public Board(final Map<Position, Piece> positionPieceMap) {
        this.positionPieceMap = positionPieceMap;
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

    public Map<Position, Piece> getPositionPieceMapForDTO() {
        return Map.copyOf(positionPieceMap);
    }

    public boolean hasTwoGeneral() {
        long generalCount = positionPieceMap.values().stream()
                .filter(piece -> piece.getPieceType() == PieceType.GENERAL)
                .count();
        return generalCount == 2;
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
