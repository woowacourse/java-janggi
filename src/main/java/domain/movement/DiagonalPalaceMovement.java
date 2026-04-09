package domain.movement;

import domain.coordination.Coordination;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DiagonalPalaceMovement extends AbstractPalaceMovement {

    private static final Map<Coordination, List<Coordination>> CHO_PALACE = Map.of(
            Coordination.of(4, 8), List.of(Coordination.of(5, 9), Coordination.of(6, 10)),
            Coordination.of(4, 10), List.of(Coordination.of(5, 9), Coordination.of(6, 8)),
            Coordination.of(5, 9), List.of(Coordination.of(4, 8), Coordination.of(4, 10), Coordination.of(6, 8), Coordination.of(6, 10)),
            Coordination.of(6, 8), List.of(Coordination.of(5, 9), Coordination.of(4, 10)),
            Coordination.of(6, 10), List.of(Coordination.of(5, 9), Coordination.of(4, 8))
    );

    private static final Map<Coordination, List<Coordination>> HAN_PALACE = Map.of(
            Coordination.of(4, 1), List.of(Coordination.of(5, 2), Coordination.of(6, 3)),
            Coordination.of(4, 3), List.of(Coordination.of(5, 2), Coordination.of(6, 1)),
            Coordination.of(5, 2), List.of(Coordination.of(4, 1), Coordination.of(4, 3), Coordination.of(6, 1), Coordination.of(6, 3)),
            Coordination.of(6, 1), List.of(Coordination.of(5, 2), Coordination.of(4, 3)),
            Coordination.of(6, 3), List.of(Coordination.of(5, 2), Coordination.of(4, 1))
    );

    private static final Map<Coordination, List<Coordination>> PALACE = Stream.of(CHO_PALACE, HAN_PALACE)
            .flatMap(m -> m.entrySet().stream())
            .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

    public DiagonalPalaceMovement() {
        super(PALACE);
    }

    @Override
    public boolean isPalace(Coordination from, Coordination to) {
        return ((CHO_PALACE.containsKey(from) && CHO_PALACE.containsKey(to)) ||
                (HAN_PALACE.containsKey(from) && HAN_PALACE.containsKey(to)))
                && from.isDiagonal(to);
    }
}
