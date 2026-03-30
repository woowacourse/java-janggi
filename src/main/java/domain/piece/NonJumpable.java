package domain.piece;

import domain.BoardStatus;
import domain.piece.strategy.MoveStrategy;
import domain.position.Position;
import java.util.List;

public abstract class NonJumpable extends Piece {
    public NonJumpable(MoveStrategy moveStrategy, PieceType pieceType, Team team) {
        super(moveStrategy, pieceType, team);
    }

    @Override
    public void check(BoardStatus boardStatus, Position start, Position destination) {
        List<Position> movablePath = moveStrategy.findMovablePath(start, destination);
        checkPathIsEmpty(boardStatus, movablePath);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private void checkPathIsEmpty(BoardStatus boardStatus, List<Position> movablePaths) {
        for (Position movablePath : movablePaths) {
            int rowValue = movablePath.getRow().getValue();
            int columnValue = movablePath.getColumn().getValue();

            if (isOccupied(boardStatus, rowValue, columnValue)) {
                throw new IllegalArgumentException(PieceErrorMessage.PATH_BLOCKED.getMessage());
            }
        }
    }

    private boolean isOccupied(BoardStatus boardStatus, int rowValue, int columnValue) {
        Piece piece = boardStatus.getBoardStatus()
                .get(Position.of(rowValue, columnValue));
        return piece != null;
    }
}
