package janggi.factory.masang;

import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;

public enum HorseElephantFactory {
    ELEPHANT_HORSE_ELEPHANT_HORSE(janggi.view.horseElephantPosition.ELEPHANT_HORSE_ELEPHANT_HORSE, ElephantHorseElephantHorse::new),
    HORSE_ELEPHANT_HORSE_ELEPHANT(janggi.view.horseElephantPosition.HORSE_ELEPHANT_HORSE_ELEPHANT, HorseElephantHorseElephant::new),
    HORSE_ELEPHANT_ELEPHANT_HORSE(janggi.view.horseElephantPosition.HORSE_ELEPHANT_ELEPHANT_HORSE, HorseElephantElephantHorse::new),
    ELEPHANT_HORSE_HORSE_ELEPHANT(janggi.view.horseElephantPosition.ELEPHANT_HORSE_HORSE_ELEPHANT, ElePhantHorseHorseElephant::new);

    private final janggi.view.horseElephantPosition horseElephantPosition;
    private final Supplier<HorseElephantPlacement> supplier;

    HorseElephantFactory(janggi.view.horseElephantPosition horseElephantPosition, Supplier<HorseElephantPlacement> supplier) {
        this.horseElephantPosition = horseElephantPosition;
        this.supplier = supplier;
    }

    public static Map<Position, Piece> create(janggi.view.horseElephantPosition horseElephantPosition, Team team) {
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
