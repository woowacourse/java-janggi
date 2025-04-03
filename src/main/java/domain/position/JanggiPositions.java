package domain.position;

import domain.janggiPiece.JanggiChessPiece;
import domain.score.Score;
import domain.type.JanggiTeam;

import java.util.Collections;
import java.util.Map;

public class JanggiPositions {
    private final Map<JanggiPosition, JanggiChessPiece> pieces;

    public JanggiPositions(Map<JanggiPosition, JanggiChessPiece> pieces) {
        this.pieces = pieces;
    }

    public boolean existChessPieceByPosition(final JanggiPosition position) {
        return pieces.containsKey(position);
    }

    public JanggiChessPiece getJanggiPieceByPosition(final JanggiPosition position) {
        validateExistPiece(position);
        return pieces.get(position);
    }

    public void move(final JanggiPosition from, final JanggiPosition to) {
        validateExistPiece(from);
        validateEmptyPosition(to);
        JanggiChessPiece target = getJanggiPieceByPosition(from);
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
        pieces.remove(position);
    }

    private void putJanggiPiece(final JanggiPosition position, final JanggiChessPiece chessPiece) {
        validateEmptyPosition(position);
        pieces.put(position, chessPiece);
    }

    public void reset() {
        pieces.clear();
    }

    public Score calculateScoreWith(JanggiTeam team) {
        final int total = pieces.values().stream()
                .filter(p -> p.getTeam() == team)
                .map(JanggiChessPiece::getScore)
                .mapToInt(Score::value)
                .sum();
        return new Score(total);
    }

    public Map<JanggiPosition, JanggiChessPiece> getJanggiPieces() {
        return Collections.unmodifiableMap(pieces);
    }
}
