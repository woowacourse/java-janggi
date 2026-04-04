package janggi.domain.piece;

import janggi.domain.board.BoardState;
import janggi.domain.movestrategy.MoveStrategy;
import janggi.domain.position.Position;
import janggi.exception.business.InvalidMoveException;

import java.util.Objects;

public class Piece {
    private final Team team;
    private final PieceType pieceType;

    public Piece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    public void verifyMove(Position from, Position to, BoardState boardState) {
        if (!pieceType.canMove(from, to, boardState)) {
            throw new InvalidMoveException();
        }

        if (boardState.hasPieceAt(to)) {
            Piece pieceTo = boardState.getPieceAt(to);
            if (getTeam() == pieceTo.getTeam()) {
                throw new InvalidMoveException();
            }
        }
    }

    public boolean isKing() {
        return pieceType.isKing();
    }

    public MoveStrategy getMoveStorage() {
        return pieceType.getMoveStrategy();
    }

    public Team getTeam() {
        return team;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return team == piece.team && pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, pieceType);
    }
}
