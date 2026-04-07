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

public class Cannon extends Piece {

    private static final List<MovingFunction> movements = List.of(
            Piece::north, Piece::south, Piece::west, Piece::east
    );
    public Cannon(Camp camp) {
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
        Optional<Position> next = movement.move(position);
        while (next.isPresent()) {
            Position currentPosition = next.get();
            if (checkCannonJumping(currentPosition, boardChecker)) {
                return collectMovablePositions(currentPosition, boardChecker, movement);
            }
            next = movement.move(currentPosition);
        }
        return movablePositions;
    }

    private Map<Position, List<Position>> collectMovablePositions(Position position,
            BoardChecker boardChecker, MovingFunction movement) {
        Map<Position, List<Position>> movablePositions = new HashMap<>();
        List<Position> collectedMovablePositions = new ArrayList<>();
        Optional<Position> next = movement.move(position);

        while (next.isPresent() && !boardChecker.isExist(next.get())) {
            Position currentPosition = next.get();
            collectedMovablePositions.add(currentPosition);
            movablePositions.put(currentPosition, new ArrayList<>(collectedMovablePositions));
            next = movement.move(currentPosition);
        }
        next.ifPresent(pos -> {
            if (checkCannonJumping(pos, boardChecker)) {
                collectedMovablePositions.add(pos);
                movablePositions.put(pos, new ArrayList<>(collectedMovablePositions));

            }
        });
        return movablePositions;
    }

    private boolean checkCannonJumping(Position position, BoardChecker boardChecker) {
        return boardChecker.isExist(position) && boardChecker.isNotCannon(position);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CANNON;
    }

}
