package domain.JanggiBoard;

import domain.JanggiPosition;
import domain.pattern.Pattern;
import domain.piece.Empty;
import domain.piece.JanggiPiece;
import domain.piece.JanggiPieceType;
import domain.piece.JanggiSide;
import java.util.List;
import java.util.Map;

public final class JanggiBoard {

    private final Map<JanggiPosition, JanggiPiece> janggiBoard;

    public JanggiBoard(final JanggiBoardInitializer initializer) {
        this.janggiBoard = initializer.initializeJanggiBoard();
    }

    public void movePiece(final JanggiPosition origin, final JanggiPosition destination) {
        JanggiPiece piece = getPieceOfPosition(origin);

        JanggiPiece targetPiece = janggiBoard.get(destination);
        JanggiPiece hurdlePiece = getFirstHurdlePieceOnRoute(piece, origin, destination);
        int hurdleCount = getHurdleCountOnRoute(piece, origin, destination);
        piece.checkPieceCanMove(hurdlePiece, hurdleCount, targetPiece);

        janggiBoard.put(origin, new Empty());
        targetPiece.captureIfNotEmpty();
        janggiBoard.put(destination, piece);
    }

    public JanggiPiece getPieceOfPosition(final JanggiPosition position) {
        return janggiBoard.get(position);
    }

    private JanggiPiece getFirstHurdlePieceOnRoute(JanggiPiece piece, final JanggiPosition origin, final JanggiPosition destination) {
        JanggiPiece hurdlePiece = new Empty();
        List<Pattern> patterns = piece.findPath(origin, destination);
        JanggiPosition newPosition = origin;
        for (Pattern pattern : patterns) {
            newPosition = newPosition.moveOnePosition(pattern);
            if (existPiece(newPosition)) {
                hurdlePiece = getPieceOfPosition(newPosition);
            }
        }
        return hurdlePiece;
    }

    private int getHurdleCountOnRoute(final JanggiPiece piece, final JanggiPosition origin, final JanggiPosition destination) {
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
        return !getPieceOfPosition(newPosition).isEmpty();
    }

    public Map<JanggiPosition, JanggiPiece> getBoard() {
        return janggiBoard;
    }

    public boolean isSameTeam(JanggiPosition position, JanggiSide janggiSide) {
        return getPieceOfPosition(position).isTeam(janggiSide);
    }

    public boolean isOpposite궁Captured(JanggiSide nowTurn) {
        JanggiPiece opposite궁 = janggiBoard.keySet().stream()
                .map(position -> getPieceOfPosition(position))
                .filter(piece -> piece.getType() == JanggiPieceType.궁)
                .filter(piece -> piece.getSide() == nowTurn.getOppositeSide())
                .findFirst()
                .orElseThrow();

        return opposite궁.isCaptured();
    }
}
