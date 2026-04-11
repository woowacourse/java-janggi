package domain.pieces;

import domain.BoardChecker;
import domain.Camp;
import domain.MovingFunction;
import domain.PieceType;
import domain.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Chariot extends Piece {

    public Chariot(Camp camp) {
        super(camp);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardChecker boardChecker) {
        Map<Position, List<Position>> routeOfDestination = new HashMap<>();
        for (MovingFunction movement : MOVEMENTS) {
            routeOfDestination.putAll(move(from, movement, boardChecker, false));
        }
        if (from.isPalaceDiagonalPosition()) {
            for (MovingFunction movement : DIAGONAL_MOVEMENTS) {
                routeOfDestination.putAll(move(from, movement, boardChecker, true));
            }
        }
        return routeOfDestination.containsKey(to);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CHARIOT;
    }

    private Map<Position, List<Position>> move(Position position,
            MovingFunction movement, BoardChecker boardChecker, Boolean isDiagonal) {
        Map<Position, List<Position>> movablePositions = new HashMap<>();
        List<Position> path = new ArrayList<>();
        Optional<Position> next = movement.move(position);

        while (next.isPresent()) {
            Position currentPosition = next.get();

            if (isOutOfPalaceBound(currentPosition, isDiagonal)) {
                return movablePositions;
            }
            path.add(currentPosition);
            movablePositions.put(currentPosition, new ArrayList<>(path));
            if (boardChecker.isExist(currentPosition)) {
                return movablePositions;
            }
            next = movement.move(currentPosition);
        }
        return movablePositions;
    }

    private boolean isOutOfPalaceBound(Position current, boolean isDiagonal) {
        return isDiagonal && !current.isPalaceDiagonalPosition();
    }
}
