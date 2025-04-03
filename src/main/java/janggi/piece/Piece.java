package janggi.piece;

import janggi.board.Board;
import janggi.coordinate.Position;
import janggi.piece.rule.movement.MovementRule;
import janggi.player.Score;
import janggi.player.Team;

public abstract class Piece {

    protected final Position position;
    protected final Team team;
    protected final MovementRule movementRule;

    protected Piece(final Position position, final Team team, final MovementRule movementRule) {
        this.position = position;
        this.team = team;
        this.movementRule = movementRule;
    }

    public static Piece of(final int row, final int column, final String pieceType, final String team) {
        return PieceFactory.of(row, column, PieceType.from(pieceType), Team.from(team));
    }

    public Piece move(final Board board, final Position destination) {
        validateMove(board, destination);
        return createPiece(destination);
    }

    public Score getScore() {
        return getType().score();
    }

    public abstract PieceType getType();

    public Team getTeam() {
        return team;
    }

    protected abstract Piece createPiece(Position destination);

    protected void validateMove(final Board board, final Position destination) {
        validateNoAllyAtDestination(board, destination);
        validateMovementRule(board, destination);
        validateSpecialRule(board, destination);
    }

    protected void validateSpecialRule(final Board board, final Position destination) {
    }

    public boolean isAlly(final Team team) {
        return this.team == team;
    }

    public Position getPosition() {
        return position;
    }

    private void validateMovementRule(final Board board, final Position destination) {
        movementRule.validate(board, position, destination);
    }

    private void validateNoAllyAtDestination(final Board board, final Position destination) {
        if (board.isAllyAt(destination, team)) {
            throw new IllegalArgumentException("목적지에 아군이 존재합니다.");
        }
    }
}
