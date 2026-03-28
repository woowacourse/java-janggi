package domain.piece;

import domain.BoardStatus;
import domain.PieceExceptionMessage;
import domain.piece.strategy.MoveStrategy;
import domain.position.Column;
import domain.position.Position;
import domain.position.Row;
import java.util.List;

public abstract class NonJumpable extends Piece {
    protected NonJumpable(MoveStrategy moveStrategy, PieceType pieceType, Team team) {
        super(moveStrategy, pieceType, team);
    }

    @Override
    public void check(BoardStatus boardStatus, Position start, Position destination) {
        List<Position> movablePath = moveStrategy.findMovablePath(start, destination);
        checkPathIsEmpty(boardStatus, movablePath);
    }

    private void checkPathIsEmpty(BoardStatus boardStatus, List<Position> movablePaths) {
        for (Position movablePath : movablePaths) {
            Row rowValue = movablePath.getRow();
            Column columnValue = movablePath.getColumn();

            if (isAlreadyExists(boardStatus, rowValue, columnValue)) {
                throw new IllegalArgumentException(PieceExceptionMessage.BLOCKED_BY_PIECE.getMessage());
            }
        }
    }

    private boolean isAlreadyExists(BoardStatus boardStatus, Row rowValue, Column columnValue) {
        Piece piece = boardStatus.getBoardStatus().get(Position.of(rowValue, columnValue));

        return piece != null;
    }
}
