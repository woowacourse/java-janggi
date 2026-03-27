package domain.piece;

import domain.piece.strategy.*;

public enum PieceType {
    GENERAL{
        @Override
        public MoveStrategy createStrategy() {
            return new GeneralStrategy();
        }
    },
    GUARD{
        @Override
        public MoveStrategy createStrategy() {
            return new GuardStrategy();
        }
    },
    HORSE{
        @Override
        public MoveStrategy createStrategy() {
            return new HorseStrategy();
        }
    },
    ELEPHANT{
        @Override
        public MoveStrategy createStrategy() {
            return new ElephantStrategy();
        }
    },
    CHARIOT{
        @Override
        public MoveStrategy createStrategy() {
            return new ChariotStrategy();
        }
    },
    CANNON{
        @Override
        public MoveStrategy createStrategy() {
            return new CannonStrategy();
        }
    },
    SOLDIER{
        @Override
        public MoveStrategy createStrategy() {
            return new SoldierStrategy();
        }
    };


    public abstract MoveStrategy createStrategy();
}
