package domain.pieces;

import domain.Camp;
import domain.BoardChecker;
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
    public boolean canMove(Position from, Position to, BoardChecker existBoard) {
        Map<Position, List<Position>> routeOfDestination = new HashMap<>();

        routeOfDestination.putAll(move(from, existBoard, this::north));
        routeOfDestination.putAll(move(from, existBoard, this::south));
        routeOfDestination.putAll(move(from, existBoard, this::west));
        routeOfDestination.putAll(move(from, existBoard, this::east));

        return routeOfDestination.containsKey(to);
    }

    private Map<Position, List<Position>> move(Position position, BoardChecker existBoard,
            MovingFunction movement) {
        Map<Position, List<Position>> movablePositions = new HashMap<>();
        List<Position> collectedMovablePositions = new ArrayList<>();
        Optional<Position> next = movement.move(position);
        while (next.isPresent()) {
            Position currentPosition = next.get();
            collectedMovablePositions.add(currentPosition);
            movablePositions.put(currentPosition, collectedMovablePositions);

            if (existBoard.isExist(currentPosition)) {
                return movablePositions;
            }
            next = movement.move(currentPosition);
        }
        return movablePositions;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CHARIOT;
    }
}
