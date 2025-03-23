package janggi.piece;

public enum Camp {

    HAN {
        @Override
        public Camp reverse() {
            return CHU;
        }
    },
    CHU {
        @Override
        public Camp reverse() {
            return HAN;
        }
    },
    NEUTRAL {
        @Override
        public Camp reverse() {
            return NEUTRAL;
        }
    },
    ;

    public abstract Camp reverse();

    public boolean isBottom() {
        return this == CHU;
    }
}
