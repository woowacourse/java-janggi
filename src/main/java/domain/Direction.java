    package domain;

    public enum Direction {
        UP(0, 1),
        DOWN(0, -1),
        LEFT(-1, 0),
        RIGHT(1, 0),
        UP_RIGHT(1, 1),
        DOWN_RIGHT(1, -1),
        UP_LEFT(-1, 1),
        DOWN_LEFT(-1, -1);


        private final Offset offset;

        Direction(int dx, int dy) {
            this.offset = new Offset(dx, dy);
        }

        public Offset getOffset() {
            return offset;
        }

        public static Direction decideXDirection(int dx) {
            if (dx > 0) {
                return Direction.RIGHT;
            }
            return Direction.LEFT;
        }

        public static Direction decideYDirection(int dy) {
            if (dy > 0) {
                return Direction.UP;
            }
            return Direction.DOWN;
        }
    }
