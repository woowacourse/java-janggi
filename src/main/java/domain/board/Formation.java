package domain.board;

import static domain.piece.PieceType.MA;
import static domain.piece.PieceType.SANG;

import domain.piece.PieceType;
import java.util.List;

public enum Formation {
    MA_SANG_MA_SANG(List.of(MA, SANG, MA, SANG), 1),
    MA_SANG_SANG_MA(List.of(MA, SANG, SANG, MA), 2),
    SANG_MA_SANG_MA(List.of(SANG, MA, SANG, MA), 3),
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
        throw new IllegalArgumentException("1에서 4까지 숫자만 입력해주세요");
    }

    public List<PieceType> getFormation() {
        return formation;
    }
}
