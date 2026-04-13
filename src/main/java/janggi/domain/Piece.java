package janggi.domain;

import janggi.domain.movestorage.MoveStorage;
import janggi.exception.move.InvalidMoveException;

import java.util.Objects;

public class Piece {
    private final MoveStorage moveStorage;
    private final Team team;
    private final int score;
    private final String name;

    public Piece(MoveStorage moveStorage, Team team, int score, String name) {
        this.moveStorage = moveStorage;
        this.team = team;
        this.score = score;
        this.name = name;
    }

    public void verifyMove(Position from, Position to, BoardView boardState) {
        if (!moveStorage.canMove(from, to, boardState)) {
            throw new InvalidMoveException();
        }

        Piece pieceTo = boardState.getPieceAt(to);

        if (pieceTo != null && isSameTeam(pieceTo)) {
            throw new InvalidMoveException();
        }
    }

    public boolean isSameTeam(Team team) {
        return this.team == team;
    }

    public boolean isSameTeam(Piece pieceTo) {
        if (pieceTo == null) {
            return false;
        }

        return isSameTeam(pieceTo.getTeam());
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return score == piece.score && Objects.equals(moveStorage, piece.moveStorage) && team == piece.team
                && Objects.equals(name, piece.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moveStorage, team, score, name);
    }
}
