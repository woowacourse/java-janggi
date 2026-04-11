package domain;

import domain.pieces.Cannon;
import domain.pieces.Chariot;
import domain.pieces.Elephant;
import domain.pieces.Empty;
import domain.pieces.General;
import domain.pieces.Guard;
import domain.pieces.Horse;
import domain.pieces.Piece;
import domain.pieces.Soldier;
import java.util.Collections;
import java.util.List;

public enum PieceType {
    GENERAL(0, List.of(new Position(4, 1))) {
        @Override
        public Piece create(Camp camp) {
            return new General(camp);
        }
    },
    GUARD(3, List.of(new Position(3, 0),
            new Position(5, 0))) {
        @Override
        public Piece create(Camp camp) {
            return new Guard(camp);
        }
    },
    HORSE(5, Collections.emptyList()) {
        @Override
        public Piece create(Camp camp) {
            return new Horse(camp);
        }
    },
    CANNON(7, List.of(new Position(1, 2),
            new Position(7, 2))) {
        @Override
        public Piece create(Camp camp) {
            return new Cannon(camp);
        }
    },
    ELEPHANT(3, Collections.emptyList()) {
        @Override
        public Piece create(Camp camp) {
            return new Elephant(camp);
        }
    },
    SOLDIER(2, List.of(new Position(0, 3),
            new Position(2, 3),
            new Position(4, 3), new Position(6, 3), new Position(8, 3))) {
        @Override
        public Piece create(Camp camp) {
            return new Soldier(camp);
        }
    },
    CHARIOT(13, List.of(new Position(0, 0),
            new Position(8, 0))) {
        @Override
        public Piece create(Camp camp) {
            return new Chariot(camp);
        }
    },
    NONE(0, Collections.emptyList()) {
        @Override
        public Piece create(Camp camp) {
            return new Empty(camp);
        }
    };

    private final int score;
    private final List<Position> initialPositions;

    PieceType(int score, List<Position> initialPositions) {
        this.score = score;
        this.initialPositions = initialPositions;
    }

    public abstract Piece create(Camp camp);

    public List<Position> getInitialPositions() {
        return initialPositions;
    }

    public int getScore() {
        return score;
    }
}
