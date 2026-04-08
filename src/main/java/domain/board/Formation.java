package domain.board;

import domain.piece.PieceDefinition;
import java.util.List;

public enum Formation {
    LEFT_GWIMA(1, List.of(PieceDefinition.SANG, PieceDefinition.MA, PieceDefinition.SANG, PieceDefinition.MA)),
    RIGHT_GWIMA(2, List.of(PieceDefinition.MA, PieceDefinition.SANG, PieceDefinition.MA, PieceDefinition.SANG)),
    WONANGMA(3, List.of(PieceDefinition.SANG, PieceDefinition.MA, PieceDefinition.MA, PieceDefinition.SANG)),
    YANGGWIMA(4, List.of(PieceDefinition.MA, PieceDefinition.SANG, PieceDefinition.SANG, PieceDefinition.MA));

    private final int inputNumber;
    private final List<PieceDefinition> variablePieces;

    Formation(int inputNumber, List<PieceDefinition> variablePieces) {
        this.inputNumber = inputNumber;
        this.variablePieces = variablePieces;
    }

    public static Formation from(int inputNumber) {
        for (Formation formation : values()) {
            if (formation.inputNumber == inputNumber) {
                return formation;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 상차림 번호입니다: " + inputNumber);
    }

    public List<PieceDefinition> getVariablePieces() {
        return variablePieces;
    }
}
