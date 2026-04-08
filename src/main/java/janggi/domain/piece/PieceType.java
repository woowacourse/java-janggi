package janggi.domain.piece;

import janggi.domain.Side;
import java.util.function.Function;

public enum PieceType {
    CHA("차", 13, Cha::new),
    GUNG("궁", 0, Gung::new),
    JOLBYEOUNG("졸,병", 2, Jolbyeong::new),
    MA("마", 5, Ma::new),
    PO("포", 7, Po::new),
    SA("사", 3, Sa::new),
    SANG("상", 3, Sang::new),
    EMPTY("없음", 0, null);

    private final String displayName;
    private final double score;
    private final Function<Side, Piece> pieceFactory;

    PieceType(String displayName, double score, Function<Side, Piece> pieceFactory) {
        this.displayName = displayName;
        this.score = score;
        this.pieceFactory = pieceFactory;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public double getScore() {
        return score;
    }

    public Piece createPiece(Side side) {
        if(this == EMPTY) {
            return EmptyPiece.getInstance();
        }
        return pieceFactory.apply(side);
    }
}
