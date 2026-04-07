package domain.board;

import domain.strategy.*;

public enum Type {

    GENERAL("궁", 0) {
        @Override
        public MoveStrategy createStrategy() {
            return new GeneralMoveStrategy();
        }
    },

    CHARIOT("차", 13) {
        @Override
        public MoveStrategy createStrategy() {
            return new ChariotMoveStrategy();
        }
    },
    CANNON("포", 7) {
        @Override
        public MoveStrategy createStrategy() {
            return new CannonMoveStrategy();
        }
    },
    HORSE("마", 5) {
        @Override
        public MoveStrategy createStrategy() {
            return new HorseMoveStrategy();
        }
    },
    ELEPHANT("상", 3) {
        @Override
        public MoveStrategy createStrategy() {
            return new ElephantMoveStrategy();
        }
    },
    GUARD("사", 3) {
        @Override
        public MoveStrategy createStrategy() {
            return new GuardMoveStrategy();
        }
    },
    SOLDIER("졸", 2) {
        @Override
        public MoveStrategy createStrategy() {
            return new SoldierMoveStrategy();
        }
    };

    private final String name;
    private final int score;

    Type(final String name, final int score) {
        this.name = name;
        this.score = score;
    }

    public abstract MoveStrategy createStrategy();

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
