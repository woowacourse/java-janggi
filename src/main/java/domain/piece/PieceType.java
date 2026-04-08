package domain.piece;

import domain.path.ElephantPaths;
import domain.path.HoresePaths;
import domain.path.LinearPiecePaths;
import domain.path.PalacePiecePaths;
import domain.path.SoliderPaths;
import domain.strategy.LinearMovementStrategy;
import domain.strategy.PathMovementStrategy;

public enum PieceType {
    ELEPHANT {
        @Override
        public Piece create(Side side) {
            return new Elephant(side, new ElephantPaths(), new PathMovementStrategy());
        }
    },
    CANNON {
        @Override
        public Piece create(Side side) {
            return new Cannon(side, new LinearPiecePaths(), new LinearMovementStrategy());
        }
    },
    CHARIOT {
        @Override
        public Piece create(Side side) {
            return new Chariot(side, new LinearPiecePaths(), new LinearMovementStrategy());
        }
    },
    GUARD {
        @Override
        public Piece create(Side side) {
            return new Guard(side, new PalacePiecePaths(), new PathMovementStrategy());
        }
    },
    HORSE {
        @Override
        public Piece create(Side side) {
            return new Horse(side, new HoresePaths(), new PathMovementStrategy());
        }
    },
    KING {
        @Override
        public Piece create(Side side) {
            return new King(side, new PalacePiecePaths(), new PathMovementStrategy());
        }
    },
    SOLDIER {
        @Override
        public Piece create(Side side) {
            return new Soldier(side, new SoliderPaths(side), new PathMovementStrategy());
        }
    };

    public abstract Piece create(Side side);
}
