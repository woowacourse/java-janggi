package position;

public enum Direction {
    UP {
        @Override
        public Position move(Position position) {
            return position.moveUp();
        }
    },
    DOWN {
        @Override
        public Position move(Position position) {
            return position.moveDown();
        }
    },
    LEFT {
        @Override
        public Position move(Position position) {
            return position.moveLeft();
        }
    },
    RIGHT {
        @Override
        public Position move(Position position) {
            return position.moveRight();
        }
    },
    RIGHT_UP {
        @Override
        public Position move(Position position) {
            return position.moveRightUp();
        }
    },
    LEFT_UP {
        @Override
        public Position move(Position position) {
            return position.moveLeftUp();
        }
    },
    RIGHT_DOWN {
        @Override
        public Position move(Position position) {
            return position.moveRightDown();
        }
    },
    LEFT_DOWN {
        @Override
        public Position move(Position position) {
            return position.moveLeftDown();
        }
    };

    public abstract Position move(Position position);
}
