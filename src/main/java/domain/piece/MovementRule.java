package domain.piece;

import domain.direction.Directions;
import domain.direction.PieceDirections;
import domain.position.Palace;
import domain.position.Position;
import java.util.List;

public enum MovementRule {
    // 이동 방향성, 궁성 이동 가능한지, 이동 가능한 최대 최소 영역
    HAN_GENERAL(PieceDirections.GENERAL, true) {
        @Override
        public boolean isValidRangePosition(final Position position) {
            return isInHanPalace(position.getRow(), position.getColumn());
        }
    },
    CHO_GENERAL(PieceDirections.GENERAL, true) {
        @Override
        public boolean isValidRangePosition(final Position position) {
            return isInChoPalace(position.getRow(), position.getColumn());
        }
    },
    CANNON(PieceDirections.CANNON, true) {
        @Override
        public boolean isValidRangePosition(final Position position) {
            return isInBoard(position.getRow(), position.getColumn());
        }
    },
    CHARIOT(PieceDirections.CHARIOT, false) {
        @Override
        public boolean isValidRangePosition(final Position position) {
            return isInBoard(position.getRow(), position.getColumn());
        }
    },
    ELEPHANT(PieceDirections.ELEPHANT, false) {
        @Override
        public boolean isValidRangePosition(final Position position) {
            return isInBoard(position.getRow(), position.getColumn());
        }
    },
    HAN_GUARD(PieceDirections.GUARD, true) {
        @Override
        public boolean isValidRangePosition(final Position position) {
            return isInHanPalace(position.getRow(), position.getColumn());
        }
    },
    CHO_GUARD(PieceDirections.GUARD, true) {
        @Override
        public boolean isValidRangePosition(final Position position) {
            return isInChoPalace(position.getRow(), position.getColumn());
        }
    },
    HORSE(PieceDirections.HORSE, false) {
        @Override
        public boolean isValidRangePosition(final Position position) {
            return isInBoard(position.getRow(), position.getColumn());
        }
    },
    HAN_SOLDIER(PieceDirections.HAN_SOLDIER, true) {
        @Override
        public boolean isValidRangePosition(final Position position) {
            return isInBoard(position.getRow(), position.getColumn());
        }
    },
    CHO_SOLDIER(PieceDirections.CHO_SOLDIER, true) {
        @Override
        public boolean isValidRangePosition(final Position position) {
            return isInBoard(position.getRow(), position.getColumn());
        }
    },
    ;

    private final PieceDirections directions;
    private final boolean canMoveInPalace; // 궁성 내 이동 가능한가?

    MovementRule(PieceDirections directions, boolean canMoveInPalace) {
        this.directions = directions;
        this.canMoveInPalace = canMoveInPalace;
    }

    public abstract boolean isValidRangePosition(final Position position);

    public List<Position> getPath(final Position start, final Position target) {
        Directions movableDirections = getMovableDirections(start, target);
        return movableDirections.getPath(start, target);
    }

    public boolean canMoveToTargetPosition(final Position start, final Position target) {
        Directions movableDirections = getMovableDirections(start, target);
        return movableDirections.canReachToTarget(start, target);
    }

    private static boolean isInBoard(int row, int column) {
        return row >= Position.MIN_ROW && row <= Position.MAX_ROW &&
                column >= Position.MIN_COLUMN && column <= Position.MAX_COLUMN;
    }

    private static boolean isInHanPalace(int row, int column) {
        return row >= Palace.MIN_ROW && row <= Palace.MAX_ROW &&
                column >= Palace.HAN_MIN_COLUMN && column <= Palace.HAN_MAX_COLUMN;
    }

    private static boolean isInChoPalace(int row, int column) {
        return row >= Palace.MIN_ROW && row <= Palace.MAX_ROW &&
                column >= Palace.CHO_MIN_COLUMN && column <= Palace.CHO_MAX_COLUMN;
    }

    private Directions getMovableDirections(Position start, Position target) {
        Directions movableDirections = directions.get();
        if (canMoveInPalace && Palace.isInPalace(start) && Palace.isInPalace(target)) {
            movableDirections = movableDirections.addDirection(Palace.getMovableDirectionInPalace(start));
        }
        return movableDirections;
    }
}
