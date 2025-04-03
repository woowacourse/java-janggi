package janggi.domain.piece.type;

import janggi.domain.camp.Camp;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;

public enum PieceType {

    GENERAL(0, false) {
        @Override
        public String getName(Camp camp) {
            return "왕";
        }
    },
    CHARIOT(13, true) {
        @Override
        public String getName(Camp camp) {
            return "차";
        }
    },
    CANNON(7, true) {
        @Override
        public String getName(Camp camp) {
            return "포";
        }
    },
    HORSE(5, false) {
        @Override
        public String getName(Camp camp) {
            return "마";
        }
    },
    ELEPHANT(3, true) {
        @Override
        public String getName(Camp camp) {
            return "상";
        }
    },
    GUARD(3, false) {
        @Override
        public String getName(Camp camp) {
            return "사";
        }
    },
    SOLDIER(2, false) {
        @Override
        public String getName(Camp camp) {
            if (camp == Camp.CHU) {
                return "졸";
            }
            return "병";
        }
    };

    private final int score;
    private final boolean routable;

    PieceType(int score, boolean routable) {
        this.score = score;
        this.routable = routable;
    }

    public abstract String getName(Camp camp);

    public int getScore() {
        return score;
    }

    public boolean isRoutable() {
        return routable;
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
