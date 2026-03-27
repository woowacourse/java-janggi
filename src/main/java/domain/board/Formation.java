package domain.board;

import static common.exception.ErrorMessage.INVALID_FORMATION_INPUT;
import static domain.piece.PieceType.MA;
import static domain.piece.PieceType.SANG;

import domain.piece.PieceType;
import java.util.List;

public enum Formation {
    SANG_MA_SANG_MA(List.of(SANG, MA, SANG, MA), 1),
    MA_SANG_MA_SANG(List.of(MA, SANG, MA, SANG), 2),
    MA_SANG_SANG_MA(List.of(MA, SANG, SANG, MA), 3),
    SANG_MA_MA_SANG(List.of(SANG, MA, MA, SANG), 4);

    private final List<PieceType> formation;
    private final int inputNumber;

    Formation(List<PieceType> formation, int inputNumber) {
        this.formation = formation;
        this.inputNumber = inputNumber;
    }

    public static Formation from(int inputNumber) {
        for (Formation formation : values()) {
            if (formation.inputNumber == inputNumber) {
                return formation;
            }
        }
        throw new IllegalArgumentException(INVALID_FORMATION_INPUT.formatted(inputNumber));
    }

    public List<PieceType> getFormation() {
        return formation;
    }
}
