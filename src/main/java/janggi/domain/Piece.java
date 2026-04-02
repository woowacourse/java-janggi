package janggi.domain;

import janggi.domain.movestrategy.MoveStrategy;
import janggi.domain.position.Position;
import janggi.exception.business.InvalidMoveException;

import java.util.Objects;

public class Piece {
    private final MoveStrategy moveStorage;
    private final Team team;
    private final PieceType pieceType;

    public Piece(MoveStrategy moveStorage, Team team, PieceType pieceType) {
        this.moveStorage = moveStorage;
        this.team = team;
        this.pieceType = pieceType;
    }

    public void verifyMove(Position from, Position to, BoardState boardState) {
        if (!moveStorage.canMove(from, to, boardState)) {
            throw new InvalidMoveException();
        }

        Piece pieceTo = boardState.getPieceAt(to);

        if (pieceTo != null && getTeam() == pieceTo.getTeam()) {
            throw new InvalidMoveException();
        }
    }

    public MoveStrategy getMoveStorage() {
        return moveStorage;
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
        return Objects.equals(moveStorage, piece.moveStorage) && team == piece.team && pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(moveStorage, team, pieceType);
    }
}
