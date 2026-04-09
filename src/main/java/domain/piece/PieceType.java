package domain.piece;

import domain.board.Country;
import java.util.Arrays;

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

    public static PieceType from(String dbValue) {
        return Arrays.stream(PieceType.values())
                .filter(pieceType -> pieceType.dbValue.equals(dbValue))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("[ERROR] PieceType에 일치하는 값이 존재하지 않습니다."));
    }

    public String getDbValue() {
        return dbValue;
    }

    public int getScore() {
        return score;
    }
}
