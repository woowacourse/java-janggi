package domain.pieces;

import domain.Team;
import domain.board.BoardPoint;
import domain.board.PieceOnRoute;
import domain.movements.DefaultMovement;
import domain.movements.Direction;
import domain.movements.PieceMovement;
import domain.movements.Route;
import static domain.pieces.PieceNames.GUARD;
import java.util.List;

public final class Guard implements Piece {

    private final Team team;
    private final PieceMovement movement;

    public Guard(final Team team) {
        this.team = team;
        this.movement = new DefaultMovement(List.of(
                new Route(List.of(Direction.NORTH)),
                new Route(List.of(Direction.SOUTH)),
                new Route(List.of(Direction.EAST)),
                new Route(List.of(Direction.WEST)),
                new Route(List.of(Direction.SOUTHEAST)),
                new Route(List.of(Direction.SOUTHWEST)),
                new Route(List.of(Direction.NORTHEAST)),
                new Route(List.of(Direction.NORTHWEST))
        ));
    }


    @Override
    public boolean hasEqualTeam(final Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isAbleToArrive(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        if (!arrivalBoardPoint.isInPalace()) {
            return false;
        }
        return movement.calculateTotalArrivalPoints(startBoardPoint).contains(arrivalBoardPoint);
    }

    @Override
    public List<BoardPoint> getRoutePoints(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        return movement.calculateRoutePoints(startBoardPoint, arrivalBoardPoint);
    }

    @Override
    public boolean isMovable(final PieceOnRoute pieceOnRoute) {
        return !pieceOnRoute.hasArrivalPointInMyTeam(team);
    }

    @Override
    public boolean canNotJumpOver() {
        return false;
    }

    @Override
    public String getName() {
        return GUARD.getNameForTeam(team);
    }

    @Override
    public int getScore() {
        return 3;
    }
}
