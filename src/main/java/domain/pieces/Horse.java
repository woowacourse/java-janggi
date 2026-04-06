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

public class Horse extends Piece {

    public Horse(Camp camp) {
        super(camp);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardChecker boardChecker) {
        Map<Position, List<Position>> routeOfDestination = new HashMap<>();

        routeOfDestination.putAll(move(from, this::north, this::northEast, boardChecker));
        routeOfDestination.putAll(move(from, this::north, this::northWest, boardChecker));
        routeOfDestination.putAll(move(from, this::south, this::southEast, boardChecker));
        routeOfDestination.putAll(move(from, this::south, this::southWest, boardChecker));
        routeOfDestination.putAll(move(from, this::east, this::northEast, boardChecker));
        routeOfDestination.putAll(move(from, this::east, this::southEast, boardChecker));
        routeOfDestination.putAll(move(from, this::west, this::northWest, boardChecker));
        routeOfDestination.putAll(move(from, this::west, this::southWest, boardChecker));

        return routeOfDestination.containsKey(to);
    }

    private Map<Position, List<Position>> move(Position position, MovingFunction firstMovement,
            MovingFunction secondMovement, BoardChecker boardChecker) {
        Map<Position, List<Position>> movablePositions = new HashMap<>();
        List<Position> collectedMovablePositions = new ArrayList<>();
        Optional<Position> next1 = firstMovement.move(position);
        if (next1.isEmpty() || boardChecker.isExist(next1.get())) {
            return movablePositions;
        }
        Optional<Position> next2 = secondMovement.move(next1.get());
        if (next2.isEmpty() || boardChecker.isExist(next2.get())) {
            return movablePositions;
        }
        collectedMovablePositions.add(next1.get());
        collectedMovablePositions.add(next2.get());

        movablePositions.put(next2.get(), collectedMovablePositions);
        return movablePositions;
    }


    @Override
    public PieceType getPieceType() {
        return PieceType.HORSE;
    }
}
