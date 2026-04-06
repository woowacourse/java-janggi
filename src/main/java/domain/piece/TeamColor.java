package domain.piece;

public enum TeamColor {
    CHO {
        @Override
        public TeamColor next() {
            return HAN;
        }
    },
    HAN {
        @Override
        public TeamColor next() {
            return CHO;
        }
    };

    public String displayName() {
        if (this == CHO) {
            return "초";
        }
        return "한";
    }

    public abstract TeamColor next();
}



