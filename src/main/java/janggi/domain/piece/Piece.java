package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Score;
import janggi.domain.Team;
import janggi.domain.rule.MoveRule;
import janggi.domain.rule.Movement;

public abstract class Piece {

    protected final Position position;
    protected final Team team;
    protected final PieceType pieceType;
    protected final MoveRule moveRule;

    public Piece(final Position position, final Team team, final PieceType pieceType, final MoveRule moveRule) {
        this.position = position;
        this.team = team;
        this.pieceType = pieceType;
        this.moveRule = moveRule;
    }

    public abstract Piece move(Board board, Position destination);

    public abstract Score die();

    protected void validateMove(final Board board, final Position destination, Movement movement) {
        moveRule.validateMove(board, this.position, destination, movement);
        validateIsAlly(board, destination, this.team);
    }

    private void validateIsAlly(final Board board, final Position destination, final Team team) {
        if ((board.isExists(destination) && board.isAlly(destination, team))) {
            throw new IllegalArgumentException("목적지에 아군이 존재합니다.");
        }
    }

    public boolean isAlly(final Team team) {
        return this.team == team;
    }

    public Position getPosition() {
        return position;
    }

    public String getName() {
        return pieceType.getName(this);
    }

    public Team getTeam() {
        return team;
    }

    protected boolean isSameType(Piece piece) {
        return this.pieceType == piece.pieceType;
    }
}
