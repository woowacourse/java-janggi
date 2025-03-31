package janggi.factory.horse_elephant;

import janggi.domain.move.Position;
import java.util.Set;

public class HorseElephantElephantHorse extends HorseElephantPlacement {

    @Override
    protected Set<Position> getHorsePositionByCho() {
        return Set.of(Position.of(10, 2), Position.of(10, 8));
    }

    @Override
    protected Set<Position> getElephantPositionByCho() {
        return Set.of(Position.of(10, 3), Position.of(10, 7));
    }
}
