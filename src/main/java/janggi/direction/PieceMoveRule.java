package janggi.direction;

import janggi.piece.board.Board;
import janggi.piece.PalaceMovement;
import janggi.position.Position;
import janggi.direction.obstacle.ObstacleMoveStrategy;
import java.util.Optional;

public class PieceMoveRule {

    protected final PieceType pieceType;
    protected final Movements movements; // TODO : 불변 필드가 가변인 문제 해결
    private final ObstacleMoveStrategy obstacleMoveStrategy;

    public PieceMoveRule(final PieceType pieceType, final ObstacleMoveStrategy givenObstacleMoveStrategy) {
        this.pieceType = pieceType;
        this.movements = pieceType.getMovements();
        this.obstacleMoveStrategy = givenObstacleMoveStrategy;
    }

    // TODO : 상대 위치로 움직이는 기물 vs 그냥 위치로 움직이는 기물 구분하기
    public void validatePath(final Position currentPosition, final Position arrivalPosition, final Board board) {
        if (pieceType.doesLiveInPalace()) {
            currentPosition.validateIsInPalace(arrivalPosition);
        }
        if (pieceType.canNotMoveDiagonal()) {
            final Optional<Movements> optionalMovements = PalaceMovement.getMovements(currentPosition);

            optionalMovements.ifPresent(this::addMovement);
            final Movement movement = movements.findMovements(currentPosition, arrivalPosition, pieceType);
            obstacleMoveStrategy.checkObstacle(currentPosition, arrivalPosition, movement, board);
            optionalMovements.ifPresent(this::deleteMovement);
            return;
        }
        final Movement movement = movements.findMovements(currentPosition, arrivalPosition, pieceType);
        obstacleMoveStrategy.checkObstacle(currentPosition, arrivalPosition, movement, board);
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void addMovement(final Movements givenMovements) {
        movements.add(givenMovements);
    }

    public void deleteMovement(final Movements givenMovements) {
        movements.delete(givenMovements);
    }
}
