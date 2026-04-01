package movepolicy.move;

import pieces.Side;
import position.Position;

public enum Step {

    FORWARD {
        @Override
        public Position move(Position position, Side side) {
            return position.move(side.forwardDelta());
        }
    },
    BACK {
        @Override
        public Position move(Position position, Side side) {
            return position.move(side.backDelta());
        }
    },
    LEFT {
        @Override
        public Position move(Position position, Side side) {
            return position.move(side.leftDelta());
        }
    },
    RIGHT {
        @Override
        public Position move(Position position, Side side) {
            return position.move(side.rightDelta());
        }
    },
    LEFT_FORWARD {
        @Override
        public Position move(Position position, Side side) {
            return position.move(side.leftForwardDelta());
        }
    },
    RIGHT_FORWARD {
        @Override
        public Position move(Position position, Side side) {
            return position.move(side.rightForwardDelta());
        }
    },
    RIGHT_BACK {
        @Override
        public Position move(Position position, Side side) {
            return position.move(side.rightBackDelta());
        }
    },
    LEFT_BACK {
        @Override
        public Position move(Position position, Side side) {
            return position.move(side.leftBackDelta());
        }
    };

    public abstract Position move(Position position, Side side);
}
