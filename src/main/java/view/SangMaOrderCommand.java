package view;

import domain.PieceType;

import java.util.List;

public enum SangMaOrderCommand {

    SANG_MA_SANG_MA("1", List.of(PieceType.SANG, PieceType.MA, PieceType.SANG, PieceType.MA)),
    SANG_MA_MA_SANG("2", List.of(PieceType.SANG, PieceType.MA, PieceType.MA, PieceType.SANG)),
    MA_SANG_SANG_MA("3", List.of(PieceType.MA, PieceType.SANG, PieceType.SANG, PieceType.MA)),
    MA_SANG_MA_SANG("4", List.of(PieceType.MA, PieceType.SANG, PieceType.MA, PieceType.SANG)),
    ;

    private final String input;
    private final List<PieceType> pieceTypes;

    SangMaOrderCommand(String input, List<PieceType> pieceTypes) {
        this.input = input;
        this.pieceTypes = pieceTypes;
    }

    public List<PieceType> getPieceTypes() {
        return pieceTypes;
    }
}
