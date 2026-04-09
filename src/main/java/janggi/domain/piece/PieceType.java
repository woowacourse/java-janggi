package janggi.domain.piece;

import janggi.domain.Team;

public enum PieceType {
    GENERAL("장", 0.0) {
        @Override
        public Piece createPiece(Team team) {
            return new General(team);
        }
    },
    CHARIOT("차", 13.0) {
        @Override
        public Piece createPiece(Team team) {
            return new Chariot(team);
        }
    },
    CANNON("포", 7.0) {
        @Override
        public Piece createPiece(Team team) {
            return new Cannon(team);
        }
    },
    HORSE("마", 5.0) {
        @Override
        public Piece createPiece(Team team) {
            return new Horse(team);
        }
    },
    ELEPHANT("상", 3.0) {
        @Override
        public Piece createPiece(Team team) {
            return new Elephant(team);
        }
    },
    GUARD("사", 3.0) {
        @Override
        public Piece createPiece(Team team) {
            return new Guard(team);
        }
    },
    SOLDIER("졸", 2.0) {
        @Override
        public Piece createPiece(Team team) {
            return new Soldier(team);
        }
    },
    EMPTY("빈", 0.0) {
        @Override
        public Piece createPiece(Team team) {
            return new EmptyPiece();
        }
    };

    private final String name;
    private final double score;

    PieceType(String name, double score) {
        this.name = name;
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public abstract Piece createPiece(Team team);
}
