package domain.piece;

import domain.path.ElephantPaths;
import domain.path.HoresePaths;
import domain.path.LinearPiecePaths;
import domain.path.PalacePiecePaths;
import domain.path.SoliderPaths;
import domain.strategy.LinearMovementStrategy;
import domain.strategy.PathMovementStrategy;

public enum PieceType {
    ELEPHANT(3) {
        @Override
        public Piece create(Side side) {
            return new Elephant(side, new ElephantPaths(), new PathMovementStrategy());
        }
    },
    CANNON(7) {
        @Override
        public Piece create(Side side) {
            return new Cannon(side, new LinearPiecePaths(), new LinearMovementStrategy());
        }
    },
    CHARIOT(13) {
        @Override
        public Piece create(Side side) {
            return new Chariot(side, new LinearPiecePaths(), new LinearMovementStrategy());
        }
    },
    GUARD(3) {
        @Override
        public Piece create(Side side) {
            return new Guard(side, new PalacePiecePaths(), new PathMovementStrategy());
        }
    },
    HORSE(5) {
        @Override
        public Piece create(Side side) {
            return new Horse(side, new HoresePaths(), new PathMovementStrategy());
        }
    },
    KING(0) {
        @Override
        public Piece create(Side side) {
            return new King(side, new PalacePiecePaths(), new PathMovementStrategy());
        }
    },
    SOLDIER(2) {
        @Override
        public Piece create(Side side) {
            return new Soldier(side, new SoliderPaths(side), new PathMovementStrategy());
        }
    };

    public final int score;

    PieceType(int score) {
        this.score = score;
    }

    public abstract Piece create(Side side);
}
