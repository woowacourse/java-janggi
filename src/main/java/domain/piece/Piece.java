package domain.piece;

import domain.Game;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.board.Side;
import domain.rule.MoveRule;
import domain.strategy.MoveStrategy;

import java.util.List;

public abstract class Piece {

    private final Side side;
    private MoveStrategy moveStrategy;
    private List<MoveRule> moveRules;

    public Piece(Side side, MoveStrategy moveStrategy, List<MoveRule> moveRules) {
        this.side = side;
        this.moveStrategy = moveStrategy;
        this.moveRules = moveRules;
    }

    public Piece(Side side) {
        this.side = side;
    }

    public Side getSide() {
        return this.side;
    }

    public boolean isChu() {
        return side.isChu();
    }

    public boolean isHan() {
        return side.isHan();
    }

    public boolean isNeutral() {
        return side.isNeutral();
    }

    public boolean isSameSide(Side side) {
        return this.side == side;
    }

    public Direction forward() {
        return side.getForward();
    }

    public List<Position> getPossibleMoves(Game game, Position start) {
        List<Position> candidates = moveStrategy.generate(game.getBoard(), start, this);

        return candidates.stream()
                .filter(dest -> moveRules.stream()
                        .allMatch(rule -> rule.isValid(game.getBoard(), start, dest, this)))
                .toList();
    }

    public abstract boolean isCannon();
}
