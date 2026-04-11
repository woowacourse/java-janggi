package janggi.domain.board;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import java.util.Arrays;
import java.util.List;

public enum Formation {
    LEFT_ELEPHANT(FormationCommand.FIRST) {
        @Override
        public List<Piece> getPieceOrders(Side side) {
            return List.of(
                    PieceFactory.createElephant(side),
                    PieceFactory.createHorse(side),
                    PieceFactory.createElephant(side),
                    PieceFactory.createHorse(side)
            );
        }
    },
    RIGHT_ELEPHANT(FormationCommand.SECOND) {
        @Override
        public List<Piece> getPieceOrders(Side side) {
            return List.of(
                    PieceFactory.createHorse(side),
                    PieceFactory.createElephant(side),
                    PieceFactory.createHorse(side),
                    PieceFactory.createElephant(side)
            );
        }
    },
    OUTER_ELEPHANT(FormationCommand.THIRD) {
        @Override
        public List<Piece> getPieceOrders(Side side) {
            return List.of(
                    PieceFactory.createElephant(side),
                    PieceFactory.createHorse(side),
                    PieceFactory.createHorse(side),
                    PieceFactory.createElephant(side)
            );
        }
    },
    INNER_ELEPHANT(FormationCommand.FOURTH) {
        @Override
        public List<Piece> getPieceOrders(Side side) {
            return List.of(
                    PieceFactory.createHorse(side),
                    PieceFactory.createElephant(side),
                    PieceFactory.createElephant(side),
                    PieceFactory.createHorse(side)
            );
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

    public abstract List<Piece> getPieceOrders(Side side);
}
