package pieces;

public enum PieceType {
    CHA {
        @Override
        public Piece create(Side side) {
            return new Cha(side);
        }
    },
    MA {
        @Override
        public Piece create(Side side) {
            return new Ma(side);
        }
    },
    SANG {
        @Override
        public Piece create(Side side) {
            return new Sang(side);
        }
    },
    SA {
        @Override
        public Piece create(Side side) {
            return new Sa(side);
        }
    },
    GUNG {
        @Override
        public Piece create(Side side) {
            return new Gung(side);
        }
    },
    PO {
        @Override
        public Piece create(Side side) {
            return new Po(side);
        }
    },
    JOL_BYEONG {
        @Override
        public Piece create(Side side) {
            return new JolByeong(side);
        }
    };

    public abstract Piece create(Side side);
}
