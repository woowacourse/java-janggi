package domain;

import domain.piece.PieceType;
import java.util.List;

public enum Formation {
    상마마상("1", List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.HORSE, PieceType.ELEPHANT)),
    마상상마("2", List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.ELEPHANT, PieceType.HORSE)),
    상마상마("3", List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE)),
    마상마상("4", List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT));

    private static final String INVALID_OPTION_RANGE = "번호는 1~4 사이의 숫자여야 합니다.";

    private final String option;
    private final List<PieceType> pieceTypes;

    Formation(String option, List<PieceType> pieceTypes) {
        this.option = option;
        this.pieceTypes = pieceTypes;
    }

    public static Formation from(String input) {
        for (Formation formation : Formation.values()) {
            if (formation.option.equals(input)) {
                return formation;
            }
        }
        throw new IllegalArgumentException(INVALID_OPTION_RANGE);
    }

    public List<PieceType> getPieceTypes() {
        return pieceTypes;
    }

    public String getOption() {
        return option;
    }

    public String toDisplayString() {
        return option + ". " + name();
    }
}
