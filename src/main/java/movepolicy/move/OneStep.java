package movepolicy.move;

import pieces.Side;
import position.Position;

public enum OneStep implements Step {

    FORWARD {
        @Override
        public Position move(Position position, Side side) {
            return position.moveForward(side);
        }
    },
    BACK {
        @Override
        public Position move(Position position, Side side) {
            return position.moveBack(side);
        }
    },
    LEFT {
        @Override
        public Position move(Position position, Side side) {
            return position.moveLeft(side);
        }
    },
    RIGHT {
        @Override
        public Position move(Position position, Side side) {
            return position.moveRight(side);
        }
    },
    LEFT_FORWARD {
        @Override
        public Position move(Position position, Side side) {
            return position.moveLeftForward(side);
        }
    },
    RIGHT_FORWARD {
        @Override
        public Position move(Position position, Side side) {
            return position.moveRightForward(side);
        }
    },
    RIGHT_BACK {
        @Override
        public Position move(Position position, Side side) {
            return position.moveRightBack(side);
        }
    },
    LEFT_BACK {
        @Override
        public Position move(Position position, Side side) {
            return position.moveLeftBack(side);
        }
    }
}
