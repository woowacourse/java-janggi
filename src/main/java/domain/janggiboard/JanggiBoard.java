package domain.janggiboard;

import domain.piece.JanggiPiece;
import domain.piece.JanggiPieceType;
import domain.piece.JanggiSide;
import domain.piece.route.Route;
import domain.position.JanggiPosition;

import java.util.Collections;
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

        movePieceFromOriginToDestination(movingPiece, origin, destination);
        captureIfNotEmpty(targetPiece);
    }

    public boolean isSameTeam(JanggiPosition position, JanggiSide janggiSide) {
        return getPieceOfPosition(position).isTeamOf(janggiSide);
    }

    private JanggiPiece getPieceOfPosition(final JanggiPosition position) {
        return janggiBoard.get(position);
    }

    public boolean isOppositeKingCaptured(JanggiSide nowTurn) {
        return janggiBoard.keySet().stream()
                .map(this::getPieceOfPosition)
                .noneMatch(piece -> piece.isTypeOf(JanggiPieceType.KING) && piece.isTeamOf(nowTurn.getOppositeSide()));
    }

    public int getRemainingPiecesTotalScore(JanggiSide side) {
        return janggiBoard.keySet().stream()
                .map(this::getPieceOfPosition)
                .filter(piece -> piece.isTeamOf(side))
                .mapToInt(JanggiPiece::getScore)
                .sum();
    }

    public Map<JanggiPosition, JanggiPiece> getBoard() {
        return Collections.unmodifiableMap(janggiBoard);
    }

    private void movePieceFromOriginToDestination(
            JanggiPiece movingPiece,
            JanggiPosition origin,
            JanggiPosition destination
    ) {
        janggiBoard.put(destination, movingPiece);
        janggiBoard.put(origin, new JanggiPiece(JanggiSide.NONE, JanggiPieceType.EMPTY));
    }

    private void captureIfNotEmpty(JanggiPiece targetPiece) {
        if (!targetPiece.isEmpty()) {
            targetPiece.capture();
        }
    }

    private JanggiPiece getFirstHurdlePieceOnRoute(JanggiPiece piece, final JanggiPosition origin, final JanggiPosition destination) {
        JanggiPiece hurdlePiece = new JanggiPiece(JanggiSide.NONE, JanggiPieceType.EMPTY);
        Route route = piece.getRoute(origin, destination);
        List<JanggiPosition> positions = route.getPositionsOnRouteFrom(origin);

        for (JanggiPosition position : positions) {
            if (checkExistPiece(position)) {
                hurdlePiece = getPieceOfPosition(position);
            }
        }
        return hurdlePiece;
    }

    private int getHurdleCountOnRoute(final JanggiPiece piece, final JanggiPosition origin, final JanggiPosition destination) {
        Route path = piece.getRoute(origin, destination);
        List<JanggiPosition> positionsWithDestination = path.getPositionsOnRouteFrom(origin);
        List<JanggiPosition> positionsWithoutDestination = positionsWithDestination.subList(0, positionsWithDestination.size() - 1);
        int count = 0;

        for (JanggiPosition position : positionsWithoutDestination) {
            if (checkExistPiece(position)) {
                count++;
            }
        }
        return count;
    }

    private boolean checkExistPiece(final JanggiPosition newPosition) {
        return !getPieceOfPosition(newPosition).isEmpty();
    }
}
