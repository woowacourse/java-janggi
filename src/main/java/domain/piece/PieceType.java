package domain.piece;

import domain.board.Country;

public enum PieceType {
    SOLDIER("soldier", 2) {
        @Override
        public Piece createPiece(Country country) {
            return new Soldier(country);
        }
    },
    GUARD("guard", 3) {
        @Override
        public Piece createPiece(Country country) {
            return new Guard(country);
        }
    },
    ELEPHANT("elephant", 3) {
        @Override
        public Piece createPiece(Country country) {
            return new Elephant(country);
        }
    },
    HORSE("horse", 5) {
        @Override
        public Piece createPiece(Country country) {
            return new Horse(country);
        }
    },
    CANNON("cannon", 7) {
        @Override
        public Piece createPiece(Country country) {
            return new Cannon(country);
        }
    },
    CHARIOT("chariot", 13) {
        @Override
        public Piece createPiece(Country country) {
            return new Chariot(country);
        }
    },
    GENERAL("general", 0) {
        @Override
        public Piece createPiece(Country country) {
            return new General(country);
        }
    },
    EMPTY("empty", 0) {
        @Override
        public Piece createPiece(Country country) {
            throw new IllegalStateException("[ERROR] 빈 칸은 기물을 생성할 수 없습니다.");
        }
    };

    public abstract Piece createPiece(Country country);

    private final String dbValue;
    private final int score;

    PieceType(String dbValue, int score) {
        this.dbValue = dbValue;
        this.score = score;
    }

    public String getDbValue() {
        return dbValue;
    }

    public int getScore() {
        return score;
    }
}
