package domain.board;

import static domain.piece.PieceType.MA;
import static domain.piece.PieceType.SANG;

import domain.piece.PieceType;
import java.util.List;

public enum Formation {
    MA_SANG_MA_SANG(List.of(MA, SANG, MA, SANG)),
    MA_SANG_SANG_MA(List.of(MA, SANG, SANG, MA)),
    SANG_MA_SANG_MA(List.of(SANG, MA, SANG, MA)),
    SANG_MA_MA_SANG(List.of(SANG, MA, MA, SANG));

    private final List<PieceType> formation;

    Formation(List<PieceType> formation) {
        this.formation = formation;
    }

    public List<PieceType> getFormation() {
        return formation;
    }
}
