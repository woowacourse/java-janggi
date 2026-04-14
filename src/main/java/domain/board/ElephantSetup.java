package domain.board;

import domain.movestrategy.MoveStrategyType;
import domain.piece.PieceStatus;
import domain.piece.Position;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static domain.piece.PieceType.ELEPHANT;
import static domain.piece.PieceType.HORSE;

public enum ElephantSetup {

    INNER_ELEPHANT_SETUP("마상상마(馬象象馬)",
            Map.of(
                    Position.of(1, 2), new PieceStatus(HORSE, MoveStrategyType.HORSE),
                    Position.of(1, 3), new PieceStatus(ELEPHANT, MoveStrategyType.ELEPHANT),
                    Position.of(1, 7), new PieceStatus(ELEPHANT, MoveStrategyType.ELEPHANT),
                    Position.of(1, 8), new PieceStatus(HORSE, MoveStrategyType.HORSE)
            )),
    OUTER_ELEPHANT_SETUP("상마마상(象馬馬象)",
            Map.of(
                    Position.of(1, 2), new PieceStatus(ELEPHANT, MoveStrategyType.ELEPHANT),
                    Position.of(1, 3), new PieceStatus(HORSE, MoveStrategyType.HORSE),
                    Position.of(1, 7), new PieceStatus(HORSE, MoveStrategyType.HORSE),
                    Position.of(1, 8), new PieceStatus(ELEPHANT, MoveStrategyType.ELEPHANT)
            )),
    RIGHT_ELEPHANT_SETUP("마상마상(馬象馬象)",
            Map.of(
                    Position.of(1, 2), new PieceStatus(HORSE, MoveStrategyType.HORSE),
                    Position.of(1, 3), new PieceStatus(ELEPHANT, MoveStrategyType.ELEPHANT),
                    Position.of(1, 7), new PieceStatus(HORSE, MoveStrategyType.HORSE),
                    Position.of(1, 8), new PieceStatus(ELEPHANT, MoveStrategyType.ELEPHANT)
            )),
    LEFT_ELEPHANT_SETUP("상마상마(象馬象馬)",
            Map.of(
                    Position.of(1, 2), new PieceStatus(ELEPHANT, MoveStrategyType.ELEPHANT),
                    Position.of(1, 3), new PieceStatus(HORSE, MoveStrategyType.HORSE),
                    Position.of(1, 7), new PieceStatus(ELEPHANT, MoveStrategyType.ELEPHANT),
                    Position.of(1, 8), new PieceStatus(HORSE, MoveStrategyType.HORSE)
            ));

    private static final String OUT_OF_RANGE_INPUT =
            "입력 값이 주어진 범위 " + 1 + " ~ " + ElephantSetup.values().length + "를 벗어났습니다.";

    private final String description;
    private final Map<Position, PieceStatus> piecePositions;

    ElephantSetup(final String description, final Map<Position, PieceStatus> piecePositions) {
        this.description = description;
        this.piecePositions = piecePositions;
    }

    public Map<Position, PieceStatus> getPiecePositions() {
        return piecePositions;
    }

    public static List<String> descriptions() {
        return Arrays.stream(values())
                .map(setup -> setup.description)
                .toList();
    }

    public static ElephantSetup of(final int number) {
        validateRange(number);
        return values()[number - 1];
    }

    private static void validateRange(final int number) {
        if (number < 1 || number > ElephantSetup.values().length) {
            throw new IllegalArgumentException(OUT_OF_RANGE_INPUT);
        }
    }
}
