package domain.janggiboard;

import domain.position.JanggiPosition;
import domain.Pattern;
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
        piece.validateCanMove(hurdlePiece, hurdleCount, targetPiece);

        janggiBoard.put(origin, new JanggiPiece(JanggiSide.NONE, JanggiPieceType.EMPTY));
        targetPiece.capture();
        janggiBoard.put(destination, piece);
    }

    public JanggiPiece getPieceOfPosition(final JanggiPosition position) {
        return janggiBoard.get(position);
    }

    private JanggiPiece getFirstHurdlePieceOnRoute(JanggiPiece piece, final JanggiPosition origin, final JanggiPosition destination) {
        JanggiPiece hurdlePiece = new JanggiPiece(JanggiSide.NONE, JanggiPieceType.EMPTY);
        List<Pattern> patterns = piece.getRoute(origin, destination);
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
        List<Pattern> path = piece.getRoute(origin, destination);
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
        return janggiBoard.keySet().stream()
                .map(this::getPieceOfPosition)
                .noneMatch(piece -> piece.isTypeOf(JanggiPieceType.궁) && piece.isTeam(nowTurn.getOppositeSide()));
    }
}
