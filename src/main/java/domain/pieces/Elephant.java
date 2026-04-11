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

public class Elephant extends Piece {

    private static final List<List<MovingFunction>> ELEPHANT_MOVEMENTS = List.of(
            List.of(Piece::north, Piece::northEast, Piece::northEast),
            List.of(Piece::north, Piece::northWest, Piece::northWest),
            List.of(Piece::south, Piece::southEast, Piece::southEast),
            List.of(Piece::south, Piece::southWest, Piece::southWest),
            List.of(Piece::east, Piece::northEast, Piece::northEast),
            List.of(Piece::east, Piece::southEast, Piece::southEast),
            List.of(Piece::west, Piece::northWest, Piece::northWest),
            List.of(Piece::west, Piece::southWest, Piece::southWest)
    );

    public Elephant(Camp camp) {
        super(camp);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardChecker boardChecker) {
        Map<Position, List<Position>> routeOfDestination = new HashMap<>();

        for (List<MovingFunction> movement : ELEPHANT_MOVEMENTS) {
            routeOfDestination.putAll(
                    move(from, movement, boardChecker));
        }
        return routeOfDestination.containsKey(to);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.ELEPHANT;
    }

    private Map<Position, List<Position>> move(Position position, List<MovingFunction> movements,
            BoardChecker boardChecker) {
        Map<Position, List<Position>> movablePositions = new HashMap<>();
        List<Position> collectedMovablePositions = new ArrayList<>();
        Position currentPosition = position;
        for (MovingFunction movement : movements) {
            Optional<Position> next = movement.move(currentPosition);
            if (next.isEmpty() || boardChecker.isExist(next.get())) {
                return movablePositions;
            }
            currentPosition = next.get();
            collectedMovablePositions.add(currentPosition);
        }

        movablePositions.put(collectedMovablePositions.getLast(), collectedMovablePositions);
        return movablePositions;
    }
}
