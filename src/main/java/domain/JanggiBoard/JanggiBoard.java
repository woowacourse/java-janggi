package domain.JanggiBoard;

import domain.JanggiPosition;
import domain.pattern.Pattern;
import domain.piece.Empty;
import domain.piece.JanggiPiece;
import java.util.List;
import java.util.Map;

public final class JanggiBoard {

    private final Map<JanggiPosition, JanggiPiece> janggiBoard;

    public JanggiBoard(final JanggiBoardInitializer initializer) {
        this.janggiBoard = initializer.initializeJanggiBoard();
    }

    public void movePiece(final JanggiPosition origin, final JanggiPosition destination) {
        destination.validatePositionInBoardBound();
        JanggiPiece piece = getPieceFrom(origin);

        JanggiPiece targetPiece = janggiBoard.get(destination);
        JanggiPiece hurdlePiece = getFirstHurdlePiece(piece, origin, destination);
        int hurdleCount = getHurdleCount(piece, origin, destination);
        piece.checkPieceCanMove(hurdlePiece, hurdleCount, targetPiece);

        janggiBoard.put(origin, new Empty());
        targetPiece.captureIfNotEmpty();
        janggiBoard.put(destination, piece);
    }

    public JanggiPiece getPieceFrom(final JanggiPosition position) {
        return janggiBoard.get(position);
    }

    private JanggiPiece getFirstHurdlePiece(JanggiPiece piece, final JanggiPosition origin, final JanggiPosition destination) {
        JanggiPiece hurdlePiece = new Empty();
        List<Pattern> patterns = piece.findPath(origin, destination);
        JanggiPosition newPosition = origin;
        for (Pattern pattern : patterns) {
            newPosition = newPosition.moveOnePosition(pattern);
            if (existPiece(newPosition)) {
                hurdlePiece = getPieceFrom(newPosition);
            }
        }
        return hurdlePiece;
    }

    private int getHurdleCount(final JanggiPiece piece, final JanggiPosition origin, final JanggiPosition destination) {
        List<Pattern> path = piece.findPath(origin, destination);
        List<Pattern> patterns = path.subList(0, path.size() - 1);
        int count = 0;
        JanggiPosition newPosition = origin;
        for (Pattern pattern : patterns) {
            newPosition = newPosition.moveOnePosition(pattern);
            if (existPiece(newPosition)) {
                count++;
            }
        }
        return count;
    }

    private boolean existPiece(final JanggiPosition newPosition) {
        return !getPieceFrom(newPosition).isEmpty();
    }
}
