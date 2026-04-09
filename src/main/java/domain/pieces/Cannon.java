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

    public Cannon(Camp camp) {
        super(camp);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardChecker boardChecker) {
        Map<Position, List<Position>> routeOfDestination = new HashMap<>();
        for (MovingFunction movement : getMovements()) {
            routeOfDestination.putAll(move(from, movement, boardChecker, false));
        }
        if (from.isPalaceDiagonalPosition()) {
            for (MovingFunction movement : getDiagonalMovements()) {
                routeOfDestination.putAll(move(from, movement, boardChecker, true));
            }
        }
        return routeOfDestination.containsKey(to);
    }

    private List<MovingFunction> getDiagonalMovements() {
        return List.of(Piece::northEast, Piece::southEast, Piece::northWest, Piece::southWest);
    }

    private List<MovingFunction> getMovements() {
        return List.of(Piece::north, Piece::south, Piece::west, Piece::east);
    }

    private Map<Position, List<Position>> move(Position position, MovingFunction movement,
            BoardChecker boardChecker, boolean isDiagonal) {
        Map<Position, List<Position>> movablePositions = new HashMap<>();
        Optional<Position> next = movement.move(position);

        while (next.isPresent()) {
            Position currentPosition = next.get();
            if (isOutOfPalaceBound(currentPosition, isDiagonal)) {
                break;
            }
            if (checkCannonJumping(currentPosition, boardChecker)) {
                return collectMovablePositions(currentPosition, movement, boardChecker, isDiagonal);
            }
            next = movement.move(currentPosition);
        }
        return movablePositions;
    }

    private Map<Position, List<Position>> collectMovablePositions(Position position,
            MovingFunction movement, BoardChecker boardChecker, boolean isDiagonal) {
        Map<Position, List<Position>> movablePositions = new HashMap<>();
        List<Position> collectedMovablePositions = new ArrayList<>();
        Optional<Position> next = movement.move(position);
        while (next.isPresent()) {
            Position currentPosition = next.get();
            if (isOutOfPalaceBound(currentPosition, isDiagonal)) {
                return movablePositions;
            }
            collectedMovablePositions.add(currentPosition);
            if (boardChecker.isExist(currentPosition)) {
                addCaptureIfPossible(currentPosition, collectedMovablePositions, movablePositions,
                        boardChecker);
                return movablePositions;
            }
            movablePositions.put(currentPosition, new ArrayList<>(collectedMovablePositions));
            next = movement.move(currentPosition);
        }
        return movablePositions;
    }

    private void addCaptureIfPossible(Position current, List<Position> path,
            Map<Position, List<Position>> movablePositions, BoardChecker boardChecker) {
        if (boardChecker.isNotCannon(current)) {
            movablePositions.put(current, new ArrayList<>(path));
        }
    }

    private boolean isOutOfPalaceBound(Position current, boolean isDiagonal) {
        return isDiagonal && !current.isPalaceDiagonalPosition();
    }

    private boolean checkCannonJumping(Position position, BoardChecker boardChecker) {
        return boardChecker.isExist(position) && boardChecker.isNotCannon(position);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CANNON;
    }

}
