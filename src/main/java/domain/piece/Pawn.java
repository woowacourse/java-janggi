package domain.piece;

import domain.board.BoardLocation;
import domain.board.BoardVector;
import domain.board.Palace;
import java.util.Collections;
import java.util.List;

public class Pawn extends Piece {

    private static final int PAWN_STEP = 1;

    public Pawn(Team team) {
        super(team, new Score(2));
    }

    @Override
    protected void validateArrival(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = BoardVector.between(current, destination);

        if (isNotValidMoveDirection(current, destination) || canNotMoveAxis(boardVector) && canNotMoveDiagonal(current, destination, boardVector)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 목표 위치로 이동할 수 없습니다");
        }
    }

    @Override
    protected List<BoardLocation> extractIntermediatePath(BoardLocation current, BoardLocation destination) {
        return Collections.emptyList();
    }

    @Override
    protected void validateMovePath(List<Piece> pathPiece) {
        if (!pathPiece.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 도착지로 이동할 수 없습니다.");
        }
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }

    private boolean isNotValidMoveDirection(BoardLocation current, BoardLocation destination) {
        if (this.team == Team.HAN) {
            return destination.isUp(current);
        }
        return destination.isDown(current);
    }

    private boolean canNotMoveDiagonal(BoardLocation current, BoardLocation destination, BoardVector boardVector) {
        return Palace.isNotDiagonalMoveAllowed(current, destination)
                || boardVector.isNotDiagonal()
                || !boardVector.hasStepDiagonalMove(PAWN_STEP);
    }

    private boolean canNotMoveAxis(BoardVector boardVector) {
        return boardVector.isNotAxis() || !boardVector.hasStepAxisMove(PAWN_STEP);
    }
}
