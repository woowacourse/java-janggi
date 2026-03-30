package janggi.domain;

import janggi.domain.movestorage.MoveStrategy;
import janggi.exception.InvalidMoveException;

import java.util.Objects;

public class Piece {
    private final MoveStrategy moveStorage;
    private final Team team;
    private final int score;
    private final String name;

    public Piece(MoveStrategy moveStorage, Team team, int score, String name) {
        this.moveStorage = moveStorage;
        this.team = team;
        this.score = score;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return score == piece.score && Objects.equals(moveStorage, piece.moveStorage) && team == piece.team && Objects.equals(name, piece.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moveStorage, team, score, name);
    }
}
