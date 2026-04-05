package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.domain.piece.moverules.ChaMoveRule;
import janggi.domain.piece.moverules.KingMoveRule;
import janggi.domain.piece.moverules.MaMoveRule;
import janggi.domain.piece.moverules.MoveRule;
import janggi.domain.piece.moverules.PoMoveRule;
import janggi.domain.piece.moverules.SaMoveRule;
import janggi.domain.piece.moverules.SangMoveRule;
import janggi.domain.piece.moverules.ZolMoveRule;
import janggi.domain.route.Route;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PieceType {
    KING("왕", new KingMoveRule()),
    SA("사", new SaMoveRule()),
    SANG("상", new SangMoveRule()),
    MA("마", new MaMoveRule()),
    CHA("차", new ChaMoveRule()),
    PO("포", new PoMoveRule()),
    ZOL("졸", new ZolMoveRule());

    private final String name;
    private final MoveRule moveRule;

    PieceType(String name, MoveRule moveRule) {
        this.name = name;
        this.moveRule = moveRule;
    }

    public String getName() {
        return name;
    }

    public List<Route> findRoutes(Team team) {
        return moveRule.findRoutes(team);
    }

    public Map<Position, List<Position>> convertToPosition(Position position, Team team) {
        if (this == CHA || this == PO) {
            return convertToContinuousRoutes(position, findRoutes(team));
        }
        return convertToFixedRoutes(position, findRoutes(team));
    }

    public List<Position> findMovablePositions(Board board, Position position, Team team) {
        return moveRule.findMovablePositions(board, position, team);
    }

    private Map<Position, List<Position>> convertToFixedRoutes(Position position, List<Route> routes) {
        Map<Position, List<Position>> result = new HashMap<>();
        for (Route route : routes) {
            List<Position> positionRoute = route.applyDirections(position);
            if (positionRoute.isEmpty()) {
                continue;
            }
            result.put(positionRoute.getLast(), positionRoute);
        }
        return result;
    }

    private Map<Position, List<Position>> convertToContinuousRoutes(Position position, List<Route> routes) {
        Map<Position, List<Position>> result = new HashMap<>();
        for (Route route : routes) {
            route.applyContinuousDirections(position, result);
        }
        return result;
    }
}
