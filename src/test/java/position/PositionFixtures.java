package position;

import static position.Column.A;
import static position.Column.B;
import static position.Column.C;
import static position.Column.D;
import static position.Column.E;
import static position.Column.F;
import static position.Column.G;
import static position.Column.H;
import static position.Column.I;
import static position.Row.EIGHT;
import static position.Row.FIVE;
import static position.Row.FOUR;
import static position.Row.NINE;
import static position.Row.ONE;
import static position.Row.SEVEN;
import static position.Row.SIX;
import static position.Row.THREE;
import static position.Row.TWO;
import static position.Row.ZERO;

import java.util.HashSet;
import java.util.Set;

public final class PositionFixtures {
    public static final Position A0 = new Position(A, ZERO);
    public static final Position A1 = new Position(A, ONE);
    public static final Position A2 = new Position(A, TWO);
    public static final Position A3 = new Position(A, THREE);
    public static final Position A4 = new Position(A, FOUR);
    public static final Position A5 = new Position(A, FIVE);
    public static final Position A6 = new Position(A, SIX);
    public static final Position A7 = new Position(A, SEVEN);
    public static final Position A8 = new Position(A, EIGHT);
    public static final Position A9 = new Position(A, NINE);

    public static final Position B0 = new Position(B, ZERO);
    public static final Position B1 = new Position(B, ONE);
    public static final Position B2 = new Position(B, TWO);
    public static final Position B3 = new Position(B, THREE);
    public static final Position B4 = new Position(B, FOUR);
    public static final Position B5 = new Position(B, FIVE);
    public static final Position B6 = new Position(B, SIX);
    public static final Position B7 = new Position(B, SEVEN);
    public static final Position B8 = new Position(B, EIGHT);
    public static final Position B9 = new Position(B, NINE);

    public static final Position C0 = new Position(C, ZERO);
    public static final Position C1 = new Position(C, ONE);
    public static final Position C2 = new Position(C, TWO);
    public static final Position C3 = new Position(C, THREE);
    public static final Position C4 = new Position(C, FOUR);
    public static final Position C5 = new Position(C, FIVE);
    public static final Position C6 = new Position(C, SIX);
    public static final Position C7 = new Position(C, SEVEN);
    public static final Position C8 = new Position(C, EIGHT);
    public static final Position C9 = new Position(C, NINE);

    public static final Position D0 = new Position(D, ZERO);
    public static final Position D1 = new Position(D, ONE);
    public static final Position D2 = new Position(D, TWO);
    public static final Position D3 = new Position(D, THREE);
    public static final Position D4 = new Position(D, FOUR);
    public static final Position D5 = new Position(D, FIVE);
    public static final Position D6 = new Position(D, SIX);
    public static final Position D7 = new Position(D, SEVEN);
    public static final Position D8 = new Position(D, EIGHT);
    public static final Position D9 = new Position(D, NINE);

    public static final Position E0 = new Position(E, ZERO);
    public static final Position E1 = new Position(E, ONE);
    public static final Position E2 = new Position(E, TWO);
    public static final Position E3 = new Position(E, THREE);
    public static final Position E4 = new Position(E, FOUR);
    public static final Position E5 = new Position(E, FIVE);
    public static final Position E6 = new Position(E, SIX);
    public static final Position E7 = new Position(E, SEVEN);
    public static final Position E8 = new Position(E, EIGHT);
    public static final Position E9 = new Position(E, NINE);

    public static final Position F0 = new Position(F, ZERO);
    public static final Position F1 = new Position(F, ONE);
    public static final Position F2 = new Position(F, TWO);
    public static final Position F3 = new Position(F, THREE);
    public static final Position F4 = new Position(F, FOUR);
    public static final Position F5 = new Position(F, FIVE);
    public static final Position F6 = new Position(F, SIX);
    public static final Position F7 = new Position(F, SEVEN);
    public static final Position F8 = new Position(F, EIGHT);
    public static final Position F9 = new Position(F, NINE);

    public static final Position G0 = new Position(G, ZERO);
    public static final Position G1 = new Position(G, ONE);
    public static final Position G2 = new Position(G, TWO);
    public static final Position G3 = new Position(G, THREE);
    public static final Position G4 = new Position(G, FOUR);
    public static final Position G5 = new Position(G, FIVE);
    public static final Position G6 = new Position(G, SIX);
    public static final Position G7 = new Position(G, SEVEN);
    public static final Position G8 = new Position(G, EIGHT);
    public static final Position G9 = new Position(G, NINE);

    public static final Position H0 = new Position(H, ZERO);
    public static final Position H1 = new Position(H, ONE);
    public static final Position H2 = new Position(H, TWO);
    public static final Position H3 = new Position(H, THREE);
    public static final Position H4 = new Position(H, FOUR);
    public static final Position H5 = new Position(H, FIVE);
    public static final Position H6 = new Position(H, SIX);
    public static final Position H7 = new Position(H, SEVEN);
    public static final Position H8 = new Position(H, EIGHT);
    public static final Position H9 = new Position(H, NINE);

    public static final Position I0 = new Position(I, ZERO);
    public static final Position I1 = new Position(I, ONE);
    public static final Position I2 = new Position(I, TWO);
    public static final Position I3 = new Position(I, THREE);
    public static final Position I4 = new Position(I, FOUR);
    public static final Position I5 = new Position(I, FIVE);
    public static final Position I6 = new Position(I, SIX);
    public static final Position I7 = new Position(I, SEVEN);
    public static final Position I8 = new Position(I, EIGHT);
    public static final Position I9 = new Position(I, NINE);
    ;

//    public static Set<Position> generateWithBlank(){
//        return new HashSet<>(Set.of(
//                A0, A1, A2, A3, A4, A5, A6, A7, A8, A9,
//                B0, B1, B2, B3, B4, B5, B6, B7, B8, B9,
//                C0, C1, C2, C3, C4, C5, C6, C7, C8, C9,
//                D0, D1, D2, D3, D4, D5, D6, D7, D8, D9,
//                E0, E1, E2, E3, E4, E5, E6, E7, E8, E9,
//                F0, F1, F2, F3, F4, F5, F6, F7, F8, F9,
//                G0, G1, G2, G3, G4, G5, G6, G7, G8, G9,
//                H0, H1, H2, H3, H4, H5, H6, H7, H8, H9,
//                I0, I1, I2, I3, I4, I5, I6, I7, I8, I9
//                ));
//    }
}
