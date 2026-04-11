package domain.place.piece;

import domain.place.moveStrategy.CannonMoveStrategy;
import domain.place.moveStrategy.ChariotMoveStrategy;
import domain.place.moveStrategy.ChoSoldierMoveStrategy;
import domain.place.moveStrategy.ElephantMoveStrategy;
import domain.place.moveStrategy.GeneralMoveStrategy;
import domain.place.moveStrategy.GuardMoveStrategy;
import domain.place.moveStrategy.HanSoldierMoveStrategy;
import domain.place.moveStrategy.HorseMoveStrategy;

public enum PieceSymbol {
    GENERAL("궁") {
        @Override
        public Piece create(Side side) {
            return new General(side, new GeneralMoveStrategy());
        }
    },
    CHARIOT("차") {
        @Override
        public Piece create(Side side) {
            return new Chariot(side, new ChariotMoveStrategy());
        }
    },
    CANNON("포") {
        @Override
        public Piece create(Side side) {
            return new Cannon(side, new CannonMoveStrategy());
        }
    },
    HORSE("마") {
        @Override
        public Piece create(Side side) {
            return new Horse(side, new HorseMoveStrategy());
        }
    },
    ELEPHANT("상") {
        @Override
        public Piece create(Side side) {
            return new Elephant(side, new ElephantMoveStrategy());
        }
    },
    GUARD("사") {
        @Override
        public Piece create(Side side) {
            return new Guard(side, new GuardMoveStrategy());
        }
    },
    SOLDIER("졸") {
        @Override
        public Piece create(Side side) {
            if (side == Side.CHO) {
                return new Soldier(side, new ChoSoldierMoveStrategy());
            }
            return new Soldier(side, new HanSoldierMoveStrategy());
        }
    };

    private final String display;

    PieceSymbol(String display) {
        this.display = display;
    }

    public String display() {
        return display;
    }

    public abstract Piece create(Side side);
}
