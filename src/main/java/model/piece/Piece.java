package model.piece;

import model.coordinate.Position;
import model.game.Team;
import model.piece.strategy.ReachStrategy;

import java.util.List;

public abstract class Piece {

    private final Team team;
    private final PieceType type;

    protected Piece(Team team, PieceType type) {
        this.team = team;
        this.type = type;
    }

    public abstract List<Position> extractPath(Position currentExclusive, Position nextExclusive);

    public boolean isSameTeam(Piece other) {
        return !isEnemy(other.team);
    }

    public boolean isEnemy(Team team) {
        return this.team != team;
    }

    public boolean canMove(Position current, Position next) {
        int rowDiff = next.calculateRowDiff(current);
        int colDiff = next.calculateColDiff(current);
        return determineReachStrategy(current, next).isReachable(rowDiff, colDiff);
    }

    protected abstract ReachStrategy determineReachStrategy(Position current, Position next);

    public boolean isCho() {
        return getTeam() == Team.CHO;
    }

    public Team getTeam() {
        return team;
    }

    public boolean isCannon() {
        return getType() == PieceType.CANNON;
    }

    public PieceType getType() {
        return type;
    }

    public double getScore() {
        return type.getScore();
    }

    public boolean isGeneral() {
        return type == PieceType.GENERAL;
    }
}
