package domain.piece;

import domain.piece.strategy.*;

public enum PieceType {
    GENERAL {
        @Override
        public MoveStrategy createStrategy(Camp camp) {
            return new GeneralAndGuardStrategy();
        }
    },
    GUARD {
        @Override
        public MoveStrategy createStrategy(Camp camp) {
            return new GeneralAndGuardStrategy();
        }
    },
    HORSE {
        @Override
        public MoveStrategy createStrategy(Camp camp) {
            return new HorseStrategy();
        }
    },
    ELEPHANT {
        @Override
        public MoveStrategy createStrategy(Camp camp) {
            return new ElephantStrategy();
        }
    },
    CHARIOT {
        @Override
        public MoveStrategy createStrategy(Camp camp) {
            return new ChariotStrategy();
        }
    },
    CANNON {
        @Override
        public MoveStrategy createStrategy(Camp camp) {
            return new CannonStrategy();
        }
    },
    SOLDIER {
        @Override
        public MoveStrategy createStrategy(Camp camp) {
            return new SoldierStrategy(camp);
        }
    };

    public abstract MoveStrategy createStrategy(Camp camp);
}
