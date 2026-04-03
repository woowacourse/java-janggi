package janggi.domain.status;

public enum Team {
    CHO {
        public boolean isBackward(int signRow) {
            return signRow < 0;
        }
    },
    HAN {
        public boolean isBackward(int signRow) {
            return signRow > 0;
        }
    };

    public abstract boolean isBackward(int signRow);
}
