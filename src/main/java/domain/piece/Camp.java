package domain.piece;

import java.util.List;

public enum Camp {
    CHO(-1) {
        @Override
        public List<PieceType> arrange(List<PieceType> pieceTypes) {
            return pieceTypes;
        }

        @Override
        public int resolveY(int defaultHanY) {
            return TOTAL_Y_COORDINATE - defaultHanY;
        }
    },
    HAN(1) {
        @Override
        public List<PieceType> arrange(List<PieceType> pieceTypes) {
            return pieceTypes.reversed();
        }

        @Override
        public int resolveY(int defaultHanY) {
            return defaultHanY;
        }
    };

    private static final int TOTAL_Y_COORDINATE = 11;
    private final int forward;

    Camp(int forward) {
        this.forward = forward;
    }

    public int forward() {
        return this.forward;
    }

    public abstract List<PieceType> arrange(List<PieceType> pieceTypes);

    public abstract int resolveY(int defaultHanY);
}
