package janggi.factory.horse_elephant;

import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import janggi.view.HorseElephantPosition;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;

public enum HorseElephantFactory {
    ELEPHANT_HORSE_ELEPHANT_HORSE(HorseElephantPosition.ELEPHANT_HORSE_ELEPHANT_HORSE, ElephantHorseElephantHorse::new),
    HORSE_ELEPHANT_HORSE_ELEPHANT(HorseElephantPosition.HORSE_ELEPHANT_HORSE_ELEPHANT, HorseElephantHorseElephant::new),
    HORSE_ELEPHANT_ELEPHANT_HORSE(HorseElephantPosition.HORSE_ELEPHANT_ELEPHANT_HORSE, HorseElephantElephantHorse::new),
    ELEPHANT_HORSE_HORSE_ELEPHANT(HorseElephantPosition.ELEPHANT_HORSE_HORSE_ELEPHANT, ElephantHorseHorseElephant::new);

    private final HorseElephantPosition horseElephantPosition;
    private final Supplier<HorseElephantPlacement> supplier;

    HorseElephantFactory(HorseElephantPosition horseElephantPosition, Supplier<HorseElephantPlacement> supplier) {
        this.horseElephantPosition = horseElephantPosition;
        this.supplier = supplier;
    }

    public static Map<Position, Piece> create(HorseElephantPosition horseElephantPosition, Team team) {
        return Arrays.stream(values())
                .filter(value -> value.horseElephantPosition.equals(horseElephantPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 포지션입니다."))
                .generate(team);
    }

    private Map<Position, Piece> generate(Team team) {
        return supplier.get().generate(team);
    }
}
