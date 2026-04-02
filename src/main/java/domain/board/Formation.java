package domain.board;

import domain.piece.PieceType;
import java.util.Arrays;
import java.util.List;
import parser.Command;

public enum Formation {
    LEFT_ELEPHANT(
            Command.FIRST,
            List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE)
    ),
    RIGHT_ELEPHANT(
            Command.SECOND,
            List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT)
    ),
    OUTER_ELEPHANT(
            Command.THIRD,
            List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.HORSE, PieceType.ELEPHANT)
    ),
    INNER_ELEPHANT(
            Command.FOURTH,
            List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.ELEPHANT, PieceType.HORSE)
    ),
    ;

    private final Command command;
    private final List<PieceType> orders;

    Formation(Command command, List<PieceType> orders) {
        this.command = command;
        this.orders = List.copyOf(orders);
    }

    public static Formation from(Command command) {
        return Arrays.stream(values())
                .filter(formation -> formation.command.equals(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 배치가 아닙니다."));
    }

    public List<PieceType> getOrders() {
        return orders;
    }
}
