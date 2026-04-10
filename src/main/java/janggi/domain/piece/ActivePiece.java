package janggi.domain.piece;

import janggi.domain.PalaceTopology;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.BaseBoard;
import janggi.domain.policy.RoutePolicy;

import java.util.List;

public abstract class ActivePiece extends BasePiece {
    public static final String INVALID_DESTINATION_MESSAGE = "올바른 도착 지점이 아닙니다.";
    public static final String UNMOVABLE_ROUTE_MESSAGE = "이동할 수 없는 경로입니다.";
    protected final RoutePolicy routePolicy;
    protected final PalaceTopology palaceTopology;

    public ActivePiece(RoutePolicy routePolicy, PalaceTopology palaceTopology, Side side, PieceType pieceType) {
        super(side, pieceType);
        this.routePolicy = routePolicy;
        this.palaceTopology = palaceTopology;
    }

    @Override
    public void validateRoute(List<Position> path, BaseBoard baseBoard) {
        if (!routePolicy.isMovable(path, side, baseBoard)) {
            throw new IllegalArgumentException(UNMOVABLE_ROUTE_MESSAGE);
        }
    }

    protected void validatePalace(Position position, String message) {
        if (!palaceTopology.isPalace(position)) {
            throw new IllegalArgumentException(message);
        }
    }
}
