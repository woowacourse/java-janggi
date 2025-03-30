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
}
