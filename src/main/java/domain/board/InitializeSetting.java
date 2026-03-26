package domain.board;

import domain.piece.PieceType;

import java.util.List;

public enum InitializeSetting {
    LEFT_ELEPHANT_SETTING(List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE)),
    RIGHT_ELEPHANT_SETTING(List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT)),
    INNER_ELEPHANT_SETTING(List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.ELEPHANT, PieceType.HORSE)),
    OUTER_ELEPHANT_SETTING(List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.HORSE, PieceType.ELEPHANT));

    private final List<PieceType> initialSetting;

    InitializeSetting(List<PieceType> initialSetting) {
        this.initialSetting = initialSetting;
    }

    public List<PieceType> getInitialSetting() {
        return initialSetting;
    }



}
