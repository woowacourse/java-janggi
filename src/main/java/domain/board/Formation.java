package domain.board;

import domain.Position;
import domain.Side;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import parser.Command;

public enum Formation {
    LEFT_ELEPHANT(Command.FIRST) {
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
    RIGHT_ELEPHANT(Command.SECOND) {
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
    OUTER_ELEPHANT(Command.THIRD) {
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
    INNER_ELEPHANT(Command.FOURTH) {
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

    private final Command command;

    Formation(Command command) {
        this.command = command;
    }

    public static Formation from(Command command) {
        return Arrays.stream(values())
                .filter(formation -> formation.command.equals(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 배치가 아닙니다."));
    }

    public abstract void placeElephant(Map<Position, Piece> pieces, Side side);

    private static void place(Map<Position, Piece> pieces, Side side, List<Piece> orders) {
        List<Integer> formationX = side.formationX();
        for (int i = 0; i < orders.size(); i++) {
            pieces.put(Position.of(formationX.get(i), side.baseY()), orders.get(i));
        }
    }
}
