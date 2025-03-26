package janggi.piece;

import janggi.Board;
import janggi.Score;
import janggi.Team;
import janggi.coordinate.Position;
import janggi.piece.strategy.block.RequiredBlockCountStrategy;
import janggi.piece.strategy.move.MoveStrategy;

import java.util.function.Consumer;

public abstract class Piece {

    protected final Position position;
    protected final Team team;
    protected final MoveStrategy moveStrategy;
    protected final RequiredBlockCountStrategy blockStrategy;

    public Piece(final Position position, final Team team, final MoveStrategy moveStrategy, final RequiredBlockCountStrategy blockStrategy) {
        this.position = position;
        this.team = team;
        this.moveStrategy = moveStrategy;
        this.blockStrategy = blockStrategy;
    }

    public Piece move(Board board, Position destination) {
        validateMove(board, destination);
        return createPiece(destination);
    }

    public Score die(Consumer<Position> remover) {
        remover.accept(this.position);
        return getType().score();
    }

    public abstract PieceType getType();

    public Team getTeam() {
        return team;
    }

    protected abstract Piece createPiece(Position destination);

    protected void validateMove(final Board board, final Position destination) {
        validateIsAlly(board, destination);
        validateMoveShape(board, destination);
        validateIsBlock(board, destination);
        validateSpecialRule(board, destination);
    }

    protected void validateSpecialRule(Board board, Position destination) {
    }

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
        if (board.isAlly(destination, team)) {
            throw new IllegalArgumentException("목적지에 아군이 존재합니다.");
        }
    }
}
