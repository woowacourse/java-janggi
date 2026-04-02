package domain.board;

import java.util.Arrays;
import java.util.List;
import parser.Command;

public enum Formation {
    LEFT_ELEPHANT(
            Command.FIRST,
            List.of(FormationPiece.ELEPHANT, FormationPiece.HORSE, FormationPiece.ELEPHANT, FormationPiece.HORSE)
    ),
    RIGHT_ELEPHANT(
            Command.SECOND,
            List.of(FormationPiece.HORSE, FormationPiece.ELEPHANT, FormationPiece.HORSE, FormationPiece.ELEPHANT)
    ),
    OUTER_ELEPHANT(
            Command.THIRD,
            List.of(FormationPiece.ELEPHANT, FormationPiece.HORSE, FormationPiece.HORSE, FormationPiece.ELEPHANT)
    ),
    INNER_ELEPHANT(
            Command.FOURTH,
            List.of(FormationPiece.HORSE, FormationPiece.ELEPHANT, FormationPiece.ELEPHANT, FormationPiece.HORSE)
    ),
    ;

    private final Command command;
    private final List<FormationPiece> orders;

    Formation(Command command, List<FormationPiece> orders) {
        this.command = command;
        this.orders = List.copyOf(orders);
    }

    public static Formation from(Command command) {
        return Arrays.stream(values())
                .filter(formation -> formation.command.equals(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 배치가 아닙니다."));
    }

    public List<FormationPiece> getOrders() {
        return orders;
    }
}
