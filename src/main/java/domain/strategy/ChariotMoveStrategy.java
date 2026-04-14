package domain.strategy;

import domain.Position;

public class ChariotMoveStrategy extends OrthogonalMoveStrategy {

    private ChariotMoveStrategy(Position position) {
        super(position);
    }

    public static ChariotMoveStrategy of(Position position) {
        return new ChariotMoveStrategy(position);
    }
}
