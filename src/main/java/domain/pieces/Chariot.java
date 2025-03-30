package domain.pieces;

import domain.Team;
import domain.board.BoardPoint;
import domain.board.PieceOnRoute;
import domain.movements.Direction;
import domain.movements.EndlessMovement;
import domain.movements.PieceMovement;
import domain.movements.Route;
import static domain.pieces.PieceNames.CHARIOT;
import java.util.Collections;
import java.util.List;

public final class Chariot implements Piece {

    private final Team team;
    private final PieceMovement movements;
//    private final PieceMovement movementInPalace;

    public Chariot(final Team team) {
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
        if (pieceOnRoute.hasArrivalPointInMyTeam(team)) {
            return false;
        }
        return pieceOnRoute.hasNotPieceOnRoute();
    }

    @Override
    public boolean canNotJumpOver() {
        return false;
    }


    @Override
    public String getName() {
        return CHARIOT.getNameForTeam(team);
    }
}
