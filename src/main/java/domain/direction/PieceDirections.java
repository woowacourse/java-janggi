package domain.direction;

import java.util.List;

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
                            .map(vector2 -> new Direction(List.of(vector1, vector2), false)))
                    .toList());
        }
    },
    ELEPHANT {
        @Override
        public Directions get() {
            return new Directions(Vector.getUpDownRightLeft().stream()
                    .flatMap(vector1 -> Vector.getDiagonals().stream()
                            .map(vector2 -> new Direction(List.of(vector1, vector2, vector2), false)))
                    .toList());
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
            return new Directions(List.of(
                    new Direction(List.of(Vector.DOWN), false),
                    new Direction(List.of(Vector.RIGHT), false),
                    new Direction(List.of(Vector.LEFT), false)
            ));
        }
    },
    CHO_SOLDIER {
        @Override
        public Directions get() {
            return new Directions(List.of(
                    new Direction(List.of(Vector.UP), false),
                    new Direction(List.of(Vector.RIGHT), false),
                    new Direction(List.of(Vector.LEFT), false)
            ));
        }
    };

    private static Directions getUpDownRightLeftDirections(boolean repeatable) {
        return new Directions(Vector.getUpDownRightLeft()
                .stream()
                .map(vector -> new Direction(List.of(vector), repeatable))
                .toList());
    }

    public abstract Directions get();
}
