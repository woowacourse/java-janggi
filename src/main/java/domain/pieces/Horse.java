package domain.pieces;

import domain.Team;
import domain.board.BoardPoint;
import domain.board.PieceOnRoute;
import domain.movements.DefaultMovement;
import domain.movements.Direction;
import domain.movements.PieceMovement;
import domain.movements.Route;
import static domain.pieces.PieceNames.HORSE;
import java.util.List;

public final class Horse implements Piece {
    private final Team team;
    private final PieceMovement defaultMovement;

    public Horse(final Team team) {
        this.team = team;
        this.defaultMovement = new DefaultMovement(List.of(
                new Route(List.of(Direction.NORTH, Direction.NORTHWEST)),
                new Route(List.of(Direction.NORTH, Direction.NORTHEAST)),
                new Route(List.of(Direction.EAST, Direction.NORTHEAST)),
                new Route(List.of(Direction.EAST, Direction.SOUTHEAST)),
                new Route(List.of(Direction.SOUTH, Direction.SOUTHEAST)),
                new Route(List.of(Direction.SOUTH, Direction.SOUTHWEST)),
                new Route(List.of(Direction.WEST, Direction.SOUTHWEST)),
                new Route(List.of(Direction.WEST, Direction.NORTHWEST))
        ));
        ;
    }

    @Override
    public boolean hasEqualTeam(final Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isAbleToArrive(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        return defaultMovement.calculateTotalArrivalPoints(startBoardPoint).contains(arrivalBoardPoint);
    }

    @Override
    public List<BoardPoint> getRoutePoints(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        return defaultMovement.calculateRoutePoints(startBoardPoint, arrivalBoardPoint);
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
        return HORSE.getNameForTeam(team);
    }

    @Override
    public int getScore() {
        return 5;
    }
}
