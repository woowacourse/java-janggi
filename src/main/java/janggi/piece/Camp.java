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
    ;

    public abstract Camp reverse();
}
