package domain.pieces;

import domain.Team;
import domain.board.BoardPoint;
import domain.board.PieceOnRoute;
import domain.movements.DefaultMovement;
import domain.movements.Direction;
import domain.movements.PieceMovement;
import domain.movements.Route;
import static domain.pieces.PieceNames.SOLDIER;
import java.util.List;

public final class Soldier implements Piece {


    private final Team team;
    private final PieceMovement defaultMovement;

    public Soldier(Team team) {
        this.team = team;
        if (team == Team.CHO) {
            this.defaultMovement = new DefaultMovement(List.of(
                    new Route(List.of(Direction.NORTH)),
                    new Route(List.of(Direction.EAST)),
                    new Route(List.of(Direction.WEST))
            ));
            return;
        }
        this.defaultMovement = new DefaultMovement(List.of(
                new Route(List.of(Direction.SOUTH)),
                new Route(List.of(Direction.EAST)),
                new Route(List.of(Direction.WEST))
        ));
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
        return !pieceOnRoute.hasArrivalPointInMyTeam(team);
    }

    @Override
    public boolean canNotJumpOver() {
        return false;
    }

    @Override
    public String getName() {
        return SOLDIER.getNameForTeam(team);
    }
}
