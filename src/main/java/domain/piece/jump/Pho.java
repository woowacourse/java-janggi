package domain.piece.jump;

import static domain.piece.PieceType.PHO;

import domain.Coordinate;
import domain.board.Board;
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
    public List<Coordinate> findAvailablePaths(Coordinate from, Board board) {
        movements.addMovementIfInGung(from);

        List<Coordinate> availablePositions = new ArrayList<>();

        for (Movement movement : movements.getMovements()) {
            Coordinate next = from.move(movement);
            boolean isJumped = false;

            while (movement.isDiagonal() ? next.isInGungBoundary() : next.isInBoundary()) {
                StepDecision decision = decide(board, next, isJumped);

                if (decision.shouldAdd()) {
                    availablePositions.add(next);
                }
                if (decision.shouldStop()) {
                    break;
                }
                if (decision.shouldJump()) {
                    isJumped = true;
                }

                next = next.move(movement);
            }
        }
        return availablePositions;
    }

    public StepDecision decide(Board board, Coordinate to, boolean isJumped) {
        if (!isJumped) {
            if (board.hasPiece(to)) {
                if (board.findPieceTypeByCoordinate(to) == PHO) {
                    return StepDecision.stop();
                }
                return StepDecision.of(false, false, true);
            }
            return StepDecision.skip();
        }

        if (!board.hasPiece(to)) {
            return StepDecision.addStep();
        }

        if (!board.isMyTeam(country, to) && board.findPieceTypeByCoordinate(to) != PHO) {
            return StepDecision.of(true, true, false);
        }

        return StepDecision.stop();
    }
}
