package model.piece;

import java.util.List;
import java.util.Objects;
import model.position.Position;

public abstract class Piece {

    private final String type;
    private final Team team;
    private final int score;

    public Piece(Team team, int score, String type) {
        this.team = team;
        this.score = score;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void checkOfTurn(Team turn) {
        if (this.team.equals(turn)) {
            return;
        }
        throw new IllegalArgumentException("본인 팀의 턴이 아닙니다.");
    }

    public abstract List<Position> calculateAllDirection(Position departure, Position arrival);

    public boolean isCannon() {
        return false;
    }

    public boolean isGeneral() {
        return false;
    }

    public abstract String getName();

    public int getScore() {
        return score;
    }

    public boolean isSameTeam(Piece piece) {
        return this.team == piece.getTeam();
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return score == piece.score && Objects.equals(type, piece.type) && team == piece.team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, team, score);
    }
}
