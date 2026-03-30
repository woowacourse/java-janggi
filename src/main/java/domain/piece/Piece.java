package domain.piece;

import domain.board.Board;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.board.Side;
import domain.rule.MoveRule;
import domain.strategy.MoveStrategy;

import java.util.List;

public abstract class Piece {

    private PieceType type;
    private final Side side;
    private MoveStrategy moveStrategy;
    private List<MoveRule> moveRules;

    public Piece(PieceType type, Side side, MoveStrategy moveStrategy, List<MoveRule> moveRules) {
        this.type = type;
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

    public boolean isNeutral() {
        return side.isNeutral();
    }

    public boolean isSameSide(Side side) {
        return this.side == side;
    }

    public Direction forward() {
        return side.getForward();
    }

    public List<Position> getPossibleMoves(Board board, Position start) {
        List<Position> candidates = moveStrategy.generate(board, start, this);

        return candidates.stream()
                .filter(dest -> moveRules.stream()
                        .allMatch(rule -> rule.isValid(board, start, dest, this)))
                .toList();
    }

    public PieceType getType() {
        return type;
    }
}
