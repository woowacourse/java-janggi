package domain.piece;

import domain.Side;
import domain.strategy.LinearMovement;
import domain.strategy.PathMovement;

public enum PieceType {
    ELEPHANT {
        @Override
        public Piece create(Side side) {
            return new Elephant(side, new PathMovement());
        }
    },
    CANNON {
        @Override
        public Piece create(Side side) {
            return new Cannon(side, new LinearMovement());
        }
    },
    CHARIOT {
        @Override
        public Piece create(Side side) {
            return new Chariot(side, new LinearMovement());
        }
    },
    GUARD {
        @Override
        public Piece create(Side side) {
            return new Guard(side, new PathMovement());
        }
    },
    HORSE {
        @Override
        public Piece create(Side side) {
            return new Horse(side, new PathMovement());
        }
    },
    KING {
        @Override
        public Piece create(Side side) {
            return new King(side, new PathMovement());
        }
    },
    EMPTY {
        @Override
        public Piece create(Side side) {
            return new Empty();
        }
    },
    SOLDIER {
        @Override
        public Piece create(Side side) {
            return new Soldier(side, new PathMovement());
        }
    };

    public abstract Piece create(Side side);
}
