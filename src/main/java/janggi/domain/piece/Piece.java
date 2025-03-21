package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Score;
import janggi.domain.Team;

public abstract class Piece {

    protected final Position position;
    protected final Team team;
    protected final PieceType pieceType;

    public Piece(final Position position, final Team team, final PieceType pieceType) {
        this.position = position;
        this.team = team;
        this.pieceType = pieceType;
    }

    public abstract Piece move(Board board, Position destination);

    public abstract Score die();

    protected void validateMove(final Board board, final Position destination) {
        validateCorrectRule(destination);
        validateIsAlly(board, destination);
        validateIsBlock(board, destination);
    }

    protected abstract void validateCorrectRule(Position destination);

    protected void validateIsAlly(final Board board, final Position destination) {
        if ((board.isExists(destination) && board.isAlly(destination, this.team))) {
            throw new IllegalArgumentException("목적지에 아군이 존재합니다.");
        }
    }

    protected void validateIsBlock(final Board board, final Position destination) {
        if (countPieceInRoute(board, destination) > 0) {
            throw new IllegalArgumentException("이동 경로에 기물이 존재합니다.");
        }
    }

    public boolean isAlly(final Team team) {
        return this.team == team;
    }

    protected int countPieceInRoute(Board board, Position destination) {
        return (int) Route.of(this.position, destination).stream()
                .filter(board::isExists)
                .count();
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
