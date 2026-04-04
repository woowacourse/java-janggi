package janggi.domain.piece;

import janggi.domain.piece.unit.Advisor;
import janggi.domain.piece.unit.Cannon;
import janggi.domain.piece.unit.Chariot;
import janggi.domain.piece.unit.Elephant;
import janggi.domain.piece.unit.Empty;
import janggi.domain.piece.unit.General;
import janggi.domain.piece.unit.Horse;
import janggi.domain.piece.unit.Piece;
import janggi.domain.piece.unit.Soldier;
import janggi.domain.side.Side;

public enum PieceType {
    CHARIOT("車", "車", 13) {
        @Override
        public Piece create(Side side) {
            return new Chariot(side);
        }
    },
    CANNON("包", "包", 7) {
        @Override
        public Piece create(Side side) {
            return new Cannon(side);
        }
    },
    HORSE("馬", "馬", 5) {
        @Override
        public Piece create(Side side) {
            return new Horse(side);
        }
    },
    ELEPHANT("象", "象", 3) {
        @Override
        public Piece create(Side side) {
            return new Elephant(side);
        }
    },
    ADVISOR("士", "士", 3) {
        @Override
        public Piece create(Side side) {
            return new Advisor(side);
        }
    },
    SOLDIER("卒", "兵", 2) {
        @Override
        public Piece create(Side side) {
            return new Soldier(side);
        }
    },
    GENERAL("楚", "漢", 0) {
        @Override
        public Piece create(Side side) {
            return new General(side);
        }
    },
    NONE("  ", "  ", 0) {
        @Override
        public Piece create(Side side) {
            return Empty.INSTANCE;
        }
    };

    private final String hanName;
    private final String choName;
    private final double score;

    PieceType(String hanName, String choName, double score) {
        this.hanName = hanName;
        this.choName = choName;
        this.score = score;
    }

    public abstract Piece create(Side side);

    public String getNameFormat(Side side) {
        if (Side.HAN.equals(side)) {
            return hanName;
        }
        if (Side.CHO.equals(side)) {
            return choName;
        }
        throw new IllegalStateException("Side는 Han 또는 Cho를 넣어주세요");
    }

    public double getScore() {
        return score;
    }
}
