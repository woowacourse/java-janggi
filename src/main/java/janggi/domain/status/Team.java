package janggi.domain.status;

public enum Team {
    CHO {
        @Override
        public boolean isBackward(int signRow) {
            return signRow < 0;
        }
    },
    HAN {
        @Override
        public boolean isBackward(int signRow) {
            return signRow > 0;
        }
    };

    public abstract boolean isBackward(int signRow);
}
