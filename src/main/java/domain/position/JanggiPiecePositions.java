package domain.position;

import domain.janggiPiece.JanggiPiece;
import domain.position.generator.JanggiPiecePositionsGenerator;

import java.util.Collections;
import java.util.Map;

public class JanggiPiecePositions {
    private final Map<JanggiPosition, JanggiPiece> chessPieces;

    public JanggiPiecePositions(JanggiPiecePositionsGenerator generator) {
        this.chessPieces = generator.generate();
    }

    public boolean existChessPieceByPosition(final JanggiPosition position) {
        return chessPieces.containsKey(position);
    }

    public JanggiPiece getJanggiPieceByPosition(final JanggiPosition position) {
        validateExistPiece(position);
        return chessPieces.get(position);
    }

    public void move(final JanggiPosition from, final JanggiPosition to) {
        validateExistPiece(from);
        validateEmptyPosition(to);
        JanggiPiece target = getJanggiPieceByPosition(from);
        removeJanggiPieceByPosition(from);
        putJanggiPiece(to, target);
    }

    private void validateExistPiece(final JanggiPosition position) {
        if (!existChessPieceByPosition(position)) {
            throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
        }
    }

    private void validateEmptyPosition(final JanggiPosition position) {
        if (existChessPieceByPosition(position)) {
            throw new IllegalArgumentException("해당 위치에 이미 다른 기물이 존재합니다.");
        }
    }

    public void removeJanggiPieceByPosition(final JanggiPosition position) {
        validateExistPiece(position);
        chessPieces.remove(position);
    }

    private void putJanggiPiece(final JanggiPosition position, final JanggiPiece chessPiece) {
        validateEmptyPosition(position);
        chessPieces.put(position, chessPiece);
    }

    public Map<JanggiPosition, JanggiPiece> getJanggiPieces() {
        return Collections.unmodifiableMap(chessPieces);
    }
}
