package domain.piece;

import domain.board.Axis;
import domain.board.BoardLocation;
import domain.board.BoardVector;
import domain.board.Palace;
import domain.board.Quadrant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Cannon extends Piece {

    private static final int ALLOWABLE_JUMP_COUNT = 1;

    public Cannon(Team team) {
        super(team, new Score(7));
    }

    @Override
    protected void validateArrival(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = BoardVector.between(current, destination);
        if (boardVector.isAxis()) {
            return;
        }
        if (Palace.isDiagonalMoveAllowed(current, destination) && boardVector.isQuadrant()) {
            return;
        }
        throw new IllegalArgumentException("[ERROR] 해당 기물은 목표 위치로 이동할 수 없습니다");
    }

    @Override
    protected List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = BoardVector.between(current, destination);
        if (boardVector.isAxis()) {
            Axis axis = Axis.findAxis(boardVector);
            return axis.createAllPath(current, boardVector);
        }

        if (boardVector.isQuadrant()) {
            Quadrant quadrant = Quadrant.findQuadrant(boardVector);
            return quadrant.createAllPath(current, boardVector);
        }

        return Collections.emptyList();
    }

    @Override
    protected void validateMovePath(List<Piece> pathPiece) {
        if (pathPiece.size() != ALLOWABLE_JUMP_COUNT || isSameType(pathPiece.getFirst())) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 도착지로 이동할 수 없습니다.");
        }
    }

    @Override
    protected void validateKillable(Piece destinationPiece) {
        super.validateKillable(destinationPiece);
        if (isSameType(destinationPiece)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 도착지로 이동할 수 없습니다.");
        }
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
    }

    private boolean isSameType(Piece piece) {
        return Objects.equals(this.getType(), piece.getType());
    }
}
