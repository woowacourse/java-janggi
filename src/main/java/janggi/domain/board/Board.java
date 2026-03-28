package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class Board {

    private final Map<Position, Piece> positionPieceMap;

    public Board(final Map<Position, Piece> positionPieceMap) {
        this.positionPieceMap = new LinkedHashMap<>(positionPieceMap);
    }

    public Map<Position, Piece> getPositionPieceMap() {
        return positionPieceMap;
    }

    public boolean isBlank(final Position position) {
        return !positionPieceMap.containsKey(position);
    }

    public Piece findPieceByPosition(final Position position) {
        if (isBlank(position)) {
            throw new IllegalStateException("요청된 위치에는 기물이 존재하지 않습니다.");
        }
        return positionPieceMap.get(position);
    }

    public Optional<Piece> movePiece(final Position from, final Position to) {
        final boolean existTarget = !isBlank(to);
        final Piece requestedPiece = positionPieceMap.remove(from);
        final Piece targetPiece = positionPieceMap.put(to, requestedPiece);

        if (existTarget) {
            assert targetPiece != null;
            return Optional.of(targetPiece);
        }
        return Optional.empty();
    }

    public boolean isGameOver() {
        final long generalCount = positionPieceMap.values()
            .stream()
            .filter(piece -> piece.getPieceType() == PieceType.GENERAL)
            .count();

        return generalCount != 2;
    }
}
