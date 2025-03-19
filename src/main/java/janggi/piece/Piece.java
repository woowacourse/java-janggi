package janggi.piece;

import janggi.Board;
import janggi.MoveVector;
import janggi.MovingRule;
import janggi.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    protected final Team team;
    protected final List<MovingRule> movingRules;

    public Piece(final Team team, final List<MovingRule> movingRules) {
        this.team = team;
        this.movingRules = movingRules;
    }

    //    public abstract boolean canMove(final Position start, final Position end, final Board board);
    public boolean canMove(final Position start, final Position end, final Board board) {
        if (cannotFindRule(start, end)) {
            return false;
        }
        if (cannotMoveThrough(start, end, board)) {
            return false;
        }
        return !isPresentSameTeam(end, board);
    }

    public boolean isHan() {
        return team == Team.HAN;
    }

    public boolean isSameTeam(final Team team) {
        return this.team == team;
    }

    protected boolean cannotFindRule(final Position start, final Position end) {
        final List<MoveVector> positionDiffs = new ArrayList<>();
        for (MovingRule movingRule : movingRules) {
            positionDiffs.add(movingRule.sumUnit());
        }
        return !positionDiffs.contains(end.getDiff(start));
    }

    protected boolean isPresentSameTeam(final Position end, final Board board) {
        return board.isPresentSameTeam(team, end);
    }

    protected boolean cannotMoveThrough(final Position start, final Position end, final Board board) {
        final MovingRule matchRule = findMatchRule(start, end);
        Position route = start;
        for (MoveVector vector : matchRule.getVectors()) {
            route = route.add(vector);
            if (board.isPresent(route)) {
                return true;
            }
        }
        return false;
    }

    private MovingRule findMatchRule(final Position start, final Position end) {
        final MoveVector startEndDiff = end.getDiff(start);
        for (MovingRule movingRule : movingRules) {
            final MoveVector vectorSum = movingRule.sumUnit();
            if (vectorSum.equals(startEndDiff)) {
                return movingRule;
            }
        }
        throw new IllegalStateException();
    }
}
