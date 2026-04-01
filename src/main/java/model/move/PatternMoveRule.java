package model.move;

import model.board.Board;
import model.board.Country;
import model.policy.DestinationPolicy;
import model.policy.PathPolicy;

import java.nio.file.Path;
import java.util.List;

public abstract class PatternMoveRule extends MoveRule {
    private final PathPolicy pathPolicy;
    private final DestinationPolicy destinationPolicy;

    protected PatternMoveRule(PathPolicy pathPolicy, DestinationPolicy destinationPolicy) {
        this.pathPolicy = pathPolicy;
        this.destinationPolicy = destinationPolicy;
    }

    @Override
    public boolean matches(Move move, Board board, Country country) {
        for (MovePattern pattern : patterns(move, country)) {
            if (pattern.matches(move, board)) {
                return true;
            }
        }
        return false;
    }

    protected PathPolicy pathPolicy() {
        return pathPolicy;
    }

    protected DestinationPolicy destinationPolicy() {
        return destinationPolicy;
    }

    protected abstract List<MovePattern> patterns(Move move, Country country);
}
