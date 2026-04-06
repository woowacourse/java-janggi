package domain.piece;

import domain.board.Country;

public enum PieceType {
    SOLDIER(2) {
        @Override
        public Piece createPiece(Country country) {
            return new Soldier(country);
        }
    },
    GUARD(3) {
        @Override
        public Piece createPiece(Country country) {
            return new Guard(country);
        }
    },
    ELEPHANT(3) {
        @Override
        public Piece createPiece(Country country) {
            return new Elephant(country);
        }
    },
    HORSE(5) {
        @Override
        public Piece createPiece(Country country) {
            return new Horse(country);
        }
    },
    CANNON(7) {
        @Override
        public Piece createPiece(Country country) {
            return new Cannon(country);
        }
    },
    CHARIOT(13) {
        @Override
        public Piece createPiece(Country country) {
            return new Chariot(country);
        }
    },
    GENERAL(0) {
        @Override
        public Piece createPiece(Country country) {
            return new General(country);
        }
    },
    EMPTY(0) {
        @Override
        public Piece createPiece(Country country) {
            throw new IllegalStateException("[ERROR] 빈 칸은 기물을 생성할 수 없습니다.");
        }
    };

    public abstract Piece createPiece(Country country);

    private final int score;

    PieceType(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
