package domain.piece.jump;

import static domain.piece.PieceType.PHO;

import domain.piece.coordiante.Coordinate;
import domain.board.ReadableBoard;
import domain.piece.Country;
import domain.piece.Piece;
import domain.piece.movement.Movement;
import domain.piece.movement.Movements;
import java.util.ArrayList;
import java.util.List;

public class Pho extends Piece {

    private final Movements movements = new Movements(List.of(
            Movement.UP, Movement.DOWN, Movement.RIGHT, Movement.LEFT));

    public Pho(Country country) {
        super(country, PHO);
    }

    @Override
    public List<Coordinate> findAvailablePaths(Coordinate from, ReadableBoard readableBoard) {
        movements.addMovementIfInGung(from);

        List<Coordinate> availablePositions = new ArrayList<>();

        for (Movement movement : movements.getMovements()) {
            Coordinate next = from.move(movement);
            boolean isJumped = false;

            while (movement.isDiagonal() ? next.isInGungBoundary() : next.isInBoundary()) {
                StepDecision step = decide(readableBoard, next, isJumped);

                if (step.shouldAdd()) {
                    availablePositions.add(next);
                }
                if (step.shouldStop()) {
                    break;
                }
                if (step.shouldJump()) {
                    isJumped = true;
                }

                next = next.move(movement);
            }
        }
        return availablePositions;
    }

    public StepDecision decide(ReadableBoard readableBoard, Coordinate to, boolean isJumped) {
        if (!isJumped) {
            if (!readableBoard.hasPiece(to)) {
                return StepDecision.skip();
            }
            if (readableBoard.findPieceTypeByCoordinate(to) == PHO) {
                return StepDecision.stop();
            }
            return StepDecision.jump();
        }

        if (!readableBoard.hasPiece(to)) {
            return StepDecision.step();
        }

        if (!readableBoard.isMyTeam(country, to) && readableBoard.findPieceTypeByCoordinate(to) != PHO) {
            return StepDecision.capture();
        }

        return StepDecision.stop();
    }
}
