package domain.piece;

import domain.piece.strategy.CannonStrategy;
import domain.piece.strategy.ChariotStrategy;
import domain.piece.strategy.ElephantStrategy;
import domain.piece.strategy.GeneralAndGuardStrategy;
import domain.piece.strategy.HorseStrategy;
import domain.piece.strategy.MoveStrategy;
import domain.piece.strategy.SoldierStrategy;

public enum PieceType {
    GENERAL(0) {
        @Override
        public MoveStrategy createStrategy(Camp camp) {
            return new GeneralAndGuardStrategy();
        }
    },
    GUARD(3) {
        @Override
        public MoveStrategy createStrategy(Camp camp) {
            return new GeneralAndGuardStrategy();
        }
    },
    HORSE(5) {
        @Override
        public MoveStrategy createStrategy(Camp camp) {
            return new HorseStrategy();
        }
    },
    ELEPHANT(3) {
        @Override
        public MoveStrategy createStrategy(Camp camp) {
            return new ElephantStrategy();
        }
    },
    CHARIOT(13) {
        @Override
        public MoveStrategy createStrategy(Camp camp) {
            return new ChariotStrategy();
        }
    },
    CANNON(7) {
        @Override
        public MoveStrategy createStrategy(Camp camp) {
            return new CannonStrategy();
        }
    },
    SOLDIER(2) {
        @Override
        public MoveStrategy createStrategy(Camp camp) {
            return new SoldierStrategy(camp);
        }
    };

    private final int score;

    PieceType(int score) {
        this.score = score;
    }

    public int score() {
        return score;
    }

    public abstract MoveStrategy createStrategy(Camp camp);
}
