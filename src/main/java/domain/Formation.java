package domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum Formation {
    LEFT_ELEPHANT(FormationCommand.FIRST) {
        @Override
        public void placeElephant(Map<Position, Piece> pieces, Side side) {
            List<Piece> orders = List.of(
                    PieceFactory.createElephant(side),
                    PieceFactory.createHorse(side),
                    PieceFactory.createElephant(side),
                    PieceFactory.createHorse(side)
            );
            place(pieces, side, orders);
        }
    },
    RIGHT_ELEPHANT(FormationCommand.SECOND) {
        @Override
        public void placeElephant(Map<Position, Piece> pieces, Side side) {
            List<Piece> orders = List.of(
                    PieceFactory.createHorse(side),
                    PieceFactory.createElephant(side),
                    PieceFactory.createHorse(side),
                    PieceFactory.createElephant(side)
            );
            place(pieces, side, orders);
        }
    },
    OUTER_ELEPHANT(FormationCommand.THIRD) {
        @Override
        public void placeElephant(Map<Position, Piece> pieces, Side side) {
            List<Piece> orders = List.of(
                    PieceFactory.createElephant(side),
                    PieceFactory.createHorse(side),
                    PieceFactory.createHorse(side),
                    PieceFactory.createElephant(side)
            );
            place(pieces, side, orders);
        }
    },
    INNER_ELEPHANT(FormationCommand.FOURTH) {
        @Override
        public void placeElephant(Map<Position, Piece> pieces, Side side) {
            List<Piece> orders = List.of(
                    PieceFactory.createHorse(side),
                    PieceFactory.createElephant(side),
                    PieceFactory.createElephant(side),
                    PieceFactory.createHorse(side)
            );
            place(pieces, side, orders);
        }
    },
    ;

    private final FormationCommand formationCommand;

    Formation(FormationCommand formationCommand) {
        this.formationCommand = formationCommand;
    }

    public static Formation from(FormationCommand formationCommand) {
        return Arrays.stream(values())
                .filter(formation -> formation.formationCommand.equals(formationCommand))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 배치가 아닙니다."));
    }

    public abstract void placeElephant(Map<Position, Piece> pieces, Side side);

    private static void place(Map<Position, Piece> pieces, Side side, List<Piece> orders) {
        List<Integer> a = side.formationX();
        for (int i = 0; i < orders.size(); i++) {
            pieces.put(Position.of(a.get(i), side.baseY()), orders.get(i));
        }
    }
}
