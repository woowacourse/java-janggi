package domain.board.formation;

import domain.state.Side;
import java.util.function.Function;

public enum InitialFormationType {

    OUTSIDE_MA(OutsideMaFormation::new),
    RIGHT_SANG(RightSangFormation::new),
    INSIDE_MA(InsideMaFormation::new),
    LEFT_SANG(LeftSangFormation::new);

    private final Function<Side, InitialFormation> factory;

    InitialFormationType(Function<Side, InitialFormation> factory) {
        this.factory = factory;
    }

    public InitialFormation create(Side side) {
        return factory.apply(side);
    }
}
