package view.label;

import domain.game.Side;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import java.util.Arrays;

public enum WingLabel {
    ELEPHANT(
            "상",
            new Elephant(Side.HAN),
            new Elephant(Side.CHO)
    ),
    HORSE(
            "마",
            new Horse(Side.HAN),
            new Horse(Side.CHO)
    ),
    ;

    private final String label;
    private final Piece hanPiece;
    private final Piece choPiece;

    WingLabel(
            String label,
            Piece hanPiece,
            Piece choPiece
    ) {
        this.label = label;
        this.hanPiece = hanPiece;
        this.choPiece = choPiece;
    }

    public static Piece from(String label, Side side) {
        return Arrays.stream(values())
                .filter(wingLabel -> wingLabel.hasSameLabel(label))
                .map(wingLabel -> wingLabel.createPiece(side))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("'상' 혹은 '마'를 입력해야 합니다."));
    }

    public Piece createPiece(Side side) {
        if (side == Side.HAN) {
            return hanPiece;
        }

        return choPiece;
    }

    private boolean hasSameLabel(String label) {
        return this.label.equals(label);
    }
}
