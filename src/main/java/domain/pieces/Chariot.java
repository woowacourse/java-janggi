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
    private static final List<MovingFunction> movements = List.of(
            Piece::north, Piece::south, Piece::west, Piece::east
    );
    public Chariot(Camp camp) {
        super(camp);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardChecker boardChecker) {
        Map<Position, List<Position>> routeOfDestination = new HashMap<>();

        for (MovingFunction movement : movements) {
            routeOfDestination.putAll(move(from, boardChecker, movement));
        }

        return routeOfDestination.containsKey(to);
    }

    private Map<Position, List<Position>> move(Position position, BoardChecker boardChecker,
            MovingFunction movement) {
        Map<Position, List<Position>> movablePositions = new HashMap<>();
        List<Position> collectedMovablePositions = new ArrayList<>();
        Optional<Position> next = movement.move(position);
        while (next.isPresent()) {
            Position currentPosition = next.get();
            collectedMovablePositions.add(currentPosition);
            movablePositions.put(currentPosition, collectedMovablePositions);

            if (boardChecker.isExist(currentPosition)) {
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
