package piece;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import location.Direction;
import location.PathUtility;
import location.Position;
import store.Pieces;

public class General implements Piece {
    private static final Map<Position, List<Position>> PALACE_DIAGONAL = Map.of(
            new Position(5, 2), List.of(new Position(4, 1), new Position(6, 1), new Position(4, 3), new Position(6, 3)),
            new Position(6, 3), List.of(new Position(5, 2)),
            new Position(4, 3), List.of(new Position(5, 2)),
            new Position(6, 1), List.of(new Position(5, 2)),
            new Position(4, 1), List.of(new Position(5, 2)),
            new Position(5, 9), List.of(new Position(4, 8), new Position(6, 8), new Position(4, 10), new Position(6, 10)),
            new Position(6, 10), List.of(new Position(5, 9)),
            new Position(4, 10), List.of(new Position(5, 9)),
            new Position(6, 8), List.of(new Position(5, 9)),
            new Position(4, 8), List.of(new Position(5, 9))
    );

    private final Position currentPosition;

    public General(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public void validateDestination(Position destination) {
        checkInPalace(destination);
        PathUtility.checkOneMovement(currentPosition, destination);

        if(Direction.isDiagonal(currentPosition, destination)) {
            checkPalaceDiagonal(currentPosition, destination);
        }
    }

    @Override
    public void validatePaths(Pieces pieces, Position destination) {

    }

    @Override
    public Piece move(Position destination) {
        return new General(destination);
    }

    @Override
    public boolean isPlacedAt(Position targetPosition) {
        return currentPosition.equals(targetPosition);
    }

    @Override
    public Position getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.GENERAL;
    }

    private static void checkInPalace(Position destination) {
        if (!isPalacePosition(destination)) {
            throw new IllegalArgumentException("[ERROR] 궁성 외 좌표입니다.");
        }
    }

    private static boolean isPalacePosition(Position destination) {
        if (destination.x() < 4 || 6 < destination.x()) {
            return false;
        }
        if (4 <= destination.y() && destination.y() <= 7) {
            return false;
        }
        return true;
    }

    private static void checkPalaceDiagonal(Position from, Position to) {
        List<Position> validDiagonalDestinations = PALACE_DIAGONAL.getOrDefault(from, Collections.emptyList());

        if(validDiagonalDestinations.isEmpty() || !validDiagonalDestinations.contains(to)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 궁성 내 대각선 움직임입니다.");
        }
    }
}
