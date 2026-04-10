package movepolicy.move;

import pieces.Side;
import position.Position;

public enum Step {

    FORWARD {
        @Override
        public boolean canMove(final Position position, final Side side) {
            return position.canMove(side.forwardDelta());
        }

        @Override
        public Position move(final Position position, final Side side) {
            return position.move(side.forwardDelta());
        }
    },
    BACK {
        @Override
        public boolean canMove(final Position position, final Side side) {
            return position.canMove(side.backDelta());
        }

        @Override
        public Position move(final Position position, final Side side) {
            return position.move(side.backDelta());
        }
    },
    LEFT {
        @Override
        public boolean canMove(final Position position, final Side side) {
            return position.canMove(side.leftDelta());
        }

        @Override
        public Position move(final Position position, final Side side) {
            return position.move(side.leftDelta());
        }
    },
    RIGHT {
        @Override
        public boolean canMove(final Position position, final Side side) {
            return position.canMove(side.rightDelta());
        }

        @Override
        public Position move(final Position position, final Side side) {
            return position.move(side.rightDelta());
        }
    },
    LEFT_FORWARD {
        @Override
        public boolean canMove(final Position position, final Side side) {
            return position.canMove(side.leftForwardDelta());
        }

        @Override
        public Position move(final Position position, final Side side) {
            return position.move(side.leftForwardDelta());
        }
    },
    RIGHT_FORWARD {
        @Override
        public boolean canMove(final Position position, final Side side) {
            return position.canMove(side.rightForwardDelta());
        }

        @Override
        public Position move(final Position position, final Side side) {
            return position.move(side.rightForwardDelta());
        }
    },
    RIGHT_BACK {
        @Override
        public boolean canMove(final Position position, final Side side) {
            return position.canMove(side.rightBackDelta());
        }

        @Override
        public Position move(final Position position, final Side side) {
            return position.move(side.rightBackDelta());
        }
    },
    LEFT_BACK {
        @Override
        public boolean canMove(final Position position, final Side side) {
            return position.canMove(side.leftBackDelta());
        }

        @Override
        public Position move(final Position position, final Side side) {
            return position.move(side.leftBackDelta());
        }
    },
    ;

    public abstract Position move(final Position position, final Side side);

    public abstract boolean canMove(final Position position, final Side side);
}
