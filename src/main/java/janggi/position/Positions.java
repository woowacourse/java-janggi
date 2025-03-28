package janggi.position;

import static janggi.position.Column.A;
import static janggi.position.Column.B;
import static janggi.position.Column.C;
import static janggi.position.Column.D;
import static janggi.position.Column.E;
import static janggi.position.Column.F;
import static janggi.position.Column.G;
import static janggi.position.Column.H;
import static janggi.position.Column.I;
import static janggi.position.Row.EIGHT;
import static janggi.position.Row.NINE;
import static janggi.position.Row.ONE;
import static janggi.position.Row.SEVEN;
import static janggi.position.Row.SIX;
import static janggi.position.Row.THREE;
import static janggi.position.Row.TWO;
import static janggi.position.Row.ZERO;
import static janggi.route.Direction.EAST_NORTH;
import static janggi.route.Direction.EAST_SOUTH;
import static janggi.route.Direction.WEST_NORTH;
import static janggi.route.Direction.WEST_SOUTH;

import janggi.route.Route;
import janggi.route.Routes;
import java.util.List;
import java.util.Set;

public class Positions {
    public static final Position A0 = new Position(A, ZERO);
    public static final Position A3 = new Position(A, THREE);
    public static final Position A6 = new Position(A, SIX);
    public static final Position A9 = new Position(A, NINE);

    public static final Position B0 = new Position(B, ZERO);
    public static final Position B2 = new Position(B, TWO);
    public static final Position B7 = new Position(B, SEVEN);
    public static final Position B9 = new Position(B, NINE);

    public static final Position C0 = new Position(C, ZERO);
    public static final Position C3 = new Position(C, THREE);
    public static final Position C6 = new Position(C, SIX);
    public static final Position C9 = new Position(C, NINE);


    public static final Position E3 = new Position(E, THREE);
    public static final Position E6 = new Position(E, SIX);


    public static final Position G0 = new Position(G, ZERO);
    public static final Position G3 = new Position(G, THREE);
    public static final Position G6 = new Position(G, SIX);
    public static final Position G9 = new Position(G, NINE);

    public static final Position H0 = new Position(H, ZERO);
    public static final Position H2 = new Position(H, TWO);
    public static final Position H7 = new Position(H, SEVEN);
    public static final Position H9 = new Position(H, NINE);

    public static final Position I0 = new Position(I, ZERO);
    public static final Position I3 = new Position(I, THREE);

    public static final Position I6 = new Position(I, SIX);
    public static final Position I9 = new Position(I, NINE);

    public static final Position D0 = new Position(D, ZERO);
    public static final Position E0 = new Position(E, ZERO);
    public static final Position F0 = new Position(F, ZERO);

    public static final Position D1 = new Position(D, ONE);
    public static final Position E1 = new Position(E, ONE);
    public static final Position F1 = new Position(F, ONE);

    public static final Position D2 = new Position(D, TWO);
    public static final Position E2 = new Position(E, TWO);
    public static final Position F2 = new Position(F, TWO);

    public static final Position D7 = new Position(D, SEVEN);
    public static final Position E7 = new Position(E, SEVEN);
    public static final Position F7 = new Position(F, SEVEN);

    public static final Position D8 = new Position(D, EIGHT);
    public static final Position E8 = new Position(E, EIGHT);
    public static final Position F8 = new Position(F, EIGHT);

    public static final Position E9 = new Position(E, NINE);
    public static final Position D9 = new Position(D, NINE);
    public static final Position F9 = new Position(F, NINE);

    public boolean isInPalace(Position position) {
        return ofPalace().contains(position);
    }

    private Set<Position> ofPalace() {
        return Set.of(
                D0, E0, F0,
                D1, E1, F1,
                D2, E2, F2,

                D7, E7, F7,
                D8, E8, F8,
                D9, E9, F9
        );
    }

    public Routes addPossiblePalaceDirections(Position position) {
        if (position.equals(D0) || position.equals(D7)) {
            return new Routes(Set.of(new Route(List.of(EAST_NORTH))));
        }

        if (position.equals(F0) || position.equals(F7)) {
            return new Routes(Set.of(new Route(List.of(WEST_NORTH))));
        }

        if (position.equals(D2) || position.equals(D9)) {
            return new Routes(Set.of(new Route(List.of(EAST_SOUTH))));
        }

        if (position.equals(F2) || position.equals(F9)) {
            return new Routes(Set.of(new Route(List.of(WEST_SOUTH))));
        }

        if (position.equals(E1) || position.equals(E8)) {
            return new Routes(Set.of(
                    new Route(List.of(EAST_NORTH)),
                    new Route(List.of(WEST_NORTH)),
                    new Route(List.of(EAST_SOUTH)),
                    new Route(List.of(WEST_SOUTH))
            ));
        }
        return new Routes(Set.of());
    }

    public Routes addPossiblePalaceForHanPawnDirections(Position position) {
        if (position.equals(D2)) {
            return new Routes(Set.of(new Route(List.of(EAST_SOUTH))));
        }

        if (position.equals(F2)) {
            return new Routes(Set.of(new Route(List.of(WEST_SOUTH))));
        }

        if (position.equals(E1)) {
            return new Routes(Set.of(
                    new Route(List.of(EAST_SOUTH)),
                    new Route(List.of(WEST_SOUTH))
            ));
        }
        return new Routes(Set.of());
    }

    public Routes addPossiblePalaceForChoPawnDirections(Position position) {
        if (position.equals(D9)) {
            return new Routes(Set.of(new Route(List.of(EAST_NORTH))));
        }

        if (position.equals(F9)) {
            return new Routes(Set.of(new Route(List.of(WEST_NORTH))));
        }

        if (position.equals(E8)) {
            return new Routes(Set.of(
                    new Route(List.of(EAST_NORTH)),
                    new Route(List.of(WEST_NORTH))
            ));
        }
        return new Routes(Set.of());
    }
}