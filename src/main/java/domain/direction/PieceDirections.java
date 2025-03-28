package domain.direction;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum PieceDirections {

    GENERAL {
        @Override
        public Directions get() {
            return getUpDownRightLeftDirections(false);
        }
    },
    CHARIOT {
        @Override
        public Directions get() {
            return getUpDownRightLeftDirections(true);
        }
    },
    CANNON {
        @Override
        public Directions get() {
            return getUpDownRightLeftDirections(true);
        }
    },
    HORSE {
        @Override
        public Directions get() {
            return new Directions(Vector.getUpDownRightLeft().stream()
                    .flatMap(vector1 -> Vector.getDiagonals().stream()
                            .map(vector2 -> new Direction(List.of(vector1, vector2))))
                    .collect(Collectors.toSet()), false);
        }
    },
    ELEPHANT {
        @Override
        public Directions get() {
            return new Directions(Vector.getUpDownRightLeft().stream()
                    .flatMap(vector1 -> Vector.getDiagonals().stream()
                            .map(vector2 -> new Direction(List.of(vector1, vector2, vector2))))
                    .collect(Collectors.toSet()), false);
        }
    },
    GUARD {
        @Override
        public Directions get() {
            return getUpDownRightLeftDirections(false);
        }
    },
    HAN_SOLDIER {
        @Override
        public Directions get() {
            return new Directions(Set.of(
                    new Direction(List.of(Vector.DOWN)),
                    new Direction(List.of(Vector.RIGHT)),
                    new Direction(List.of(Vector.LEFT))
            ), false);
        }
    },
    CHO_SOLDIER {
        @Override
        public Directions get() {
            return new Directions(Set.of(
                    new Direction(List.of(Vector.UP)),
                    new Direction(List.of(Vector.RIGHT)),
                    new Direction(List.of(Vector.LEFT))
            ), false);
        }
    };

    private static Directions getUpDownRightLeftDirections(boolean repeatable) {
        return new Directions(Vector.getUpDownRightLeft()
                .stream()
                .map(vector -> new Direction(List.of(vector)))
                .collect(Collectors.toSet()), repeatable);
    }

    public abstract Directions get();
}
