package move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import move.direction.Direction;
import piece.Piece;
import piece.PieceType;
import piece.Pieces;
import piece.Team;
import piece.position.JanggiPosition;

public class FoMoveBehavior extends JanggiMoveBehavior {

    @Override
    public List<JanggiPosition> calculateLegalRoute(JanggiPosition startPosition, JanggiPosition endPosition,
                                                    Team team) {
        JanggiPosition smallerPosition = startPosition.getSmallerPosition(endPosition);
        JanggiPosition biggerPosition = startPosition.getBiggerPosition(endPosition);

        List<JanggiPosition> positions = new ArrayList<>();
        return calculateCanMoveRoute(smallerPosition, biggerPosition, positions);
    }

    private List<JanggiPosition> calculateCanMoveRoute(JanggiPosition minPosition,
                                                       JanggiPosition maxPosition, List<JanggiPosition> positions) {
        if (minPosition.isSameColumn(maxPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, Direction.UP);
        }
        if (minPosition.isSameRow(maxPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, Direction.RIGHT);
        }
        if (isDiagonalGungsungCase(minPosition, maxPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, Direction.UP_RIGHT);
        }
        throw new InvalidMovePosition();
    }

    @Override
    public JanggiPosition moveOnRoute(JanggiPosition destination, Pieces onRoutePieces, Team moveTeam) {
        validatePiecesEmpty(onRoutePieces);
        Piece onRouteFirstPiece = onRoutePieces.getFirstPiece();
        Piece onRoutelastPiece = onRoutePieces.getLastPiece();
        var onRoutePiecesSize = onRoutePieces.size();

        validateFoMove(destination, moveTeam, onRoutePiecesSize, onRouteFirstPiece, onRoutelastPiece);
        return destination;
    }

    private List<JanggiPosition> calculateLegalRoute(JanggiPosition minPosition, JanggiPosition maxPosition,
                                                     List<JanggiPosition> positions,
                                                     Direction direction) {
        while (!minPosition.equals(maxPosition)) {
            minPosition = minPosition.add(direction);
            positions.add(minPosition);
        }
        return Collections.unmodifiableList(positions);
    }

    private void validateFoMove(JanggiPosition destination, Team moveTeam, int onRoutePiecesSize, Piece firstPiece,
                                Piece lastPiece) {
        throwInvalidMoveBehaviorByCondition(() -> isFo(firstPiece) || isFo(lastPiece));
        throwInvalidMoveBehaviorByCondition(() -> !(onRoutePiecesSize == 1 || onRoutePiecesSize == 2));

        if (onRoutePiecesSize == 1) {
            throwInvalidMoveBehaviorByCondition(() -> firstPiece.isSamePosition(destination));
        }
        if (onRoutePiecesSize == 2) {
            throwInvalidMoveBehaviorByCondition(() -> !lastPiece.isSamePosition(destination));
        }
        throwInvalidMoveBehaviorByCondition(
                () -> lastPiece.isSamePosition(destination) && lastPiece.isSameTeam(moveTeam));
    }

    private void validatePiecesEmpty(Pieces pieces) {
        if (pieces.size() == 0) {
            throw new InvalidMovePosition();
        }
    }

    private boolean isFo(Piece piece) {
        return piece.isSameType(getPieceType());
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.FO;
    }
}
