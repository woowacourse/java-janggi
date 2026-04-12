package domain.setup;

import domain.piece.PieceType;
import java.util.Arrays;
import java.util.List;

public enum Arrangement {
    MASANGMASANG("1", List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT)),
    MASANGSANGMA("2", List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.ELEPHANT, PieceType.HORSE)),
    SANGMAMASANG("3", List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.HORSE, PieceType.ELEPHANT)),
    SANGMASANGMA("4", List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE));

    private final String value;
    private final List<PieceType> innerPieces;

    Arrangement(String value, List<PieceType> innerPieces) {
        this.value = value;
        this.innerPieces = innerPieces;
    }

    public List<PieceType> innerPieces() {
        return innerPieces;
    }

    public static Arrangement toArrangement(String setupCommand) {
        return Arrays.stream(values())
                .filter(arrangement -> arrangement.value.equals(setupCommand))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 1~4까지의 숫자만 입력 가능합니다."));
    }

}
