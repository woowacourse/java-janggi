package domain.pieces;

import domain.Camp;
import domain.ExistBoard;
import domain.MovingFunction;
import domain.PieceType;
import domain.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Cannon extends Piece {

    public Cannon(Camp camp) {
        super(camp);
    }

    @Override
    public boolean canMove(Position from, Position to, ExistBoard existBoard) {
        Map<Position, List<Position>> routeOfDestination = new HashMap<>();

        routeOfDestination.putAll(move(from, existBoard, this::north));
        routeOfDestination.putAll(move(from, existBoard, this::south));
        routeOfDestination.putAll(move(from, existBoard, this::west));
        routeOfDestination.putAll(move(from, existBoard, this::east));

        return routeOfDestination.containsKey(to);
    }

    private Map<Position, List<Position>> move(Position position, ExistBoard existBoard,
            MovingFunction movement) {
        Map<Position, List<Position>> movablePositions = new HashMap<>();
        Optional<Position> next = movement.move(position);
        while (next.isPresent()) {
            Position currentPosition = next.get();
            if (checkCannonJumping(currentPosition, existBoard)) {
                return collectMovablePositions(currentPosition, existBoard, movement);
            }
            next = movement.move(currentPosition);
        }
        return movablePositions;
    }

    private Map<Position, List<Position>> collectMovablePositions(Position position,
            ExistBoard existBoard, MovingFunction movement) {
        Map<Position, List<Position>> movablePositions = new HashMap<>();
        List<Position> collectedMovablePositions = new ArrayList<>();
        Optional<Position> next = movement.move(position);

        while (next.isPresent() && !existBoard.isExist(next.get())) {
            Position currentPosition = next.get();
            collectedMovablePositions.add(currentPosition);
            movablePositions.put(currentPosition, new ArrayList<>(collectedMovablePositions));
            next = movement.move(currentPosition);
        }
        next.ifPresent(pos -> {
            if (checkCannonJumping(pos, existBoard)) {
                collectedMovablePositions.add(pos);
                movablePositions.put(pos, new ArrayList<>(collectedMovablePositions));

            }
        });
        return movablePositions;
    }

    private boolean checkCannonJumping(Position position, ExistBoard existBoard) {
        return existBoard.isExist(position) && existBoard.isNotCannon(position);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CANNON;
    }

}
