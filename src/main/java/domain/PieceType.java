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
    GENERAL(List.of(new Position(4, 1))) {
        @Override
        public Piece create(Camp camp) {
            return new General(camp);
        }
    },
    GUARD(List.of(new Position(3, 0),
            new Position(5, 0))) {
        @Override
        public Piece create(Camp camp) {
            return new Guard(camp);
        }
    },
    HORSE(Collections.emptyList()) {
        @Override
        public Piece create(Camp camp) {
            return new Horse(camp);
        }
    },
    CANNON(List.of(new Position(1, 2),
            new Position(7, 2))) {
        @Override
        public Piece create(Camp camp) {
            return new Cannon(camp);
        }
    },
    ELEPHANT(Collections.emptyList()) {
        @Override
        public Piece create(Camp camp) {
            return new Elephant(camp);
        }
    },
    SOLDIER(List.of(new Position(0, 3),
            new Position(2, 3),
            new Position(4, 3), new Position(6, 3), new Position(8, 3))) {
        @Override
        public Piece create(Camp camp) {
            return new Soldier(camp);
        }
    },
    CHARIOT(List.of(new Position(0, 0),
            new Position(8, 0))) {
        @Override
        public Piece create(Camp camp) {
            return new Chariot(camp);
        }
    },
    NONE(Collections.emptyList()) {
        @Override
        public Piece create(Camp camp) {
            return new Empty(camp);
        }
    };

    private final List<Position> initialPositions;

    PieceType(List<Position> initialPositions) {
        this.initialPositions = initialPositions;
    }

    public abstract Piece create(Camp camp);

    public List<Position> getInitialPositions() {
        return initialPositions;
    }
}
