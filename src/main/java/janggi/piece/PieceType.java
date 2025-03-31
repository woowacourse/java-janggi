package janggi.piece;

import janggi.camp.Camp;

public enum PieceType {

    GENERAL(0) {
        @Override
        public String getName(Camp camp) {
            return "왕";
        }
    },
    CHARIOT(13) {
        @Override
        public String getName(Camp camp) {
            return "차";
        }
    },
    CANNON(7) {
        @Override
        public String getName(Camp camp) {
            return "포";
        }
    },
    HORSE(5) {
        @Override
        public String getName(Camp camp) {
            return "마";
        }
    },
    ELEPHANT(3) {
        @Override
        public String getName(Camp camp) {
            return "상";
        }
    },
    GUARD(3) {
        @Override
        public String getName(Camp camp) {
            return "사";
        }
    },
    SOLDIER(2) {
        @Override
        public String getName(Camp camp) {
            if (camp == Camp.CHU) {
                return "졸";
            }
            return "병";
        }
    };

    private final int score;

    PieceType(int score) {
        this.score = score;
    }

    public abstract String getName(Camp camp);

    public int getScore() {
        return score;
    }

    public static Piece toPiece(String name, Camp camp) {
        for (PieceType type : values()) {
            if (type.getName(camp).equals(name)) {
                return switch (type.getName(camp)) {
                    case "왕" -> new General(camp);
                    case "차" -> new Chariot(camp);
                    case "포" -> new Cannon(camp);
                    case "마" -> new Horse(camp);
                    case "상" -> new Elephant(camp);
                    case "사" -> new Guard(camp);
                    case "졸", "병" -> new Soldier(camp);
                    default -> throw new IllegalStateException("일치하는 PieceType이 없습니다.");
                };
            }
        }
        throw new IllegalArgumentException("일치하는 Piece가 없습니다.");
    }
}
