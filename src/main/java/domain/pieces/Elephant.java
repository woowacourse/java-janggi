package domain.pieces;

import domain.Camp;
import domain.ExistBoard;
import domain.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Elephant extends Piece {

    public Elephant(Camp camp) {
        super(camp);
    }

    @Override
    public boolean canMove(Position from, Position to, ExistBoard existBoard) {
        Map<Position, List<Position>> routeOfDestination = new HashMap<>();

        routeOfDestination.putAll(move_U_RU(from));
        routeOfDestination.putAll(move_U_LU(from));
        routeOfDestination.putAll(move_D_RD(from));
        routeOfDestination.putAll(move_D_LD(from));
        routeOfDestination.putAll(move_R_RU(from));
        routeOfDestination.putAll(move_R_RD(from));
        routeOfDestination.putAll(move_L_LU(from));
        routeOfDestination.putAll(move_L_LD(from));

        if (!routeOfDestination.containsKey(to)) {
            return false;
        }

        List<Position> route = routeOfDestination.get(to);
        for (Position position : route) {
            if (existBoard.isExist(position)) {
                return false;
            }
        }

        return true;
    }

    private Map<Position, List<Position>> move_U_RU(Position position) {
        Map<Position, List<Position>> routeOfDestination = new HashMap<>();
        List<Position> route = new ArrayList<>();
        try {
            position = up(position);
            route.add(position);

            position = rightUpDiagonal(position);
            route.add(position);

            position = rightUpDiagonal(position);

            routeOfDestination.put(position, route);
        } catch (IllegalArgumentException e) {
            // 생성할 수 없는 Position이면 무시
        }
        return routeOfDestination;
    }

    private Map<Position, List<Position>> move_U_LU(Position position) {
        Map<Position, List<Position>> routeOfDestination = new HashMap<>();
        List<Position> route = new ArrayList<>();
        try {
            position = up(position);
            route.add(position);

            position = leftUpDiagonal(position);
            route.add(position);

            position = leftUpDiagonal(position);

            routeOfDestination.put(position, route);
        } catch (IllegalArgumentException e) {
            // 생성할 수 없는 Position이면 무시
        }

        return routeOfDestination;
    }

    private Map<Position, List<Position>> move_D_RD(Position position) {
        Map<Position, List<Position>> routeOfDestination = new HashMap<>();
        List<Position> route = new ArrayList<>();
        try {
            position = down(position);
            route.add(position);

            position = rightDownDiagonal(position);
            route.add(position);

            position = rightDownDiagonal(position);

            routeOfDestination.put(position, route);
        } catch (IllegalArgumentException e) {
            // 생성할 수 없는 Position이면 무시
        }
        return routeOfDestination;
    }

    private Map<Position, List<Position>> move_D_LD(Position position) {
        Map<Position, List<Position>> routeOfDestination = new HashMap<>();
        List<Position> route = new ArrayList<>();
        try {
            position = down(position);
            route.add(position);

            position = leftDownDiagonal(position);
            route.add(position);

            position = leftDownDiagonal(position);

            routeOfDestination.put(position, route);
        } catch (IllegalArgumentException e) {
            // 생성할 수 없는 Position이면 무시
        }

        return routeOfDestination;
    }

    private Map<Position, List<Position>> move_R_RU(Position position) {
        Map<Position, List<Position>> routeOfDestination = new HashMap<>();
        List<Position> route = new ArrayList<>();
        try {
            position = right(position);
            route.add(position);

            position = rightUpDiagonal(position);
            route.add(position);

            position = rightUpDiagonal(position);

            routeOfDestination.put(position, route);
        } catch (IllegalArgumentException e) {
            // 생성할 수 없는 Position이면 무시
        }

        return routeOfDestination;
    }

    private Map<Position, List<Position>> move_R_RD(Position position) {
        Map<Position, List<Position>> routeOfDestination = new HashMap<>();
        List<Position> route = new ArrayList<>();
        try {
            position = right(position);
            route.add(position);

            position = rightDownDiagonal(position);
            route.add(position);

            position = rightDownDiagonal(position);

            routeOfDestination.put(position, route);
        } catch (IllegalArgumentException e) {
            // 생성할 수 없는 Position이면 무시
        }

        return routeOfDestination;
    }

    private Map<Position, List<Position>> move_L_LU(Position position) {
        Map<Position, List<Position>> routeOfDestination = new HashMap<>();
        List<Position> route = new ArrayList<>();
        try {
            position = left(position);
            route.add(position);

            position = leftUpDiagonal(position);
            route.add(position);

            position = leftUpDiagonal(position);

            routeOfDestination.put(position, route);
        } catch (IllegalArgumentException e) {
            // 생성할 수 없는 Position이면 무시
        }

        return routeOfDestination;
    }

    private Map<Position, List<Position>> move_L_LD(Position position) {
        Map<Position, List<Position>> routeOfDestination = new HashMap<>();
        List<Position> route = new ArrayList<>();
        try {
            position = left(position);
            route.add(position);

            position = leftDownDiagonal(position);
            route.add(position);

            position = leftDownDiagonal(position);

            routeOfDestination.put(position, route);
        } catch (IllegalArgumentException e) {
            // 생성할 수 없는 Position이면 무시
        }

        return routeOfDestination;
    }
}
