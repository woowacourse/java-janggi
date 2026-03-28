package domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum Formation {
    LEFT_ELEPHANT(Selection.FIRST) {
        @Override
        public void placeElephant(Map<Position, Piece> pieces, Side side) {
            List<Piece> orders = List.of(
                    new Elephant(side),
                    new Horse(side),
                    new Elephant(side),
                    new Horse(side)
            );
            extracted(pieces, side, orders);
        }
    },
    RIGHT_ELEPHANT(Selection.SECOND) {
        @Override
        public void placeElephant(Map<Position, Piece> pieces, Side side) {
            List<Piece> orders = List.of(
                    new Horse(side),
                    new Elephant(side),
                    new Horse(side),
                    new Elephant(side)
            );
            extracted(pieces, side, orders);
        }
    },
    OUTER_ELEPHANT(Selection.THIRD) {
        @Override
        public void placeElephant(Map<Position, Piece> pieces, Side side) {
            List<Piece> orders = List.of(
                    new Elephant(side),
                    new Horse(side),
                    new Horse(side),
                    new Elephant(side)
            );
            extracted(pieces, side, orders);
        }
    },
    INNER_ELEPHANT(Selection.FOURTH) {
        @Override
        public void placeElephant(Map<Position, Piece> pieces, Side side) {
            List<Piece> orders = List.of(
                    new Horse(side),
                    new Elephant(side),
                    new Elephant(side),
                    new Horse(side)
            );
            extracted(pieces, side, orders);
        }
    },
    ;

    private final Selection selection;

    Formation(Selection selection) {
        this.selection = selection;
    }

    public static Formation from(Selection selection ) {
        return Arrays.stream(values())
                .filter(formation -> formation.selection.equals(selection))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 배치가 아닙니다."));
    }

    public abstract void placeElephant(Map<Position, Piece> pieces, Side side);

    private static void extracted(Map<Position, Piece> pieces, Side side, List<Piece> leftElephant) {
        List<Integer> a = side.formationX();
        for (int i = 0; i < leftElephant.size(); i++) {
            pieces.put(new Position(a.get(i), side.baseY()), leftElephant.get(i));
        }
    }
}
