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
        boolean isAxis = boardVector.isAxis();
        boolean isStepAxisMove = boardVector.isStepAxisMove(PAWN_STEP);
        boolean isStepQuadrantMove = boardVector.isStepQuadrantMove(PAWN_STEP);

        if ((this.team == Team.HAN) && !destination.isUp(current)) {
            if (isAxis && isStepAxisMove) {
                return;
            }
            if (Palace.isDiagonalMoveAllowed(current, destination) && boardVector.isQuadrant() && isStepQuadrantMove) {
                return;
            }
        }
        if ((this.team == Team.CHO) && !destination.isDown(current)) {
            if (isAxis && isStepAxisMove) {
                return;
            }
            if (Palace.isDiagonalMoveAllowed(current, destination) && boardVector.isQuadrant() && isStepQuadrantMove) {
                return;
            }
        }

        throw new IllegalArgumentException("[ERROR] 해당 기물은 목표 위치로 이동할 수 없습니다");
    }

    @Override
    protected List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
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
}
