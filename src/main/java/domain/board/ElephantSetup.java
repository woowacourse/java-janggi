package domain.board;

import domain.piece.PieceType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum ElephantSetup {

    INNER_ELEPHANT_SETUP("마상상마(馬象象馬)",
            Map.of(
                    Position.of(1, 2), PieceType.HORSE,
                    Position.of(1, 3), PieceType.ELEPHANT,
                    Position.of(1, 7), PieceType.ELEPHANT,
                    Position.of(1, 8), PieceType.HORSE
            )),
    OUTER_ELEPHANT_SETUP("상마마상(象馬馬象)",
            Map.of(
                    Position.of(1, 2), PieceType.ELEPHANT,
                    Position.of(1, 3), PieceType.HORSE,
                    Position.of(1, 7), PieceType.HORSE,
                    Position.of(1, 8), PieceType.ELEPHANT
            )),
    RIGHT_ELEPHANT_SETUP("마상마상(馬象馬象)",
            Map.of(
                    Position.of(1, 2), PieceType.HORSE,
                    Position.of(1, 3), PieceType.ELEPHANT,
                    Position.of(1, 7), PieceType.HORSE,
                    Position.of(1, 8), PieceType.ELEPHANT
            )),
    LEFT_ELEPHANT_SETUP("상마상마(象馬象馬)",
            Map.of(
                    Position.of(1, 2), PieceType.ELEPHANT,
                    Position.of(1, 3), PieceType.HORSE,
                    Position.of(1, 7), PieceType.ELEPHANT,
                    Position.of(1, 8), PieceType.HORSE
            ));

    private final String description;
    private final Map<Position, PieceType> piecePositions;

    ElephantSetup(final String description, final Map<Position, PieceType> piecePositions) {
        this.description = description;
        this.piecePositions = piecePositions;
    }

    public Map<Position, PieceType> getPiecePositions() {
        return piecePositions;
    }

    public static List<String> descriptions() {
        return Arrays.stream(values())
                .map(setup -> setup.description)
                .toList();
    }

    public static ElephantSetup of(int number) {
        validateRange(number);
        return values()[number - 1];
    }

    private static void validateRange(final int number) {
        if (number < 1 || number > ElephantSetup.values().length) {
            throw new IllegalArgumentException("주어진 숫자 범위를 벗어났습니다.");
        }
    }
}
