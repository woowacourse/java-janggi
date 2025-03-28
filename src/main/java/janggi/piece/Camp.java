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

    public boolean isEnemy(Camp otherCamp) {
        return this != otherCamp;
    }
}
