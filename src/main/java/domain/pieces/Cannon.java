package domain.pieces;

import domain.Team;
import domain.board.BoardPoint;
import domain.board.PieceOnRoute;
import domain.movements.Direction;
import domain.movements.EndlessMovement;
import domain.movements.PieceMovement;
import domain.movements.Route;
import static domain.pieces.PieceNames.CANNON;
import java.util.Collections;
import java.util.List;

public final class Cannon implements Piece {

    private static final int VALID_BETWEEN_PIECE_COUNT = 1;
    private final Team team;
    private final PieceMovement movements;

    public Cannon(final Team team) {
        this.movements = new EndlessMovement(
                List.of(
                        new Route(Collections.nCopies(10, Direction.NORTH)),
                        new Route(Collections.nCopies(10, Direction.EAST)),
                        new Route(Collections.nCopies(10, Direction.SOUTH)),
                        new Route(Collections.nCopies(10, Direction.WEST))
                )
        );
        this.team = team;
    }

    @Override
    public boolean hasEqualTeam(final Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isAbleToArrive(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        final List<BoardPoint> arrivalBoardPoints = movements.calculateTotalArrivalPoints(startBoardPoint);
        return arrivalBoardPoints.contains(arrivalBoardPoint);
    }

    @Override
    public List<BoardPoint> getRoutePoints(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        return movements.calculateRoutePoints(startBoardPoint, arrivalBoardPoint);
    }

    @Override
    public boolean isMovable(final PieceOnRoute pieceOnRoute) {
        if (pieceOnRoute.countPieceOnRoute() != VALID_BETWEEN_PIECE_COUNT) {
            return false;
        }
        if (pieceOnRoute.canNotJumpOverFirstPiece()) {
            return false;
        }
        return !pieceOnRoute.hasArrivalPointInMyTeam(team);
    }

    @Override
    public boolean canNotJumpOver() {
        return true;
    }

    @Override
    public String getName() {
        return CANNON.getNameForTeam(team);
    }

}
