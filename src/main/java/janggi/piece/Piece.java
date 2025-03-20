package janggi.piece;

import janggi.Board;
import janggi.Position;
import janggi.Score;
import janggi.Team;
import janggi.piece.strategy.block.BlockStrategy;
import janggi.piece.strategy.move.MoveStrategy;

public abstract class Piece {

    protected final Position position;
    protected final Team team;
    protected final MoveStrategy moveStrategy;
    protected final BlockStrategy blockStrategy;

    public Piece(final Position position, final Team team, final MoveStrategy moveStrategy, final BlockStrategy blockStrategy) {
        this.position = position;
        this.team = team;
        this.moveStrategy = moveStrategy;
        this.blockStrategy = blockStrategy;
    }

    public Piece move(Board board, Position destination) {
        validateMove(board, destination);
        return createPiece(destination);
    }

    public abstract Score die();

    protected abstract Piece createPiece(Position destination);

    protected void validateMove(final Board board, final Position destination) {
        validateIsAlly(board, destination);
        validateMoveShape(board, destination);
        validateIsBlock(board, destination);
        validateSpecialRule(board, destination);
    }

    protected abstract void validateSpecialRule(Board board, Position destination);

    protected abstract PieceType getType();

    public boolean isAlly(final Team team) {
        return this.team == team;
    }

    public Position getPosition() {
        return position;
    }

    private void validateMoveShape(final Board board, final Position destination) {
        moveStrategy.validate(board, position, destination);
    }

    private void validateIsBlock(final Board board, final Position destination) {
        blockStrategy.validate(board, position, destination);
    }

    private void validateIsAlly(final Board board, final Position destination) {
        if ((board.isExists(destination) && board.isAlly(destination, team))) {
            throw new IllegalArgumentException("목적지에 아군이 존재합니다.");
        }
    }
}
