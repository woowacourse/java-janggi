package domain.board;

public enum Country {
    HAN("한나라") {
        @Override
        public boolean isInPalace(Position position) {
            return position.x() >= 3 && position.x() <= 5 && position.y() >= 7 && position.y() <= 9;
        }
    },
    CHO("초나라") {
        @Override
        public boolean isInPalace(Position position) {
            return position.x() >= 3 && position.x() <= 5 && position.y() >= 0 && position.y() <= 2;
        }
    },
    ;

    public abstract boolean isInPalace(Position position);

    private final String dbValue;

    Country(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }
}
