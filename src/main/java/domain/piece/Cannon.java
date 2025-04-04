package domain.piece;

import domain.board.BoardLocation;
import domain.board.BoardVector;
import domain.board.Palace;
import domain.board.PathDirection;
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
        if (boardVector.isNotAxis() && canNotMoveDiagonal(current, destination, boardVector)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 목표 위치로 이동할 수 없습니다");
        }
    }

    @Override
    protected List<BoardLocation> extractIntermediatePath(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = BoardVector.between(current, destination);
        PathDirection pathDirection = PathDirection.findPathDirection(boardVector);
        return pathDirection.createPaths(current, boardVector);
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

    private boolean canNotMoveDiagonal(BoardLocation current, BoardLocation destination, BoardVector boardVector) {
        return Palace.isNotDiagonalMoveAllowed(current, destination) || boardVector.isNotDiagonal();
    }
}
