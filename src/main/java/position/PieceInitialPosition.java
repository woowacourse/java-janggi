package position;

import piece.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public enum PieceInitialPosition {

    GENERAL(List.of(new RelativePosition(5, 0)), General::new),
    GUARD(List.of(new RelativePosition(4, 0), new RelativePosition(6, 0)), Guard::new),
    HORSE(List.of(new RelativePosition(3, 0), new RelativePosition(7, 0)), Horse::new),
    ELEPHANT(List.of(new RelativePosition(2, 0), new RelativePosition(8, 0)), Elephant::new),
    CHARIOT(List.of(new RelativePosition(1, 0), new RelativePosition(9, 0)), Chariot::new),
    CANNON(List.of(new RelativePosition(2, 2), new RelativePosition(8, 2)), Cannon::new),
    SOLDIER(List.of(
            new RelativePosition(1, 3),
            new RelativePosition(3, 3),
            new RelativePosition(5, 3),
            new RelativePosition(7, 3),
            new RelativePosition(9, 3)
    ), Soldier::new);

    private final List<RelativePosition> relativePositions;

    private final BiFunction<Position, Country, Piece> constructor;

    PieceInitialPosition(List<RelativePosition> relativePositions, BiFunction<Position, Country, Piece> constructor) {
        this.relativePositions = relativePositions;
        this.constructor = constructor;
    }

    public Map<Position, Piece> getAbsolutePositions(Country country) {
        Map<Position, Piece> map = new HashMap<>();
        relativePositions.stream()
                .map(rp -> new Position(rp.x(), country.getLineFarBy(rp.distanceFromEnd())))
                .forEach(position -> {
                    Piece piece = constructor.apply(position, country);
                    map.put(position, piece);
                });
        return map;
    }
}

record RelativePosition(int x, int distanceFromEnd) {
}
