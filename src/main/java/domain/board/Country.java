package domain.board;

public enum Country {
    HAN {
        @Override
        public boolean isInPalace(Position position) {
            return position.x() >= 3 && position.x() <= 5 && position.y() >= 7 && position.y() <= 9;
        }
    },
    CHO {
        @Override
        public boolean isInPalace(Position position) {
            return position.x() >= 3 && position.x() <= 5 && position.y() >= 0 && position.y() <= 2;
        }
    },
    ;

    public abstract boolean isInPalace(Position position);
}
