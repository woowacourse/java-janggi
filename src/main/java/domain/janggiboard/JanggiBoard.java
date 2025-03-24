package domain.janggiboard;

import domain.position.JanggiPosition;
import domain.MovingPattern;
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
        JanggiPiece movingPiece = getPieceOfPosition(origin);

        JanggiPiece targetPiece = janggiBoard.get(destination);
        JanggiPiece hurdlePiece = getFirstHurdlePieceOnRoute(movingPiece, origin, destination);
        int hurdleCount = getHurdleCountOnRoute(movingPiece, origin, destination);
        movingPiece.validateCanMove(hurdlePiece, hurdleCount, targetPiece);

        moveAndCapture(movingPiece, origin, destination, targetPiece);
    }

    public boolean isSameTeam(JanggiPosition position, JanggiSide janggiSide) {
        return getPieceOfPosition(position).isTeamOf(janggiSide);
    }

    public boolean isOppositeKingCaptured(JanggiSide nowTurn) {
        return janggiBoard.keySet().stream()
                .map(this::getPieceOfPosition)
                .noneMatch(piece -> piece.isTypeOf(JanggiPieceType.KING) && piece.isTeamOf(nowTurn.getOppositeSide()));
    }

    public Map<JanggiPosition, JanggiPiece> getBoard() {
        return janggiBoard;
    }

    private JanggiPiece getPieceOfPosition(final JanggiPosition position) {
        return janggiBoard.get(position);
    }

    private void moveAndCapture(
            JanggiPiece movingPiece,
            JanggiPosition origin,
            JanggiPosition destination,
            JanggiPiece targetPiece
    ) {
        janggiBoard.put(origin, new JanggiPiece(JanggiSide.NONE, JanggiPieceType.EMPTY));
        targetPiece.capture();
        janggiBoard.put(destination, movingPiece);
    }

    private JanggiPiece getFirstHurdlePieceOnRoute(JanggiPiece piece, final JanggiPosition origin, final JanggiPosition destination) {
        JanggiPiece hurdlePiece = new JanggiPiece(JanggiSide.NONE, JanggiPieceType.EMPTY);
        List<MovingPattern> patterns = piece.getRoute(origin, destination);
        JanggiPosition newPosition = origin;
        for (MovingPattern pattern : patterns) {
            newPosition = newPosition.moveOnePosition(pattern);
            if (checkExistPiece(newPosition)) {
                hurdlePiece = getPieceOfPosition(newPosition);
            }
        }
        return hurdlePiece;
    }

    private int getHurdleCountOnRoute(final JanggiPiece piece, final JanggiPosition origin, final JanggiPosition destination) {
        List<MovingPattern> path = piece.getRoute(origin, destination);
        List<MovingPattern> patterns = path.subList(0, path.size() - 1);
        int count = 0;
        JanggiPosition newPosition = origin;
        for (MovingPattern pattern : patterns) {
            newPosition = newPosition.moveOnePosition(pattern);
            if (checkExistPiece(newPosition)) {
                count++;
            }
        }
        return count;
    }

    private boolean checkExistPiece(final JanggiPosition newPosition) {
        return !getPieceOfPosition(newPosition).isEmpty();
    }
}
